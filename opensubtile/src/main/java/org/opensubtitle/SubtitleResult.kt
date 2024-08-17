package org.opensubtitle

data class SubtitleResult(
    val page: Int, val totalPage: Int, val subtitles: List<Subtitle>
) {
    data class Subtitle(
        val name: String,
        val releaseYear: String,
        val title: String?,
        val subLanguageId: String,
        val dateTime: String,
        val publishedDate: String,
        val publishedTime: String,
        val imdbUrl: String?,
        val imdbRating: Double,
        val downloadUrl: String,
        val id: Long
    )
}
