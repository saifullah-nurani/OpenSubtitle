package io.github.saifullah.nurani.opensubtitle.endpoint

import io.github.saifullah.nurani.opensubtitle.core.HttpClient
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Abstract base class for all OpenSubtitles API endpoint implementations.
 *
 * This class enforces the use of a [HttpClient] for making network requests
 * and serves as a foundation for specific endpoint behaviors such as search, detail fetching, etc.
 *
 * Each subclass must provide its own instance of [httpClient], which is used to perform
 * HTTP operations in a lightweight and pluggable way, suitable for testing and customization.
 *
 */
internal abstract class EndPoint {
    /**
     * The HTTP client used to perform network requests for this endpoint.
     * Must be provided by concrete subclasses.
     */
    protected abstract val httpClient: HttpClient

}
