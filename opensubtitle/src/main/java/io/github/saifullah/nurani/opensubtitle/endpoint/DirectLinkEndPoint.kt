package io.github.saifullah.nurani.opensubtitle.endpoint

import io.github.saifullah.nurani.opensubtitle.core.HttpClient
import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException
import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException.Companion.ERROR_CODE_IO_EXCEPTION
import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException.Companion.ERROR_CODE_PARSING_FAILURE
import io.github.saifullah.nurani.opensubtitle.extension.executeWithHandling

/**
 * Endpoint responsible for extracting a direct subtitle download link from OpenSubtitles by subtitle ID.
 *
 * It performs a background HTTP GET request to fetch the subtitle detail page,
 * then parses the HTML content using a regular expression to extract the direct download URL.
 */
internal class DirectLinkEndPoint(
    override val httpClient: HttpClient,
) : EndPoint() {

    private companion object {
        // Regular expression to find the direct subtitle download link
        val REGEX_DIRECT_LINK = Regex("""moviehash.*?href="([^"]+)""")
    }

    /**
     * Invokes the endpoint to get the direct download link of a subtitle using its ID.
     *
     * @param id The ID of the subtitle on OpenSubtitles.
     * @return The direct download URL as a String, or `null` if extraction fails.
     */

    @Throws(OpenSubtitleException::class)
    operator fun invoke(id: Long): String? {
        return executeWithHandling {
            val url = "https://www.opensubtitles.org/en/subtitles/$id"
            val response = httpClient.get(url)
            if (response.statusCode == 200 && response.bodyAsText != null) {
                REGEX_DIRECT_LINK.find(response.bodyAsText!!)?.groups?.get(1)?.value
                    ?: throw OpenSubtitleException(
                        ERROR_CODE_PARSING_FAILURE,
                        "Failed to extract direct download link for subtitle ID $id.",
                        null
                    )
            } else throw OpenSubtitleException(
                ERROR_CODE_IO_EXCEPTION, "No response received from OpenSubtitles for ID $id.", null
            )
        }
    }
}
