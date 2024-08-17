package org.opensubtitle

import androidx.annotation.IntRange
import androidx.annotation.StringDef
import androidx.annotation.WorkerThread
import androidx.core.text.isDigitsOnly
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.opensubtitle.exception.OpenSubtitleException
import org.opensubtitle.language.SubLanguageId
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

class OpenSubtitle(private val userAgent: String) : OpenSubtitleI {

    companion object {
        // search only
        const val MOVIE = 1000
        const val TV_SERIES = 1001

        // subtitle format
        const val FORMAT_SRT = "srt"
        const val FORMAT_SUB = "sub"
        const val FORMAT_TXT = "txt"
        const val FORMAT_SSA = "ssa"
        const val FORMAT_SMI = "smi"
        const val FORMAT_MPL = "mpl"
        const val FORMAT_TMP = "tmp"
        const val FORMAT_VTT = "vtt"
        const val FORMAT_DFXP = "dfxp"

        // user agent
        const val DEFAULT_USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
    }

    private var url: String = ""
    private var page: Int = -1
    private var totalPage: Int = -1

    @WorkerThread
    override suspend fun search(url: String, page: Int, response: Response?) {
        if (url.contains("https://www.opensubtitles.org/en/search/")) {
            val newUrl = if (this.page != -1 && page > 1 && totalPage > page) {
                "$url/offset-${page * 40}}"
            } else {
                this.url = url
                url
            }
            getSubtitle(newUrl, response)
        }
    }

