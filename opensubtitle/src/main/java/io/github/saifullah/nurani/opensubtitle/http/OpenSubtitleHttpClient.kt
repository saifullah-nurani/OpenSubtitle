package io.github.saifullah.nurani.opensubtitle.http

import io.github.saifullah.nurani.opensubtitle.core.HttpClient
import io.github.saifullah.nurani.opensubtitle.core.HttpResponse
import io.github.saifullah.nurani.opensubtitle.http.OpenSubtitleHttpClient.Companion.default
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class OpenSubtitleHttpClient(private val config: Config) : HttpClient {

    constructor() : this(default)

    companion object {
        private const val DEFAULT_CONNECT_TIMEOUT = 60_000
        private const val DEFAULT_READ_TIMEOUT = 60_000

        internal val default = Config(
            connectTimeout = DEFAULT_CONNECT_TIMEOUT,
            readTimeout = DEFAULT_READ_TIMEOUT,
            followRedirects = true,
            userAgent = HttpClient.DEFAULT_USER_AGENT
        )
    }

    data class Config(
        val readTimeout: Int,
        val connectTimeout: Int,
        val followRedirects: Boolean,
        val userAgent: String,
    )

    // Manually close URL Connection so always false
    override val isClosed: Boolean get() = false

    override fun get(url: String): HttpResponse {
        val finalUrl = resolveFinalRedirectUrl(url)
        val connection = if (config.followRedirects) {
            finalUrl.openConnection() as HttpURLConnection
        } else {
            URL(url).openConnection() as HttpURLConnection
        }

        connection.apply {
            requestMethod = "GET"
            connectTimeout = config.connectTimeout
            readTimeout = config.readTimeout
            instanceFollowRedirects = false
            setRequestProperty("User-Agent", config.userAgent)
            connect()
        }

        val responseCode = connection.responseCode
        val body = try {
            connection.inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            connection.errorStream?.bufferedReader()?.use { it.readText() }
        }
        connection.disconnect()
        return HttpResponse(statusCode = responseCode, bodyAsText = body)
    }


    override fun close() {
        // No-op: HttpURLConnection does not hold shared or reusable resources
    }

    @Throws(IOException::class)
    private fun resolveFinalRedirectUrl(urlStr: String): URL {
        var url = URL(urlStr)
        var redirectCount = 0

        while (redirectCount++ < 5) {
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = config.connectTimeout
                readTimeout = config.readTimeout
                instanceFollowRedirects = false
                setRequestProperty("User-Agent", config.userAgent)
            }

            val responseCode = connection.responseCode
            if (responseCode in 300..399) {
                val location = connection.getHeaderField("Location")
                    ?: throw IOException("Redirect location is missing")
                connection.disconnect()
                url = URL(url, location) // Handles relative redirects
            } else {
                connection.disconnect()
                return url // Final resolved URL
            }
        }

        throw IOException("Too many redirects")
    }

}


/**
 * Creates an [HttpClient] with custom configurations using a DSL-style builder.
 *
 * Example:
 * ```
 * val client = openSubtitleHttpClient {
 *     connectTimeout = 30_000
 *     readTimeout = 30_000
 *     followRedirects = false
 * }
 * ```
 *
 * @param config Lambda receiver for configuring the [OpenSubtitleHttpClient.Config]
 * @return Configured [HttpClient] instance
 */
fun openSubtitleHttpClient(config: OpenSubtitleHttpClient.Config.() -> Unit): HttpClient {
    return OpenSubtitleHttpClient(default.apply(config))
}

/**
 * Creates an [HttpClient] with default OkHttp configurations.
 *
 * Default features:
 * - 60s timeouts (connect/read/write)
 * - User-Agent header set
 *
 * @return Preconfigured [HttpClient] instance
 */
fun openSubtitleHttpClient(): HttpClient {
    return OpenSubtitleHttpClient(default)
}
