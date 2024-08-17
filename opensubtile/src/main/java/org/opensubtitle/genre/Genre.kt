package org.opensubtitle.genre

import androidx.annotation.StringDef
import org.opensubtitle.genre.Genre.Companion.ADULT
import org.opensubtitle.genre.Genre.Companion.ADVENTURE
import org.opensubtitle.genre.Genre.Companion.ANIMATION
import org.opensubtitle.genre.Genre.Companion.BIOGRAPHY
import org.opensubtitle.genre.Genre.Companion.COMEDY
import org.opensubtitle.genre.Genre.Companion.CRIME
import org.opensubtitle.genre.Genre.Companion.DOCUMENTARY
import org.opensubtitle.genre.Genre.Companion.DRAM
import org.opensubtitle.genre.Genre.Companion.DRAMA
import org.opensubtitle.genre.Genre.Companion.FAMILY
import org.opensubtitle.genre.Genre.Companion.FANTASY
import org.opensubtitle.genre.Genre.Companion.FILM_NOIR
import org.opensubtitle.genre.Genre.Companion.GAME_SHOW
import org.opensubtitle.genre.Genre.Companion.HISTORY
import org.opensubtitle.genre.Genre.Companion.HORROR
import org.opensubtitle.genre.Genre.Companion.MUSIC
import org.opensubtitle.genre.Genre.Companion.MUSICAL
import org.opensubtitle.genre.Genre.Companion.MYSTERY
import org.opensubtitle.genre.Genre.Companion.NEWS
import org.opensubtitle.genre.Genre.Companion.REALITY_TV
import org.opensubtitle.genre.Genre.Companion.ROMANCE
import org.opensubtitle.genre.Genre.Companion.SCI_FI
import org.opensubtitle.genre.Genre.Companion.SHORT
import org.opensubtitle.genre.Genre.Companion.SPORT
import org.opensubtitle.genre.Genre.Companion.TALK_SHOW
import org.opensubtitle.genre.Genre.Companion.THRILL
import org.opensubtitle.genre.Genre.Companion.THRILLER
import org.opensubtitle.genre.Genre.Companion.WAR
import org.opensubtitle.genre.Genre.Companion.WESTERN

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
