package org.opensubtitle.language

import androidx.annotation.StringDef
import org.opensubtitle.language.SubLanguageId.Companion.AFRIKAANS
import org.opensubtitle.language.SubLanguageId.Companion.ALBANIAN
import org.opensubtitle.language.SubLanguageId.Companion.ALL
import org.opensubtitle.language.SubLanguageId.Companion.AMHARIC
import org.opensubtitle.language.SubLanguageId.Companion.ARABIC
import org.opensubtitle.language.SubLanguageId.Companion.ARAGONESE
import org.opensubtitle.language.SubLanguageId.Companion.ARMENIAN
import org.opensubtitle.language.SubLanguageId.Companion.ASSAMESE
import org.opensubtitle.language.SubLanguageId.Companion.ASTURIAN
import org.opensubtitle.language.SubLanguageId.Companion.AZERBAIJANI
import org.opensubtitle.language.SubLanguageId.Companion.BASQUE
import org.opensubtitle.language.SubLanguageId.Companion.BELARUSIAN
import org.opensubtitle.language.SubLanguageId.Companion.BENGALI
import org.opensubtitle.language.SubLanguageId.Companion.BOSNIAN
import org.opensubtitle.language.SubLanguageId.Companion.BRETON
import org.opensubtitle.language.SubLanguageId.Companion.BULGARIAN
import org.opensubtitle.language.SubLanguageId.Companion.BURMESE
import org.opensubtitle.language.SubLanguageId.Companion.CANTONESE_CANTONESE
import org.opensubtitle.language.SubLanguageId.Companion.CATALAN
import org.opensubtitle.language.SubLanguageId.Companion.CHINESE_BILINGUAL
import org.opensubtitle.language.SubLanguageId.Companion.CHINESE_SIMPLIFIED
import org.opensubtitle.language.SubLanguageId.Companion.CHINESE_TRADITIONAL
import org.opensubtitle.language.SubLanguageId.Companion.CROATIAN
import org.opensubtitle.language.SubLanguageId.Companion.CZECH
import org.opensubtitle.language.SubLanguageId.Companion.DANISH
import org.opensubtitle.language.SubLanguageId.Companion.DARI
import org.opensubtitle.language.SubLanguageId.Companion.DUTCH
import org.opensubtitle.language.SubLanguageId.Companion.ENGLISH
import org.opensubtitle.language.SubLanguageId.Companion.ESPERANTO
import org.opensubtitle.language.SubLanguageId.Companion.ESTONIAN
import org.opensubtitle.language.SubLanguageId.Companion.EXTREMADURAN
import org.opensubtitle.language.SubLanguageId.Companion.FINNISH
import org.opensubtitle.language.SubLanguageId.Companion.FRENCH
import org.opensubtitle.language.SubLanguageId.Companion.GAELIC
import org.opensubtitle.language.SubLanguageId.Companion.GALICIAN
import org.opensubtitle.language.SubLanguageId.Companion.GEORGIAN
import org.opensubtitle.language.SubLanguageId.Companion.GERMAN
import org.opensubtitle.language.SubLanguageId.Companion.GREEK
import org.opensubtitle.language.SubLanguageId.Companion.HEBREW
import org.opensubtitle.language.SubLanguageId.Companion.HINDI
import org.opensubtitle.language.SubLanguageId.Companion.HUNGARIAN
import org.opensubtitle.language.SubLanguageId.Companion.ICELANDIC
import org.opensubtitle.language.SubLanguageId.Companion.IGBO
import org.opensubtitle.language.SubLanguageId.Companion.INDONESIAN
import org.opensubtitle.language.SubLanguageId.Companion.INTERLINGUA
import org.opensubtitle.language.SubLanguageId.Companion.IRISH
import org.opensubtitle.language.SubLanguageId.Companion.ITALIAN
import org.opensubtitle.language.SubLanguageId.Companion.JAPANESE
import org.opensubtitle.language.SubLanguageId.Companion.KANNADA
import org.opensubtitle.language.SubLanguageId.Companion.KAZAKH
import org.opensubtitle.language.SubLanguageId.Companion.KHMER
import org.opensubtitle.language.SubLanguageId.Companion.KOREAN
import org.opensubtitle.language.SubLanguageId.Companion.KURDISH
import org.opensubtitle.language.SubLanguageId.Companion.LATVIAN
import org.opensubtitle.language.SubLanguageId.Companion.LITHUANIAN
import org.opensubtitle.language.SubLanguageId.Companion.LUXEMBOURGISH
import org.opensubtitle.language.SubLanguageId.Companion.MACEDONIAN
import org.opensubtitle.language.SubLanguageId.Companion.MALAY
import org.opensubtitle.language.SubLanguageId.Companion.MALAYAM
import org.opensubtitle.language.SubLanguageId.Companion.MANIPURI
import org.opensubtitle.language.SubLanguageId.Companion.MARATHI
import org.opensubtitle.language.SubLanguageId.Companion.MONGOLIAN
import org.opensubtitle.language.SubLanguageId.Companion.MONTENEGRIN
import org.opensubtitle.language.SubLanguageId.Companion.NAVAJO
import org.opensubtitle.language.SubLanguageId.Companion.NEPALI
import org.opensubtitle.language.SubLanguageId.Companion.NORTHERN_SAMI
import org.opensubtitle.language.SubLanguageId.Companion.NORWEGIAN
import org.opensubtitle.language.SubLanguageId.Companion.OCCITAN
import org.opensubtitle.language.SubLanguageId.Companion.ODIA
import org.opensubtitle.language.SubLanguageId.Companion.PERSIAN
import org.opensubtitle.language.SubLanguageId.Companion.POLISH
import org.opensubtitle.language.SubLanguageId.Companion.PORTUGUESE
import org.opensubtitle.language.SubLanguageId.Companion.PORTUGUESE_BR
import org.opensubtitle.language.SubLanguageId.Companion.PORTUGUESE_MZ
import org.opensubtitle.language.SubLanguageId.Companion.PUSHTO
import org.opensubtitle.language.SubLanguageId.Companion.ROMANIAN
import org.opensubtitle.language.SubLanguageId.Companion.RUSSIAN
import org.opensubtitle.language.SubLanguageId.Companion.SANTALI
import org.opensubtitle.language.SubLanguageId.Companion.SELECTED
import org.opensubtitle.language.SubLanguageId.Companion.SERBIAN
import org.opensubtitle.language.SubLanguageId.Companion.SINDHI
import org.opensubtitle.language.SubLanguageId.Companion.SINHALESE
import org.opensubtitle.language.SubLanguageId.Companion.SLOVAK
import org.opensubtitle.language.SubLanguageId.Companion.SLOVENIAN
import org.opensubtitle.language.SubLanguageId.Companion.SOMALI
import org.opensubtitle.language.SubLanguageId.Companion.SPANISH
import org.opensubtitle.language.SubLanguageId.Companion.SPANISH_EU
import org.opensubtitle.language.SubLanguageId.Companion.SPANISH_LA
import org.opensubtitle.language.SubLanguageId.Companion.SWAHILI
import org.opensubtitle.language.SubLanguageId.Companion.SWEDISH
import org.opensubtitle.language.SubLanguageId.Companion.SYRIAC
import org.opensubtitle.language.SubLanguageId.Companion.TAGALOG
import org.opensubtitle.language.SubLanguageId.Companion.TAMIL
import org.opensubtitle.language.SubLanguageId.Companion.TATAR
import org.opensubtitle.language.SubLanguageId.Companion.TELUGU
import org.opensubtitle.language.SubLanguageId.Companion.THAI
import org.opensubtitle.language.SubLanguageId.Companion.TOKI_PONA
import org.opensubtitle.language.SubLanguageId.Companion.TURKISH
import org.opensubtitle.language.SubLanguageId.Companion.TURKMEN
import org.opensubtitle.language.SubLanguageId.Companion.URDU
import org.opensubtitle.language.SubLanguageId.Companion.URKAINIAN
import org.opensubtitle.language.SubLanguageId.Companion.UZBEK
import org.opensubtitle.language.SubLanguageId.Companion.VIETNAMESE
import org.opensubtitle.language.SubLanguageId.Companion.WELSH

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
@StringDef(
    ALL,
    SELECTED,
    AFRIKAANS,
    ALBANIAN,
    AMHARIC,
    ARABIC,
    ARAGONESE,
    ARMENIAN,
    ASSAMESE,
    ASTURIAN,
    AZERBAIJANI,
    BASQUE,
    BELARUSIAN,
    BENGALI,
    BOSNIAN,
    BRETON,
    BULGARIAN,
    BURMESE,
    CATALAN,
    CANTONESE_CANTONESE,
    CHINESE_SIMPLIFIED,
    CHINESE_TRADITIONAL,
    CHINESE_BILINGUAL,
    CROATIAN,
    CZECH,
    DANISH,
    DARI,
    DUTCH,
    ENGLISH,
    ESPERANTO,
    ESTONIAN,
    EXTREMADURAN,
    FINNISH,
    FRENCH,
    GAELIC,
    GALICIAN,
    GEORGIAN,
    GERMAN,
    GREEK,
    HEBREW,
    HINDI,
    HUNGARIAN,
    ICELANDIC,
    IGBO,
    INDONESIAN,
    INTERLINGUA,
    IRISH,
    ITALIAN,
    JAPANESE,
    KANNADA,
    KAZAKH,
    KHMER,
    KOREAN,
    KURDISH,
    LATVIAN,
    LITHUANIAN,
    LUXEMBOURGISH,
    MACEDONIAN,
    MALAY,
    MALAYAM,
    MANIPURI,
    MARATHI,
    MONGOLIAN,
    MONTENEGRIN,
    NAVAJO,
    NEPALI,
    NORTHERN_SAMI,
    NORWEGIAN,
    OCCITAN,
    ODIA,
    PERSIAN,
    POLISH,
    PORTUGUESE,
    PORTUGUESE_BR,
    PORTUGUESE_MZ,
    PUSHTO,
    ROMANIAN,
    RUSSIAN,
    SANTALI,
    SERBIAN,
    SINDHI,
    SINHALESE,
    SLOVAK,
    SLOVENIAN,
    SOMALI,
    SPANISH,
    SPANISH_EU,
    SPANISH_LA,
    SWAHILI,
    SWEDISH,
    SYRIAC,
    TAGALOG,
    TAMIL,
    TATAR,
    TELUGU,
    THAI,
    TOKI_PONA,
    TURKISH,
    TURKMEN,
    URKAINIAN,
    URDU,
    UZBEK,
    VIETNAMESE,
    WELSH
)
annotation class SubLanguageId {
    companion object {
        const val ALL = "all"
        const val SELECTED = "abk"
        const val AFRIKAANS = "afr"
        const val ALBANIAN = "alb"
        const val AMHARIC = "amh"
        const val ARABIC = "ara"
        const val ARAGONESE = "arg"
        const val ARMENIAN = "arm"
        const val ASSAMESE = "asm"
        const val ASTURIAN = "ast"
        const val AZERBAIJANI = "aze"
        const val BASQUE = "baq"
        const val BELARUSIAN = "bel"
        const val BENGALI = "ben"
        const val BOSNIAN = "bos"
        const val BRETON = "bre"
        const val BULGARIAN = "bul"
        const val BURMESE = "bur"
        const val CATALAN = "cat"
        const val CANTONESE_CANTONESE = "zhc"
        const val CHINESE_SIMPLIFIED = "chi"
        const val CHINESE_TRADITIONAL = "zht"
        const val CHINESE_BILINGUAL = "zhe"
        const val CROATIAN = "hrv"
        const val CZECH = "cze"
        const val DANISH = "dan"
        const val DARI = "prs"
        const val DUTCH = "dut"
        const val ENGLISH = "eng"
        const val ESPERANTO = "epo"
        const val ESTONIAN = "est"
        const val EXTREMADURAN = "ext"
        const val FINNISH = "fin"
        const val FRENCH = "fre"
        const val GAELIC = "gla"
        const val GALICIAN = "glg"
        const val GEORGIAN = "geo"
        const val GERMAN = "ger"
        const val GREEK = "ell"
        const val HEBREW = "heb"
        const val HINDI = "hin"
        const val HUNGARIAN = "hun"
        const val ICELANDIC = "ice"
        const val IGBO = "ibo"
        const val INDONESIAN = "ind"
        const val INTERLINGUA = "ina"
        const val IRISH = "gle"
        const val ITALIAN = "ita"
        const val JAPANESE = "jpn"
        const val KANNADA = "kan"
        const val KAZAKH = "kaz"
        const val KHMER = "khm"
        const val KOREAN = "kor"
        const val KURDISH = "kur"
        const val LATVIAN = "lav"
        const val LITHUANIAN = "lit"
        const val LUXEMBOURGISH = "ltz"
        const val MACEDONIAN = "mac"
        const val MALAY = "may"
        const val MALAYAM = "mal"
        const val MANIPURI = "mni"
        const val MARATHI = "mar"
        const val MONGOLIAN = "mon"
        const val MONTENEGRIN = "mne"
        const val NAVAJO = "nav"
        const val NEPALI = "nep"
        const val NORTHERN_SAMI = "sme"
        const val NORWEGIAN = "nor"
        const val OCCITAN = "oci"
        const val ODIA = "ori"
        const val PERSIAN = "per"
        const val POLISH = "pol"
        const val PORTUGUESE = "por"
        const val PORTUGUESE_BR = "pob"
        const val PORTUGUESE_MZ = "pom"
        const val PUSHTO = "pus"
        const val ROMANIAN = "rum"
        const val RUSSIAN = "rus"
        const val SANTALI = "sat"
        const val SERBIAN = "scc"
        const val SINDHI = "snd"
        const val SINHALESE = "sin"
        const val SLOVAK = "slo"
        const val SLOVENIAN = "slv"
        const val SOMALI = "som"
        const val SPANISH = "spa"
        const val SPANISH_EU = "spn"
        const val SPANISH_LA = "spl"
        const val SWAHILI = "swa"
        const val SWEDISH = "swe"
        const val SYRIAC = "syr"
        const val TAGALOG = "tgl"
        const val TAMIL = "tam"
        const val TATAR = "tat"
        const val TELUGU = "tel"
        const val THAI = "tha"
        const val TOKI_PONA = "tok"
        const val TURKISH = "tur"
        const val TURKMEN = "tuk"
        const val URKAINIAN = "ukr"
        const val URDU = "urd"
        const val UZBEK = "uzb"
        const val VIETNAMESE = "vie"
        const val WELSH = "wel"
    }
}