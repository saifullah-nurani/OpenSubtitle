package io.github.saifullah.nurani.opensubtitle

import androidx.annotation.IntDef
import androidx.annotation.IntRange
import androidx.annotation.StringDef
import io.github.saifullah.nurani.opensubtitle.core.HttpClient
import io.github.saifullah.nurani.opensubtitle.endpoint.DirectLinkEndPoint
import io.github.saifullah.nurani.opensubtitle.endpoint.SearchEndpoint
import io.github.saifullah.nurani.opensubtitle.exception.OpenSubtitleException
import io.github.saifullah.nurani.opensubtitle.genre.Genre
import io.github.saifullah.nurani.opensubtitle.http.OpenSubtitleHttpClient

class OpenSubtitle {

    @StringDef(
        FORMAT_SRT,
        FORMAT_SUB,
        FORMAT_TXT,
        FORMAT_SSA,
        FORMAT_SMI,
        FORMAT_MPL,
        FORMAT_TMP,
        FORMAT_VTT,
        FORMAT_DFXP
    )
    @Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)
    internal annotation class SubFormat

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(
        OPERATOR_IS_EQUAL,
        OPERATOR_IS_LESS_THEN,
        OPERATOR_IS_LESS_THEN_EQUAL,
        OPERATOR_IS_GREATER_THEN,
        OPERATOR_IS_GREATER_THEN_EQUAL,
        OPERATOR_IS_NOT_EQUAL
    )
    internal annotation class ComparisonOperator

    @Retention(AnnotationRetention.SOURCE)
    @StringDef(
        FPS_23_976, FPS_23_980, FPS_24, FPS_25, FPS_29_97, FPS_30, FPS_50, FPS_59_940, FPS_60
    )
    internal annotation class Fps

    companion object {
        // subtitle format
        const val FORMAT_SRT = "srt"
        const val FORMAT_SUB = "sub"
        const val FORMAT_TXT = "txt"
        const val FORMAT_SSA = "ssa"
        const val FORMAT_SMI = "smi"
        const val FORMAT_MPL = "mpl"
        const val FORMAT_TMP = "tmp"
        const val FORMAT_VTT = "vtt"
        const val FORMAT_DFXP = "dfxp"

        // FPS
        const val FPS_23_976 = "23.976"
        const val FPS_23_980 = "23.980"
        const val FPS_24 = "24.0"
        const val FPS_25 = "25.0"
        const val FPS_29_97 = "29.97"
        const val FPS_30 = "30.0"
        const val FPS_50 = "50.0"
        const val FPS_59_940 = "59.940"
        const val FPS_60 = "60.0"

        // comparison operator
        const val OPERATOR_IS_EQUAL = 1
        const val OPERATOR_IS_LESS_THEN = 2
        const val OPERATOR_IS_LESS_THEN_EQUAL = 3
        const val OPERATOR_IS_GREATER_THEN = 4
        const val OPERATOR_IS_GREATER_THEN_EQUAL = 5
        const val OPERATOR_IS_NOT_EQUAL = 6


        /**
         * Indicates whether the underlying HTTP client connection has been closed.
         *
         * Once closed, the client can no longer be used to perform requests.
         */
        val isClosed: Boolean
            get() = httpClient.isClosed

        /**
         * Closes the underlying HTTP client and releases all associated resources.
         *
         * This method must be called to clean up system resources. Once closed,
         * the client cannot be reused, and further operations will throw an exception.
         *
         * @throws IllegalStateException if the client is already closed.
         */
        fun close() {
            httpClient.close()
        }

        @JvmStatic
        @Synchronized
        fun setHttpClient(httpClient: HttpClient) {
            this._httpClient = httpClient
        }

        @Volatile
        private var _httpClient: HttpClient? = null
        internal val httpClient: HttpClient
            get() = synchronized(this) {
                _httpClient ?: OpenSubtitleHttpClient().also {
                    _httpClient = it
                }
            }
    }

    /**
     * Performs a subtitle search using the specified OpenSubtitles URL and page number.
     *
     * @param url The search URL from OpenSubtitles (must be a valid HTML search result page).
     * @param page The page number to fetch, must be >= 1 (default is 1).
     * @param callback A callback to receive the result or an error.
     */
    fun search(url: String, @IntRange(from = 1) page: Int, callback: ResultCallback) {
        try {
            val data = SearchEndpoint(httpClient).invoke(url, page)
            callback.onSuccess(data)
        } catch (e: OpenSubtitleException) {
            callback.onFailure(e)
        }
    }

    /**
     * Retrieves a direct download link for a subtitle file by its OpenSubtitles ID.
     *
     * @param id The subtitle ID from OpenSubtitles.
     * @return A direct download link string if available, or `null` if not found.
     * @throws OpenSubtitleException if an error occurs while fetching or parsing the subtitle page.
     */
    @Throws(OpenSubtitleException::class)
    fun getDirectLink(id: Long): String? {
        return DirectLinkEndPoint(httpClient).invoke(id)
    }

    /**
     * A callback interface to handle subtitle search results from OpenSubtitles.
     *
     * This interface is designed for both Java and Kotlin interoperability.
     * Implement this interface to receive either a successful list of subtitles
     * or an exception in case of failure.
     */
    interface ResultCallback {

        /**
         * Called when the subtitle search is successful.
         *
         * @param result A objects matching the search criteria and pages.
         */
        fun onSuccess(result: io.github.saifullah.nurani.opensubtitle.data.SubtitleData)

        /**
         * Called when an error occurs during the subtitle search process.
         *
         * @param throwable An instance of [OpenSubtitleException] containing the error details.
         */
        fun onFailure(throwable: Throwable)
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
     *     .setTitle("ek tha tiger")
     *     .subLanguageId(SubLanguageId.ENGLISH)
     *     .build()
     * ```
     *
     * @return open subtitle search url.
     */
    class URLBuilder : Builder {
        private var title: String? = null
        private var subLanguageId: String? = null
        private var searchOnly: String? = null
        private var subFormat: String? = null
        private var imdbId: String? = null
        private var season: Int? = null
        private var episode: Int? = null
        private var cds: Int? = null
        private var fileSize: Long? = null
        private var movieRatingOperator: Int? = null
        private var movieRating: String? = null
        private var movieYearOperator: Int? = null
        private var movieYear: String? = null
        private var genre: String? = null
        private var language: String? = null
        private var country: String? = null
        private var fps: Double? = null

        override fun setTitle(title: String) = apply {
            this.title = title.replace("\\s+".toRegex(), "%20")
        }

        override fun setSubLanguage(@io.github.saifullah.nurani.opensubtitle.language.SubLanguageId subLanguageId: String) =
            apply {
                this.subLanguageId = subLanguageId
            }

        override fun setSearchOnly(searchOnly: SearchOnly) = apply {
            this.searchOnly = searchOnly.name.lowercase()
        }

        override fun setSubFormat(@SubFormat subFormat: String) = apply {
            this.subFormat = subFormat
        }

        override fun setImdbId(imdbId: String) = apply {
            check(
                imdbId.startsWith(
                    "tt", true
                )
            ) { "Invalid Imdb Id: Imdb id must be start with tt" }
            this.imdbId = imdbId
        }

        override fun setSeason(@IntRange(from = 1) season: Int) = apply {
            this.season = season
        }

        override fun setEpisode(@IntRange(from = 1) episode: Int) = apply {
            this.episode = episode
        }

        override fun setCDs(cds: Int) = apply {
            this.cds = cds
        }

        override fun setFileBytesSize(fileSize: Long) = apply {
            this.fileSize = fileSize
        }

        override fun setMovieRating(
            @ComparisonOperator operator: Int,
            rating: Float,
        ) = apply {
            movieRatingOperator = operator
            movieRating = "$rating"
        }

        override fun setMovieYear(
            @ComparisonOperator operator: Int,
            year: Int,
        ) = apply {
            movieYearOperator = operator
            movieYear = "$year"
        }

        override fun setGenre(@Genre genre: String) = apply {
            this.genre = genre
        }

        override fun setMovieLanguage(@io.github.saifullah.nurani.opensubtitle.language.MovieLanguage language: String) =
            apply {
                this.language = language
            }

        override fun setMovieCountry(@io.github.saifullah.nurani.opensubtitle.country.Country country: String) =
            apply {
                this.country = country
            }

        override fun setFPS(@Fps fps: Double) = apply {
            this.fps = fps
        }

        fun build(): String {
            // Construct the URL based on the set parameters.
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
                movieRating?.let { append("movieimdbratingsign-$movieRatingOperator/movieimdbrating-$it/") }
                movieYear?.let { append("movieyearsign-$movieYearOperator/movieyear-$it/") }
                genre?.let { append("genre-$it/") }
                language?.let { append("movielanguage-$it/") }
                country?.let { append("moviecountry-$it/") }
                fps?.let { append("moviefps-$it/") }
            }
        }
    }
}