package org.opensubtitle

import androidx.annotation.IntDef

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
@IntDef(OpenSubtitle.MOVIE, OpenSubtitle.TV_SERIES)
annotation class SearchOnly
