package io.github.saifullah.nurani.opensubtitle.endpoint

import io.github.saifullah.nurani.opensubtitle.OpenSubtitle
import io.github.saifullah.nurani.opensubtitle.core.HttpClient
import io.github.saifullah.nurani.opensubtitle.data.Subtitle
import io.github.saifullah.nurani.opensubtitle.data.SubtitleData
import io.github.saifullah.nurani.opensubtitle.data.SubtitleRaw
import io.github.saifullah.nurani.opensubtitle.data.toSubtitle
import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException
import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException.Companion.ERROR_CODE_INVALID_SEARCH_PARAMS
import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException.Companion.ERROR_CODE_INVALID_URL
import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException.Companion.ERROR_CODE_IO_EXCEPTION
import io.github.saifullah.nurani.opensubtitle.extension.executeWithHandling

internal class SearchEndpoint(override val httpClient: HttpClient) : EndPoint() {

    private companion object {
        // OFFSET
        const val OFFSET = 40

        // Regex for Paging
        val REGEX_PREV_PAGE = Regex("""<link\s+rel="prev"\s+href="[^"]*/offset-(\d+)"[^>]*>""")
        val REGEX_LAST_PAGE = Regex("""<link\s+rel="last"\s+href="[^"]*/offset-(\d+)"[^>]*>""")

        // Regex For Subtitle
        val SUBTITLE_TABLE_REGEX = """<tr\s+onclick([\s\S]*?)</tr>""".toRegex()
        val TD1_REGEX = """href="([^"]+).*?">(.*?)\s+\((\d{4})\)""".toRegex()
        val TD2_REGEX = """align=.*title="([^"]+)"\s+href="([^"]+)"""".toRegex()
        val TD3_REGEX = """align.*>([^"]+)</td>""".toRegex()
        val TD4_REGEX = """align.*datetime="([^"]+)"\s+title="([^"]+).*class.*">([^<]+)""".toRegex()
        val TD5_REGEX = """align.*>([^<]+)x\s+.*">([^<]+)""".toRegex()
        val TD6_REGEX = """align.*title="(\d+).*?">([^<]+)""".toRegex()
        val TD8_REGEX = """align.*title="(\d+).*href="/redirect/([^"]+).*">([\d.]+)""".toRegex()
        val TD9_REGEX = """<a href="([^"]+)".*?>([^<]+)""".toRegex()
    }

    @Throws(OpenSubtitleException::class)
    operator fun invoke(url: String, page: Int): SubtitleData {
        return executeWithHandling {
            validateUrl(url) // May throw OpenSubtitleException(ERROR_CODE_INVALID_URL)
            val finalUrl = buildFinalUrl(url, page)
            val response = httpClient.get(finalUrl)
            if (response.statusCode == 200 && response.bodyAsText != null) {
                val subtitle = extractSubtitles(response.bodyAsText!!)
                val pages = extractPages(response.bodyAsText!!)
                var currentPage = pages.first
                var totalPage = pages.second
                if (currentPage == null && totalPage == null) {
                    if (subtitle.isNotEmpty()) {
                        currentPage = 1
                        totalPage = 1
                    }
                }
                SubtitleData(currentPage ?: -1, totalPage ?: -1, subtitle)
            } else {
                throw OpenSubtitleException(
                    ERROR_CODE_IO_EXCEPTION,
                    "Failed to load subtitles: server returned non-200 response or input stream was null.",
                    null
                )
            }
        }!!
    }


    /**
     * Extracts the current and total page numbers from the raw HTML using regex matches.
     *
     * OpenSubtitles paginates results using offset-based URLs (e.g., `offset-40`, `offset-80`).
     * This function converts those offsets into page numbers. It looks for two values:
     * - The offset of the "previous" page (to infer the current page)
     * - The offset of the "last" page (to determine the total number of pages)
     *
     * Logic:
     * - If the previous offset exists, calculate the current page by adding `OFFSET * 2`
     *   (to include both the previous page and the current page), then divide by `OFFSET`.
     * - If the last offset exists, calculate the total page by adding `OFFSET`
     *   (to account for the first page at offset 0), then divide by `OFFSET`.
     * - Fallbacks are used if either value is missing.
     *
     * @param rawHtml The raw HTML string to parse.
     * @return A [Pair] where the first value is the current page number and the second is the total page number.
     */
    private fun extractPages(rawHtml: String): Pair<Int?, Int?> {
        val prevOffset = REGEX_PREV_PAGE.find(rawHtml)?.groupValues?.getOrNull(1)?.toIntOrNull()
        val lastOffset = REGEX_LAST_PAGE.find(rawHtml)?.groupValues?.getOrNull(1)?.toIntOrNull()
        var currentPage: Int? = null
        var totalPage: Int? = null

        // Calculate current page from previous offset
        if (prevOffset != null) {
            // If previous offset is 140, add 80 (OFFSET * 2) to include the current page and first page,
            // then divide by 40 (OFFSET) to get current page number.
            currentPage = (prevOffset + (OFFSET * 2)) / OFFSET
        } else if (lastOffset != null) {
            currentPage = 1 // Fallback if only lastOffset is available
        }

        // Calculate total page from last offset
        if (lastOffset != null) {
            // If last offset is 480, add 40 (OFFSET) to account for the first page at offset 0,
            // then divide by 40 (OFFSET) to get total number of pages.
            totalPage = (lastOffset + OFFSET) / OFFSET
        } else if (prevOffset != null) {
            totalPage = currentPage // Fallback if only prevOffset is available
        }
        return Pair(currentPage, totalPage)
    }