    override suspend fun getDownloadUrl(id: Long): String? {
        var html: String? = null
        try {
            println("id : $id")
            withContext(Dispatchers.IO) {
                val initialUrl = "https://www.opensubtitles.org/en/subtitles/$id"
                val finalUrl = followRedirects(initialUrl)
                if (finalUrl != null) {
                    val urlConnection = URL(finalUrl).openConnection() as HttpURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.setRequestProperty(
                        "User-Agent",
                        userAgent
                    )
                    urlConnection.connect()

                    val responseCode = urlConnection.responseCode
                    println("Response Code: $responseCode")

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        urlConnection.inputStream.bufferedReader().use {
                            html = it.readText()
                        }
                    } else {
                        println("Failed to fetch data. Response Code: $responseCode")
                    }
                } else {
                    println("Failed to follow redirects.")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("${e.message}")
        }
        val url = extractUrl(html)
        return url
    }

    private fun followRedirects(initialUrl: String): String? {
        var url: String? = initialUrl
        var redirectCount = 0
        while (url != null) {
            val connection = (URL(url).openConnection() as HttpURLConnection).apply {
                instanceFollowRedirects = false
            }
            try {
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode in 300..399) {
                    val location = connection.getHeaderField("Location")
                    url = if (location.startsWith("/")) {
                        val baseUrl = URL(initialUrl)
                        "${baseUrl.protocol}://${baseUrl.host}$location"
                    } else {
                        location
                    }
                    redirectCount++
                    if (redirectCount > 5) {
                        println("Too many redirects.")
                        return null
                    }
                } else if (responseCode == HttpURLConnection.HTTP_OK) {
                    return url
                } else {
                    println("Failed to fetch data. Response Code: $responseCode")
                    return null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            } finally {
                connection.disconnect()
            }
        }
        return null
    }

    private fun extractUrl(html: String?): String? {
        if (html == null) return null
        // Parse the HTML string using Jsoup
        val document: Document = Jsoup.parse(html)

        // Select the first anchor tag with class "none" within the form with name "moviehash"
        val anchor: Element? = document.select("form[name=moviehash] a.none").first()

        // Return the value of the href attribute, if the anchor element is found
        return anchor?.attr("href")
    }


    @kotlin.jvm.Throws(
        IOException::class,
        InterruptedException::class,
        JSONException::class,
        OpenSubtitleException::class
    )
    private suspend fun getSubtitle(url: String, response: Response?) {
        var httpConnection: HttpURLConnection? = null
        var html: String? = null
        withContext(Dispatchers.IO) {
            try {
                httpConnection = URL(url).openConnection() as HttpURLConnection
                httpConnection!!.requestMethod = "GET"
                httpConnection!!.setRequestProperty("User-Agent", userAgent)
                httpConnection!!.connect()

                if (httpConnection!!.responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader(InputStreamReader(httpConnection!!.inputStream)).use { reader ->
                        html = reader.readText()
                        reader.close()
                    }
                }
            } catch (e: Exception) {
                response?.onFailure(OpenSubtitleException(e.message))
            } finally {
                httpConnection?.disconnect()
            }

            if (html.isNullOrEmpty()) {
                response?.onFailure(OpenSubtitleException("Empty Response"))
                return@withContext
            }
            val trPattern = Pattern.compile("""<tr.*?>(.*?)</tr>""", Pattern.DOTALL)
            val trMatcher = trPattern.matcher(html!!)

            val pattern = Pattern.compile(
                """<a class="bnone".*?>(.*?)\s*\((\d{4})\)</a>.*?<br/>\s*<span title="([^"]+)">.*?</span>.*?<a.*?<a title="[^"]*" href="[^"]*sublanguageid-([a-zA-Z]+)".*?<time datetime="([^"]+)" title="([^"]+)">.*?href="([^"]+)".*?>(\d\.\d)</a>""",
                Pattern.DOTALL
            )

            val subtitles: MutableList<SubtitleResult.Subtitle> = mutableListOf()
            while (trMatcher.find()) {
                val trContent = trMatcher.group(1) ?: continue
                val matcher = pattern.matcher(trContent)
                if (matcher.find()) {
                    val name = matcher.group(1)?.trim() ?: ""
                    val year = matcher.group(2)?.trim() ?: ""
                    val title = matcher.group(3)?.trim()
                    val languageCode = matcher.group(4)?.trim() ?: ""
                    val datetime = matcher.group(5)?.trim() ?: ""
                    val date = matcher.group(6)?.trim()?.substringAfterLast(" ") ?: ""
                    val time = matcher.group(6)?.trim()?.substringBeforeLast(" ") ?: ""
                    val id =
                        matcher.group(7)?.trim().takeIf { !it.isNullOrEmpty() }
                            ?.substringAfterLast("/")
                            ?.takeIf { it.isDigitsOnly() }?.toLong() ?: 0
                    val imdbRating = matcher.group(8)?.trim()?.toDouble() ?: 0.0
                    subtitles.add(
                        SubtitleResult.Subtitle(
                            name = name,
                            releaseYear = year,
                            title = title,
                            subLanguageId = languageCode,
                            dateTime = datetime,
                            publishedDate = date,
                            publishedTime = time,
                            imdbUrl = "",
                            imdbRating = imdbRating,
                            id = id,
                            downloadUrl = "https://www.opensubtitles.org/en/subtitleserve/sub/$id"
                        )
                    )
                }
            }
            withContext(Dispatchers.Main) {
                response?.onSuccess(SubtitleResult(page = 1, totalPage = 1, subtitles))
            }
        }
    }

    class UrlBuilder(private val name: String) {
        private var subLanguageId = SubLanguageId.ALL
        private var searchOnly: Int = -1
        private var season: Int = 0
        private var episode: Int = 0
        private var format: String? = null
        private var imdbId: Long = -1L

        fun setSubLanguageId(@SubLanguageId subLanguageId: String): UrlBuilder {
            this.subLanguageId = subLanguageId
            return this
        }

        fun searchOnlyIn(@SearchOnly searchOnly: Int): UrlBuilder {
            this.searchOnly = searchOnly
            return this
        }

        fun setSeason(@IntRange(from = 1) season: Int): UrlBuilder {
            this.season = season
            return this
        }

        fun setEpisode(@IntRange(from = 1) episode: Int): UrlBuilder {
            this.episode = episode
            return this
        }

        fun setSubFormat(@SubFormat format: String): UrlBuilder {
            this.format = format
            return this
        }

        fun setImdbId(imdbId: Long): UrlBuilder {
            this.imdbId = imdbId
            return this
        }

        fun build(): String {
            //https://www.opensubtitles.org/en/search/sublanguageid-abk/searchonlymovies-on/searchonlytvseries-on/season-1/episode-2/genre-action/movielanguage-acholi/moviecountry-albania/moviefps-23.976/subformat-srt/subadddate-7/subversion-on/subtrusted-on/hearingimpaired-on/hd-on/foreignpartsonly-on/moviename-ek+tha+tiger
            return buildString {
                append("https://www.opensubtitles.org/en/search/sublanguageid-$subLanguageId/")
                if (searchOnly == MOVIE) {
                    append("searchonlymovies-on/")
                } else if (searchOnly == TV_SERIES) {
                    append("searchonlytvseries-on/")
                }
                if (season > 0) {
                    append("season-$season/")
                }
                if (episode > 0) {
                    append("episode-$episode/")
                }
                if (format != null) {
                    append("subformat-$format/")
                }
                append("moviename-$name")
            }
        }

    }

    @StringDef(
        FORMAT_SRT, FORMAT_SUB, FORMAT_TXT, FORMAT_SSA, FORMAT_SMI, FORMAT_MPL, FORMAT_TMP,
        FORMAT_VTT, FORMAT_DFXP
    )
    @Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class SubFormat

    interface Response {
        fun onSuccess(data: SubtitleResult)
        fun onFailure(error: OpenSubtitleException)
    }
}