package org.opensubtitle

import androidx.annotation.IntRange
import org.opensubtitle.OpenSubtitle.SubFormat
import org.opensubtitle.country.Country
import org.opensubtitle.genre.Genre
import org.opensubtitle.language.MovieLanguage
import org.opensubtitle.language.SubLanguageId

internal interface Builder {
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
    fun setSubLanguage(@SubLanguageId subLanguageId: String): Builder

    /**
     * Sets whether to search only.
     *
     * @param searchOnly An integer indicating search only.
     * @return The builder instance for chaining.
     */
    fun setSearchOnly(@OpenSubtitle.SearchOnly searchOnly: Int): Builder

    /**
     * Sets the subtitle format.
     *
     * @param subFormat The subtitle format.
     * @return The builder instance for chaining.
     */
    fun setSubFormat(@SubFormat subFormat: String): Builder

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
    fun setMovieRating(@OpenSubtitle.ComparisonOperator operator: Int, rating: Float): Builder

    /**
     * Sets the movie release year along with the operator.
     *
     * @param operator The operator for year.
     * @param year The release year.
     * @return The builder instance for chaining.
     */
    fun setMovieYear(@OpenSubtitle.ComparisonOperator operator: Int, year: Int): Builder

    /**
     * Sets the genre of the movie.
     *
     * @param genre The genre of the movie.
     * @return The builder instance for chaining.
     */
    fun setGenre(@Genre genre: String): Builder

    /**
     * Sets the language of the movie.
     *
     * @param language The language of the movie.
     * @return The builder instance for chaining.
     */
    fun setMovieLanguage(@MovieLanguage language: String): Builder

    /**
     * Sets the country of the movie.
     *
     * @param country The country of the movie.
     * @return The builder instance for chaining.
     */
    fun setMovieCountry(@Country country: String): Builder

    /**
     * Sets the frames per second (FPS) of the movie.
     *
     * @param fps The frames per second.
     * @return The builder instance for chaining.
     */
    fun setFPS(@OpenSubtitle.Fps fps: Double): Builder

    /**
     * Builds the final URL string.
     *
     * @return The constructed URL string.
     */
    fun build(): String
}

class URLBuilder : Builder {
    private var title: String? = null
    private var subLanguageId: String? = null
    private var searchOnly: Int? = null
    private var subFormat: String? = null
    private var imdbId: String? = null
    private var season: Int? = null
    private var episode: Int? = null
    private var cds: Int? = null
    private var fileSize: Long? = null
    private var movieRating: String? = null
    private var movieYear: String? = null
    private var genre: String? = null
    private var language: String? = null
    private var country: String? = null
    private var fps: Double? = null

    override fun setTitle(title: String): URLBuilder {
        this.title = title.replace("\\s+".toRegex(), "-")
        return this
    }

    override fun setSubLanguage(subLanguageId: String): URLBuilder {
        this.subLanguageId = subLanguageId
        return this
    }

    override fun setSearchOnly(searchOnly: Int): URLBuilder {
        this.searchOnly = searchOnly
        return this
    }

    override fun setSubFormat(subFormat: String): URLBuilder {
        this.subFormat = subFormat
        return this
    }

    override fun setImdbId(imdbId: String): URLBuilder {
        check(imdbId.startsWith("tt", true)) { "Invalid Imdb Id: Imdb id must be start with tt" }
        this.imdbId = imdbId
        return this
    }

    override fun setSeason(season: Int): URLBuilder {
        this.season = season
        return this
    }

    override fun setEpisode(episode: Int): URLBuilder {
        this.episode = episode
        return this
    }

    override fun setCDs(cds: Int): URLBuilder {
        this.cds = cds
        return this
    }

    override fun setFileBytesSize(fileSize: Long): URLBuilder {
        this.fileSize = fileSize
        return this
    }

    override fun setMovieRating(operator: Int, rating: Float): URLBuilder {
        movieRating = "$operator$rating"
        return this
    }

    override fun setMovieYear(operator: Int, year: Int): URLBuilder {
        movieYear = "$operator$year"
        return this
    }

    override fun setGenre(genre: String): URLBuilder {
        this.genre = genre
        return this
    }

    override fun setMovieLanguage(language: String): URLBuilder {
        this.language = language
        return this
    }

    override fun setMovieCountry(country: String): URLBuilder {
        this.country = country
        return this
    }

    override fun setFPS(fps: Double): URLBuilder {
        this.fps = fps
        return this
    }

    override fun build(): String {
        // Construct the URL based on the set parameters.
        // For example:
        return buildString {
            append("https://www.opensubtitles.org/en/search/")
            title?.let { append("moviename-$it/") }
            subLanguageId?.let { append("sublanguageid-$it/") }
            searchOnly?.let { append("searchonly$it-on/") }
            subFormat?.let { append("subformat-$it/") }
            imdbId?.let { append("imdbid-$it/") }
            season?.let { append("season-$it/") }
            episode?.let { append("episode-$it/") }
            cds?.let { append("subsumcd-$it/") }
            fileSize?.let { append("moviebytesize-$it/") }
            movieRating?.let { append("movieimdbratingsign-2/movieimdbrating-$it&/") }
            movieYear?.let { append("movieyearsign-2/movieyear-$it/") }
            genre?.let { append("genre-$it/") }
            language?.let { append("movielanguage-$it/") }
            country?.let { append("moviecountry-$it/") }
            fps?.let { append("moviefps-$it/") }
        }
    }
}

/**
 * Factory function to create a new instance of [URLBuilder].
 *
 * This builder allows you to construct a search URL for the Open Subtitles API
 * by chaining various optional parameters, such as title, IMDb ID,
 *
 * Example usage:
 * ```
 * val url = URLBuilder()
 *     .title("ek tha tiger")
 *     .subLanguageId(SubLanguageId.ENGLISH)
 *     .build()
 * ```
 *
 * @return A new instance of [OpenSubtitle.UrlBuilder].
 */
fun SubtitleURLBuilder() = URLBuilder()
