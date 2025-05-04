package io.github.saifullah.nurani.opensubtitle.core

/**
 * Represents an HTTP response containing status, headers, and optional response body content.
 *
 * @property statusCode The HTTP status code returned by the server (e.g., 200, 404).
 * @property bodyAsText The response body content as a string, present if the request was successful.
 */
data class HttpResponse(
    val statusCode: Int,

    /** The response body as a string, or null if the request failed. */
    val bodyAsText: String?,
)

