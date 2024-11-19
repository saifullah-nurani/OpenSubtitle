package org.opensubtitle

import android.util.Log
import androidx.annotation.IntDef
import androidx.annotation.IntRange
import androidx.annotation.StringDef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.opensubtitle.data.Result
import org.opensubtitle.data.Subtitle
import org.opensubtitle.exception.SubtitleException
import org.opensubtitle.http.HttpClient
import java.io.IOException
import java.net.HttpURLConnection

class OpenSubtitle {
    private var currentPage = 0
    private var totalPage: Int = 0
    private lateinit var searchUrl: String
    private val httpClient: HttpClient = HttpClient()
    private lateinit var responseCallback: ResponseCallback

    /**
     * Performs a advanceSearch for subtitles on OpenSubtitles.
     *
     * @param url The base URL for the search.
     * @param page The page number to fetch (default is 1).
     * @param callback The callback to handle success or failure.
     * @throws SubtitleException if the URL is invalid or network issues occur.
     */
    suspend fun search(
        url: String, @IntRange(from = 1) page: Int = 1, callback: ResponseCallback
    ) {
        try {
            if (!url.contains("https://www.opensubtitles.org/en/search/")) {
                throw SubtitleException(
                    SubtitleException.ERROR_CODE_INVALID_URL,
                    "Invalid URL: URL must contain 'https://www.opensubtitles.org/en/search/'"
                )
            }
            // Ensure the URL contains at least one required parameter for a valid search
            if (!url.contains("moviename-").or(url.contains("imdbid-"))) {
                throw SubtitleException(
                    SubtitleException.ERROR_CODE_INVALID_SEARCH_PARAMS,
                    "Invalid search parameters: URL must contain either title or imdb id"
                )
            }
            val newUrl = if (page > 1) {
                "$url/offset-${(page - 1) * 40}"
            } else {
                this.searchUrl = url
                url
            }
            responseCallback = callback
            performSearch(newUrl)
        } catch (e: SubtitleException) {
            callback.onFailure(e)
        } catch (e: Exception) {
            callback.onFailure(
                SubtitleException(
                    SubtitleException.ERROR_CODE_UNSPECIFIED,
                    "Unknown error occurred during search", e
                )
            )
        }
    }

