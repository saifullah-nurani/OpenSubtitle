package io.github.saifullah.nurani.opensubtitle.exception

import androidx.annotation.IntDef

/**
 * Exception class used for representing various Subtitles-related errors in a structured and type-safe way.
 *
 * This exception allows categorizing errors using predefined error codes, making it easier to debug and handle
 * different failure scenarios programmatically.
 *
 * @param errorCode the error code indicating the specific type of failure.
 * @param message an optional error message describing the failure.
 * @param throwable an optional cause of the exception.
 *
 * @see ErrorCode for the full list of supported error codes.
 */
class OpenSubtitleException(
    val errorCode: @ErrorCode Int,
    message: String? = null,
    throwable: Throwable? = null,
) : Exception(message, throwable) {

    companion object {
        // General errors (5000–5099)

        /** Represents an unknown or unspecified error. */

        const val ERROR_CODE_UNSPECIFIED = 5001

        /** Indicates that the URL provided is invalid or not properly formatted. */
        const val ERROR_CODE_INVALID_URL = 5002

        /** Indicates missing or invalid search parameters in the request URL. */
        const val ERROR_CODE_INVALID_SEARCH_PARAMS = 5003

        /** Represents an error while parsing response data such as malformed JSON. */
        const val ERROR_CODE_PARSING_FAILURE = 5004

        /** Indicates a network or I/O related error. */
        const val ERROR_CODE_IO_EXCEPTION = 5005

        // API-specific errors (5100–5199)

        /** Indicates that the page number provided for pagination is invalid or out of range. */
        const val ERROR_CODE_INVALID_PAGE_NUMBER = 5101
    }

    /**
     * Annotation that restricts valid values for [errorCode] to known constants.
     *
     * This helps ensure compile-time safety when assigning or checking error codes.
     */
    @Target(AnnotationTarget.TYPE)
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(
        ERROR_CODE_UNSPECIFIED,
        ERROR_CODE_INVALID_URL,
        ERROR_CODE_INVALID_SEARCH_PARAMS,
        ERROR_CODE_PARSING_FAILURE,
        ERROR_CODE_IO_EXCEPTION,
        ERROR_CODE_INVALID_PAGE_NUMBER
    )
    annotation class ErrorCode
}
