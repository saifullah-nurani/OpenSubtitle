package io.github.saifullah.nurani.opensubtitle.okhttp

import io.github.saifullah.nurani.opensubtitle.core.HttpClient
import io.github.saifullah.nurani.opensubtitle.core.HttpResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * An implementation of [HttpClient] that uses OkHttp for performing HTTP GET requests.
 *
 * @param httpClient An instance of [OkHttpClient] to be used for network requests.
 */
class OpenSubtitleOkHttpClient(
    private val httpClient: OkHttpClient,
) : HttpClient {

    companion object {
        const val CONNECT_TIMEOUT = 60_000L
        const val READ_TIMEOUT = 60_000L
        const val WRITE_TIMEOUT = 60_000L

        /**
         * Default client instance with predefined timeouts and headers.
         */
        internal val default: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .followRedirects(true)
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder()
                            .header("User-Agent", HttpClient.DEFAULT_USER_AGENT)
                            .build()
                    )
                }
                .build()
        }
    }

    @Volatile
    private var _isClosed = false
    override val isClosed: Boolean get() = _isClosed

    /**
     * Constructs an instance using the default OkHttpClient configuration.
     */
    constructor() : this(default)

    /**
     * Executes an HTTP GET request to the given URL and returns the response.
     *
     * The caller is responsible for handling the returned [HttpResponse].
     *
     * @param url The target URL.
     * @return An [HttpResponse] containing status code, body, and headers.
     * @throws IOException If the request fails or client is closed.
     */
    @Throws(IOException::class)
    override fun get(url: String): HttpResponse {
        check(!isClosed) { "HttpClient is already closed and cannot be used." }

        val request = Request.Builder().url(url).get().build()
        val response = httpClient.newCall(request).execute()

        if (!response.isSuccessful) {
            response.close()
            throw createHttpException(response)
        }

        val httpCode = response.code
        val body = response.body?.byteStream()?.bufferedReader()?.readText()
        response.close()

        return HttpResponse(
            statusCode = httpCode,
            bodyAsText = body
        )
    }

    /**
     * Closes the HTTP client and releases any open resources or streams.
     *
     * This method must be called after all requests are complete.
     * Once closed, the client cannot be reused.
     */
    override fun close() {
        if (isClosed) throw IllegalStateException("OkHttpClient has already been closed.")
        try {
            httpClient.dispatcher.executorService.shutdownNow()
            httpClient.connectionPool.evictAll()
            httpClient.cache?.close()
        } finally {
            _isClosed = true
        }
    }


    /**
     * Builds a detailed [IOException] with response code and error body.
     *
     * @param response The non-successful response.
     * @return An [IOException] with detailed info.
     */
    private fun createHttpException(response: okhttp3.Response): IOException {
        val message = "HTTP ${response.code}: ${response.body?.string() ?: "No error body"}"
        return IOException(message).apply {
            addSuppressed(IOException("Request URL: ${response.request.url}"))
        }
    }
}

/**
 * DSL builder to create an [HttpClient] with custom [OkHttpClient] configurations.
 *
 * Example:
 * ```
 * val client = openSubtitleOkHttpClient {
 *     connectTimeout(30, TimeUnit.SECONDS)
 *     followRedirects(true)
 * }
 * ```
 *
 * @param builder Lambda to configure the [OkHttpClient.Builder].
 * @return A customized [HttpClient] instance.
 */
fun openSubtitleOkHttpClient(builder: OkHttpClient.Builder.() -> Unit): HttpClient {
    val customClient = OpenSubtitleOkHttpClient.default.newBuilder().apply(builder).build()
    return OpenSubtitleOkHttpClient(customClient)
}

/**
 * Returns an [HttpClient] using the default OkHttp configuration.
 */
fun openSubtitleOkHttpClient(): HttpClient {
    return OpenSubtitleOkHttpClient(OpenSubtitleOkHttpClient.default)
}
