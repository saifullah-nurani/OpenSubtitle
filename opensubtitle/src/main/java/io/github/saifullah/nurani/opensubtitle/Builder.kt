package io.github.saifullah.nurani.opensubtitle

import androidx.annotation.IntRange

interface Builder {
    /**
     * Sets the title of the movie.
     *
     * @param title The title of the movie.
     * @return The builder instance for chaining.
     */
    fun setTitle(title: String): Builder

    /**
     * Sets the subtitle language ID.
     *
     * @param subLanguageId The subtitle language ID.
     * @return The builder instance for chaining.
     */
    fun setSubLanguage(subLanguageId: String): Builder

    /**
     * Sets whether to search only.
     *
     * @param searchOnly valid param is [SearchOnly.Movies],[SearchOnly.TvSeries].
     * @return The builder instance for chaining.
     */
    fun setSearchOnly(searchOnly: SearchOnly): Builder

    /**
     * Sets the subtitle format.
     *
     * @param subFormat The subtitle format.
     * @return The builder instance for chaining.
     */
    fun setSubFormat(subFormat: String): Builder

    /**
     * Sets the IMDb ID of the movie.
     *
     * @param imdbId The IMDb ID.
     * @return The builder instance for chaining.
     */
    fun setImdbId(imdbId: String): Builder

    /**
     * Sets the season number.
     *
     * @param season The season number (must be greater than or equal to 1).
     * @return The builder instance for chaining.
     */
    fun setSeason(@IntRange(from = 1) season: Int): Builder

    /**
     * Sets the episode number.
     *
     * @param episode The episode number (must be greater than or equal to 1).
     * @return The builder instance for chaining.
     */
    fun setEpisode(@IntRange(from = 1) episode: Int): Builder

    /**
     * Sets the number of CDs.
     *
     * @param cds The number of CDs accepted value `1`,`2`,`3`.
     * @return The builder instance for chaining.
     */
    fun setCDs(cds: Int): Builder

    /**
     * Sets the file size in bytes.
     *
     * @param fileSize The size of the file in bytes.
     * @return The builder instance for chaining.
     */
    fun setFileBytesSize(fileSize: Long): Builder

    /**
     * Sets the movie rating along with the operator.
     *
     * @param operator The operator for rating.
     * @param rating The rating value.
     * @return The builder instance for chaining.
     */
    fun setMovieRating(operator: Int, rating: Float): Builder

    /**
     * Sets the movie release year along with the operator.
     *
     * @param operator The operator for year.
     * @param year The release year.
     * @return The builder instance for chaining.
     */
    fun setMovieYear(operator: Int, year: Int): Builder

    /**
     * Sets the genre of the movie.
     *
     * @param genre The genre of the movie.
     * @return The builder instance for chaining.
     */
    fun setGenre(genre: String): Builder

    /**
     * Sets the language of the movie.
     *
     * @param language The language of the movie.
     * @return The builder instance for chaining.
     */
    fun setMovieLanguage(language: String): Builder

    /**
     * Sets the country of the movie.
     *
     * @param country The country of the movie.
     * @return The builder instance for chaining.
     */
    fun setMovieCountry(country: String): Builder

    /**
     * Sets the frames per second (FPS) of the movie.
     *
     * @param fps The frames per second.
     * @return The builder instance for chaining.
     */
    fun setFPS(fps: Double): Builder
}

/**
 * Builds a URL using a custom DSL-style [Builder] block.
 *
 * This function initializes an instance of [OpenSubtitle.URLBuilder],
 * applies the provided [builder] lambda to configure the URL components,
 * and returns the resulting URL as a [String].
 *
 * Example usage:
 * ```
 * val url = urlBuilder {
 *    setTitle("ek tha tiger")
 *    subLanguageId(SubLanguageId.ENGLISH)
 *    build()
 * }
 * ```
 *
 * @param builder A lambda with receiver to configure the [Builder].
 * @return The final URL string.
 */
fun buildOpenSubtitleUrl(builder: Builder.() -> Unit): String {
    return OpenSubtitle.URLBuilder().apply(builder).build()
}