    /**
     * Retrieves the direct download URL for a subtitle.
     *
     * @param id The subtitle ID.
     * @return The direct download URL or null if an error occurs.
     */
    suspend fun getDirectDownloadUrl(id: Long): String? {
        return withContext(Dispatchers.IO) {
            try {
                val finalUrl =
                    HttpClient.getFinalRedirectUrl("https://www.opensubtitles.org/en/subtitles/$id")
                if (finalUrl != null) {
                    httpClient.connect(finalUrl)
                }
                if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
                    val html = httpClient.readResponse()
                    REGEX_DIRECT_URL.find(html)?.groups?.get(1)?.value
                } else null
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching direct download URL: ${e.message}")
                null
            } finally {
                httpClient.disconnect()
            }
        }
    }

    /**
     * Performs the actual search operation.
     *
     * @param url The search URL.
     * @throws SubtitleException if a network or state error occurs.
     */
    private suspend fun performSearch(url: String) {
        withContext(Dispatchers.IO) {
            try {
                httpClient.connect(url)
                if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
                    val html = httpClient.readResponse()
                    val subtitle = parseResponse(html)
                    val pager = REGEX_PAGER.find(html)?.value
                    pager?.let {
                        // Extract the current page from <strong> tag
                        currentPage =
                            REGEX_CURRENT_PAGE.find(it)?.groups?.get(1)?.value?.toIntOrNull() ?: 1
                        // Extract total pages by finding the max page number in <a> tags
                        val workingValue = it.replace("&lt;&lt;", "<<").replace("&gt;&gt;", ">>")
                        totalPage = REGEX_TOTAL_PAGE.findAll(workingValue)
                            .mapNotNull { match -> match.groups[1]?.value?.toIntOrNull() }
                            .maxOrNull()
                            ?: currentPage  // Default to current page if no total pages found
                    }
                    withContext(Dispatchers.Main) {
                        responseCallback.onSuccess(Result(currentPage, totalPage, subtitle))
                    }
                } else {
                    throw SubtitleException(
                        SubtitleException.ERROR_CODE_NETWORK_FAILURE,
                        "Failed to connect: ${httpClient.responseCode}"
                    )
                }
            } catch (e: IOException) {
                Log.e(TAG, "Network error: ${e.message}")
                withContext(Dispatchers.Main) {
                    responseCallback.onFailure(
                        SubtitleException(
                            SubtitleException.ERROR_CODE_NETWORK_FAILURE,
                            "Network error: ${e.message}",
                            e
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Unknown error: ${e.message}")
                withContext(Dispatchers.Main) {
                    responseCallback.onFailure(
                        SubtitleException(
                            SubtitleException.ERROR_CODE_UNSPECIFIED,
                            "Unknown error: ${e.message}",
                            e
                        )
                    )
                }
            } finally {
                httpClient.disconnect()
            }
        }
    }

    /**
     * Parses the response HTML and extracts subtitle details.
     *
     * @param html The response HTML.
     * @return A list of extracted subtitles.
     */
    private fun parseResponse(html: String?): List<Subtitle> {
        return REGEX_SUBTITLE.findAll(html ?: "").mapNotNull { matcher ->
            try {
                val pageUrl = matcher.groups[1]?.value.orEmpty()
                val title = matcher.groups[2]?.value.orEmpty()
                val id = pageUrl.substringBeforeLast("/").substringAfterLast("/").toLongOrNull()
                val releaseYear = matcher.groups[3]?.value.orEmpty()
                val description =
                    matcher.groups[4]?.value.takeIf { it?.contains("<td") == false }
                val subLanguage = matcher.groups[5]?.value.orEmpty()
                val subLanguageUrl = matcher.groups[6]?.value.orEmpty()
                val subLanguageId = subLanguageUrl.substringAfterLast("-")
                val cd = matcher.groups[7]?.value.orEmpty()
                val iosTimeStamp = matcher.groups[8]?.value.orEmpty()
                val dateTime = matcher.groups[9]?.value?.split(Regex("\\s"))
                val date = dateTime?.firstOrNull().orEmpty()
                val time = dateTime?.lastOrNull().orEmpty()
                val zipFileUrl = matcher.groups[10]?.value.orEmpty()
                val totalDownload = matcher.groups[11]?.value?.toIntOrNull() ?: 0
                val subFormat = matcher.groups[12]?.value.orEmpty()
                val votes = matcher.groups[13]?.value?.toDoubleOrNull() ?: 0.0
                val imdbUrl = matcher.groups[15]?.value.orEmpty()
                val imdbVoteCount = matcher.groups[14]?.value?.toIntOrNull() ?: 0
                val imdbId = imdbUrl.substringBeforeLast("/").substringAfterLast("/")
                val imdbRating = matcher.groups[16]?.value?.toDoubleOrNull() ?: 0.0
                val uploaderProfileUrl = matcher.groups[17]?.value.orEmpty()
                val uploaderName = matcher.groups[18]?.value.orEmpty()
                val uploaderId = uploaderProfileUrl.substringAfterLast("-").toLongOrNull() ?: 0L

                Subtitle(
                    id = id ?: 0,
                    title = title,
                    description = description,
                    pageUrl = pageUrl,
                    releaseYear = releaseYear,
                    subFormat = subFormat,
                    cd = cd,
                    zipFileUrl = zipFileUrl,
                    publishedIsoTimeStamp = iosTimeStamp,
                    publishedDate = date,
                    publishedTime = time,
                    totalDownloads = totalDownload,
                    votes = votes,
                    subLanguage = Subtitle.SubLanguage(
                        language = subLanguage,
                        languageId = subLanguageId,
                        languageUrl = subLanguageUrl
                    ),
                    imdb = Subtitle.Imdb(
                        id = imdbId,
                        rating = imdbRating,
                        url = imdbUrl,
                        voteCount = imdbVoteCount
                    ),
                    uploader = if (uploaderId == 0L) null else Subtitle.Uploader(
                        uploaderId, uploaderName, uploaderProfileUrl
                    )
                )
            } catch (e: Exception) {
                responseCallback.onFailure(
                    SubtitleException(
                        SubtitleException.ERROR_CODE_PARSING_FAILURE,
                        "Error parsing subtitle: ${e.message}", e
                    )
                )
                Log.e(TAG, "Error parsing subtitle: ${e.message}")
                null // Skip this entry if an error occurs
            }
        }.toList()
    }

    interface ResponseCallback {
        fun onSuccess(data: Result)
        fun onFailure(error: SubtitleException)
    }

    companion object {
        private val TAG = OpenSubtitle::class.java.simpleName
        private const val USER_AGENT = ".opensubtitle"

        // search only
        const val MOVIE = "movies"
        const val TV_SERIES = "tvseries"

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

        // FPS
        const val FPS_23_976 = "23.976"
        const val FPS_23_980 = "23.980"
        const val FPS_24 = "24.0"
        const val FPS_25 = "25.0"
        const val FPS_29_97 = "29.97"
        const val FPS_30 = "30.0"
        const val FPS_50 = "50.0"
        const val FPS_59_940 = "59.940"
        const val FPS_60 = "60.0"

        // comparison operator
        const val OPERATOR_IS_EQUAL = 1
        const val OPERATOR_IS_LESS_THEN = 2
        const val OPERATOR_IS_LESS_THEN_EQUAL = 3
        const val OPERATOR_IS_GREATER_THEN = 4
        const val OPERATOR_IS_GREATER_THEN_EQUAL = 5
        const val OPERATOR_IS_NOT_EQUAL = 6

        // Regex patterns for parsing
        private val REGEX_DIRECT_URL = Regex("""moviehash.*?href="([^"]+)""")
        private val REGEX_PAGER = Regex("""<div class="pager-list".*?</div>""")
        private val REGEX_CURRENT_PAGE = Regex("""<strong>(\d+)</strong>""")
        private val REGEX_TOTAL_PAGE = Regex("""<a href="[^"]+">(\d+?)</a>""")
        private val REGEX_SUBTITLE =
            Regex("""<a class="bnone".*?href="([^"]+).*?">(.*)?\s+\((\d{4}).*>(.*)?\s+.*?<a title="([^"]+).*?href="([^"]+).*?center">([^<\s?]+)\s+.*datetime="([^"]+)?.*title="([^"]+)?.*?href="([^"]+)?.*>(\d{1,})?.*?\s+.*?p">([a-z]+).*?votes">([\d.]+).*?title="(\d+).*?redirect/([^"]+).*?">([\d.]+).*?<a href="([^"]+).*?>(.*?)</a>""")
    }

    @StringDef(
        FORMAT_SRT, FORMAT_SUB, FORMAT_TXT, FORMAT_SSA, FORMAT_SMI, FORMAT_MPL, FORMAT_TMP,
        FORMAT_VTT, FORMAT_DFXP
    )
    @Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    internal annotation class SubFormat

    @Retention(AnnotationRetention.SOURCE)
    @StringDef(MOVIE, TV_SERIES)
    internal annotation class SearchOnly

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(
        OPERATOR_IS_EQUAL,
        OPERATOR_IS_LESS_THEN,
        OPERATOR_IS_LESS_THEN_EQUAL,
        OPERATOR_IS_GREATER_THEN,
        OPERATOR_IS_GREATER_THEN_EQUAL, OPERATOR_IS_NOT_EQUAL
    )
    internal annotation class ComparisonOperator

    @Retention(AnnotationRetention.SOURCE)
    @StringDef(
        FPS_23_976, FPS_23_980, FPS_24, FPS_25, FPS_29_97,
        FPS_30, FPS_50, FPS_59_940, FPS_60
    )
    internal annotation class Fps
}