    @Throws(OpenSubtitleException::class)
    private fun extractSubtitles(rawHtml: String): List<Subtitle> {
        return SUBTITLE_TABLE_REGEX.findAll(rawHtml).mapNotNull { match ->
            val subtitleTable = match.groupValues[1].ifEmpty { return@mapNotNull null }
            val tdBlocks = subtitleTable.split("<td")
            val subtitleRaw = SubtitleRaw()
            TD1_REGEX.find(tdBlocks.getOrNull(1).orEmpty())?.let {
                subtitleRaw.pageUrl = it.groupValues.getOrNull(1).orEmpty()
                subtitleRaw.title = it.groupValues.getOrNull(2).orEmpty().trim()
                subtitleRaw.releaseYear = it.groupValues.getOrNull(3)?.toInt() ?: 0
                subtitleRaw.id = subtitleRaw.pageUrl.split("/").getOrNull(3)?.toLongOrNull() ?: 0
            }
            TD2_REGEX.find(tdBlocks.getOrNull(2).orEmpty())?.let {
                subtitleRaw.subLanguageName = it.groupValues.getOrNull(1).orEmpty()
                subtitleRaw.subLanguageUrl = it.groupValues.getOrNull(2).orEmpty()
                subtitleRaw.subLanguageId = subtitleRaw.subLanguageUrl.substringAfterLast("-")
            }
            TD3_REGEX.find(tdBlocks.getOrNull(3).orEmpty())?.let {
                subtitleRaw.cd = it.groupValues.getOrNull(1).orEmpty().trim()
            }
            TD4_REGEX.find(tdBlocks.getOrNull(4).orEmpty())?.let {
                subtitleRaw.publishedIsoTimeStamp = it.groupValues.getOrNull(1).orEmpty()
                it.groupValues.getOrNull(2).orEmpty().let { dateTime ->
                    subtitleRaw.publishedDate = dateTime.substringBefore(" ")
                    subtitleRaw.publishedTime = dateTime.substringAfter(" ")
                }
            }
            TD5_REGEX.find(tdBlocks.getOrNull(5).orEmpty())?.let {
                subtitleRaw.totalDownloads = it.groupValues.getOrNull(1)?.toIntOrNull() ?: 0
                subtitleRaw.subFormat = it.groupValues.getOrNull(2).orEmpty()
            }
            TD6_REGEX.find(tdBlocks.getOrNull(6).orEmpty())?.let {
                subtitleRaw.voteCount = it.groupValues.getOrNull(1)?.toIntOrNull() ?: 0
                subtitleRaw.votes = it.groupValues.getOrNull(2)?.toDoubleOrNull() ?: 0.0
            }
            TD8_REGEX.find(tdBlocks.getOrNull(8).orEmpty())?.let {
                subtitleRaw.imdbVoteCount = it.groupValues.getOrNull(1)?.toIntOrNull() ?: 0
                subtitleRaw.imdbUrl = it.groupValues.getOrNull(2).orEmpty()
                subtitleRaw.imdbRating = it.groupValues.getOrNull(3)?.toDoubleOrNull() ?: .0
                subtitleRaw.imdbId = subtitleRaw.imdbUrl.split("/").getOrNull(4).orEmpty()
            }
            TD9_REGEX.find(tdBlocks.getOrNull(9).orEmpty())?.let {
                subtitleRaw.uploaderProfileUrl = it.groupValues.getOrNull(1).orEmpty()
                subtitleRaw.uploaderName = it.groupValues.getOrNull(2).orEmpty()
                subtitleRaw.uploaderId =
                    subtitleRaw.uploaderProfileUrl.substringAfterLast("-").toLongOrNull() ?: 0
            }
            subtitleRaw.toSubtitle()
        }.toList()
    }

    private fun validateUrl(url: String) {
        if (!url.contains("https://www.opensubtitles.org/en/search/")) {
            throw OpenSubtitleException(
                ERROR_CODE_INVALID_URL,
                "Invalid URL: Use ${OpenSubtitle.URLBuilder::class.java} to build url"
            )
        }

        if (!url.contains("moviename-") && !url.contains("imdbid-")) {
            throw OpenSubtitleException(
                ERROR_CODE_INVALID_SEARCH_PARAMS,
                "Invalid search parameters: URL must contain either title or imdb id"
            )
        }
    }

    /**
     * Constructs the final paginated URL based on the current page number.
     *
     * OpenSubtitles uses an offset-based pagination system, where:
     * - Page 1 has no offset.
     * - Page 2 starts from offset 40.
     * - Page 3 starts from offset 80, and so on.
     *
     * The formula `(page - 1) * OFFSET` ensures we skip the correct number of results
     * to reach the desired page. For example:
     * - Page 2 → offset = (2 - 1) * 40 = 40
     * - Page 3 → offset = (3 - 1) * 40 = 80
     *
     * @param url The base search URL (without any offset).
     * @param page The target page number (1-based).
     * @return A URL with the correct offset appended for pagination.
     */
    private fun buildFinalUrl(url: String, page: Int): String {
        return if (page > 1) {
            // to get page 2 data we need only passe to OFFSET to get next page data so always -1
            // one to get correct page data
            "$url/offset-${(page - 1) * OFFSET}"
        } else {
            url
        }
    }

}