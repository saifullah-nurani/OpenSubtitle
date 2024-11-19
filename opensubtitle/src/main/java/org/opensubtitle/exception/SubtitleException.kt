package org.opensubtitle.exception

import androidx.annotation.IntDef

class SubtitleException(
    val errorCode: @ErrorCode Int,
    message: String? = null,
    throwable: Throwable? = null
) :
    Exception(message, throwable) {
    companion object {
        const val ERROR_CODE_UNSPECIFIED = 5001
        const val ERROR_CODE_INVALID_URL = 5002
        const val ERROR_CODE_INVALID_SEARCH_PARAMS = 5003
        const val ERROR_CODE_PARSING_FAILURE = 5004
        const val ERROR_CODE_NETWORK_FAILURE = 5005
        const val ERROR_CODE_INVALID_PAGE_NUMBER = 5101
    }

    @Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
    @IntDef(
        ERROR_CODE_UNSPECIFIED,
        ERROR_CODE_INVALID_URL,
        ERROR_CODE_INVALID_SEARCH_PARAMS,
        ERROR_CODE_PARSING_FAILURE,
        ERROR_CODE_NETWORK_FAILURE,
        ERROR_CODE_INVALID_PAGE_NUMBER
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class ErrorCode
}
