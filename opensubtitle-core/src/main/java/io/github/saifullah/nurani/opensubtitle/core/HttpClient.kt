package io.github.saifullah.nurani.opensubtitle.core

import java.io.Closeable
import java.io.IOException

/**
 * A lightweight and minimal HTTP client abstraction for performing basic GET requests.
 *
 * This interface provides a for executing HTTP requests using platform-native
 * implementations like `HttpURLConnection`. It is designed to be lightweight, customizable, and safe for use
 * in resource-constrained environments or libraries.
 *
 * @see Closeable
 */
interface HttpClient : Closeable {

    companion object {
        /**
         * The default user agent used by the HTTP client if none is provided explicitly.
         *
         * This user agent mimics a modern desktop browser for compatibility with websites
         * that serve different content based on the client type.
         */
        const val DEFAULT_USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"
    }

    /**
     * Whether this HTTP client has been closed.
     */
    val isClosed: Boolean

    /**
     * Executes an HTTP GET request to the specified URL and returns a [HttpResponse] containing
     * the status code, headers, input stream (for successful responses), and error stream (for failures).
     *
     * The caller is responsible for closing any non-null streams in the returned [HttpResponse].
     *
     * @param url The fully-qualified URL to fetch.
     * @return A [HttpResponse] containing the server response
     * @throws IOException If the request fails due to a network error, timeout, or other I/O issue.
     */
    @Throws(IOException::class)
    fun get(url: String): HttpResponse

    /**
     * Closes the HTTP client and releases any open resources or streams.
     *
     * This method must be called after a request to ensure all connections are properly cleaned up.
     * Once closed, the client cannot be reused.
     *
     * @throws IllegalStateException if the client is already closed.
     */
    override fun close()

}
