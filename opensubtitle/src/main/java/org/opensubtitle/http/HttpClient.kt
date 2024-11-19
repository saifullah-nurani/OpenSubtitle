package org.opensubtitle.http

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

internal open class HttpClient {

    companion object {
        private const val USER_AGENT =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3"

        @JvmStatic
        @Throws(IOException::class)
        fun getFinalRedirectUrl(originalUrl: String): String? {
            var currentUrl = originalUrl
            var redirectCount = 0
            val maxRedirects = 5 // Limit to avoid infinite redirect loops

            while (true) {
                val connection = URL(currentUrl).openConnection() as HttpURLConnection
                connection.instanceFollowRedirects = false // Disable automatic redirects

                try {
                    connection.connect()
                    val responseCode = connection.responseCode

                    if (responseCode in 300..399) {
                        // Check if the response code indicates a redirect
                        val location = connection.getHeaderField("Location")

                        if (location != null) {
                            // Update URL to the new location for the next iteration
                            currentUrl = if (location.startsWith("/")) {
                                // Relative redirect, use the base URL
                                val baseUrl = URL(currentUrl)
                                "${baseUrl.protocol}://${baseUrl.host}$location"
                            } else {
                                // Absolute redirect
                                location
                            }

                            // Increase the redirect count
                            redirectCount++

                            // Stop if max redirects are reached
                            if (redirectCount > maxRedirects) {
                                println("Too many redirects")
                                return null
                            }
                        } else {
                            println("Redirect location not found")
                            return null
                        }
                    } else if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Return the final URL if response is OK (200)
                        return currentUrl
                    } else {
                        // Handle other response codes
                        println("Unexpected response code: $responseCode")
                        return null
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    return null
                } finally {
                    connection.disconnect()
                }
            }
        }
    }

    @Volatile
    private var _httpURLConnection: HttpURLConnection? = null

    @get:Throws(IOException::class, IllegalStateException::class)
    val responseCode: Int
        get() = _httpURLConnection?.responseCode
            ?: throw IllegalStateException("Connection not established")

    /**
     * Establishes a connection to the specified URL. Optional configurations
     * can be applied to the connection via the [httpURLConnection]
     * lambda, allowing properties like headers or request method to
     * be customized. Throws an [IOException] if the connection fails.
     *
     * @param url The URL to connect to.
     * @param httpURLConnection Optional lambda for additional connection
     *    configurations.
     * @throws IOException if an I/O error occurs during the connection setup.
     */
    @Throws(IOException::class)
    fun connect(url: String, httpURLConnection: (HttpURLConnection.() -> Unit)? = null) {
        with(URL(url).openConnection() as HttpURLConnection) {
            requestMethod = "GET" // Use GET method for the request
            setRequestProperty("User-Agent", USER_AGENT) // Set the User-Agent header
            connectTimeout = 5000 // Connection timeout in milliseconds
            readTimeout = 5000 // Read timeout in milliseconds
            // Apply any additional configurations passed in the lambda function
            httpURLConnection?.invoke(this)
            connect() // Establish the connection
            _httpURLConnection = this // Store the connection reference for potential later use
        }
    }

    /**
     * Disconnects the current HTTP connection, releasing resources. No action
     * is taken if there is no active connection.
     *
     * @throws IOException if an I/O error occurs during the disconnection
     *    process.
     */
    @Throws(IOException::class)
    fun disconnect() {
        _httpURLConnection?.disconnect() // Disconnect if a connection exists
        _httpURLConnection = null // Clear the reference
    }

    /**
     * Reads the response from the current HTTP connection as a string. This
     * should be called only after a successful connection is established.
     * Throws an [IllegalStateException] if no connection has been established.
     *
     * @return The response body as a string.
     * @throws IllegalStateException if the connection is not established.
     */
    @Throws(IllegalStateException::class)
    fun readResponse(): String {
        // Ensure there's an active connection; throw an exception if not connected
        if (_httpURLConnection == null) throw IllegalStateException("Connection not established")
        // Use BufferedReader to read the input stream line by line and return the entire response as a string
        BufferedReader(InputStreamReader(_httpURLConnection!!.inputStream)).use { reader ->
            return reader.readText().also {
                reader.close()
            }
        }
    }


}
