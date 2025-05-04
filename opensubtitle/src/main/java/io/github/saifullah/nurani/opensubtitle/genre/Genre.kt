package io.github.saifullah.nurani.opensubtitle.genre

import androidx.annotation.StringDef
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.ADULT
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.ADVENTURE
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.ANIMATION
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.BIOGRAPHY
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.COMEDY
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.CRIME
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.DOCUMENTARY
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.DRAM
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.DRAMA
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.FAMILY
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.FANTASY
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.FILM_NOIR
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.GAME_SHOW
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.HISTORY
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.HORROR
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.MUSIC
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.MUSICAL
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.MYSTERY
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.NEWS
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.REALITY_TV
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.ROMANCE
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.SCI_FI
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.SHORT
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.SPORT
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.TALK_SHOW
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.THRILL
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.THRILLER
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.WAR
import io.github.saifullah.nurani.opensubtitle.genre.Genre.Companion.WESTERN

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
@StringDef(
    ADULT, ADVENTURE, ANIMATION, BIOGRAPHY, COMEDY, CRIME, DOCUMENTARY, DRAM, DRAMA, FAMILY,
    FANTASY, FILM_NOIR, GAME_SHOW, HISTORY, HORROR, MUSIC, MUSICAL, MYSTERY, NEWS, REALITY_TV,
    ROMANCE, SCI_FI, SHORT, SPORT, TALK_SHOW, THRILL, THRILLER, WAR, WESTERN
)
annotation class Genre {
    companion object {
        const val ADULT = "adult"
        const val ADVENTURE = "adventure"
        const val ANIMATION = "animation"
        const val BIOGRAPHY = "biography"
        const val COMEDY = "comedy"
        const val CRIME = "crime"
        const val DOCUMENTARY = "documentary"
        const val DRAM = "dram"
        const val DRAMA = "drama"
        const val FAMILY = "family"
        const val FANTASY = "fantasy"
        const val FILM_NOIR = "film_noir"
        const val GAME_SHOW = "game_show"
        const val HISTORY = "history"
        const val HORROR = "horror"
        const val MUSIC = "music"
        const val MUSICAL = "musical"
        const val MYSTERY = "mystery"
        const val NEWS = "news"
        const val REALITY_TV = "reality_tv"
        const val ROMANCE = "romance"
        const val SCI_FI = "sci_fi"
        const val SHORT = "short"
        const val SPORT = "sport"
        const val TALK_SHOW = "talk_show"
        const val THRILL = "thrill"
        const val THRILLER = "thriller"
        const val WAR = "war"
        const val WESTERN = "western"
    }
}
