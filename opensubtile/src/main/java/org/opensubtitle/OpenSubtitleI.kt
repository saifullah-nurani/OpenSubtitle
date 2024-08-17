package org.opensubtitle

import androidx.annotation.IntRange

internal interface OpenSubtitleI {
    suspend fun search(url: String, @IntRange(from = 1) page: Int = 1, response: OpenSubtitle.Response? = null)
    suspend fun getDownloadUrl(id: Long): String?
}