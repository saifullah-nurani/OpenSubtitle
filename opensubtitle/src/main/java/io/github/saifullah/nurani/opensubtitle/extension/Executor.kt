package io.github.saifullah.nurani.opensubtitle.extension

import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException
import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException.Companion.ERROR_CODE_INVALID_SEARCH_PARAMS
import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException.Companion.ERROR_CODE_INVALID_URL
import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException.Companion.ERROR_CODE_IO_EXCEPTION
import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException.Companion.ERROR_CODE_UNSPECIFIED
import java.io.IOException
import java.net.MalformedURLException
import java.util.concurrent.ExecutorService

/**
 * Executes a given action synchronously and handles exceptions by mapping them to OpenSubtitleException.
 *
 * @param action The operation to execute.
 * @return The result of the operation.
 * @throws OpenSubtitleException if any known or unknown error occurs.
 */
internal inline fun <R> executeWithHandling(
    crossinline action: () -> R,
): R? {
    return try {
        action()
    } catch (e: OpenSubtitleException) {
        throw e
    } catch (e: MalformedURLException) {
        throw OpenSubtitleException(
            ERROR_CODE_INVALID_URL, "Invalid URL format", e
        )
    } catch (e: IOException) {
        throw OpenSubtitleException(
            ERROR_CODE_IO_EXCEPTION, "I/O error occurred: ${e.message}", e
        )
    } catch (e: IllegalArgumentException) {
        throw OpenSubtitleException(
            ERROR_CODE_INVALID_SEARCH_PARAMS, "Invalid search parameters: ${e.message}", e
        )
    } catch (e: Exception) {
        throw OpenSubtitleException(
            ERROR_CODE_UNSPECIFIED, "Unknown error occurred", e
        )
    }
}
