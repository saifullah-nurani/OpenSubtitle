package io.github.saifullah.nurani.opensubtitle.language

import androidx.annotation.StringDef

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
@StringDef(
    SubLanguageId.ALL,
    SubLanguageId.SELECTED,
    SubLanguageId.AFRIKAANS,
    SubLanguageId.ALBANIAN,
    SubLanguageId.AMHARIC,
    SubLanguageId.ARABIC,
    SubLanguageId.ARAGONESE,
    SubLanguageId.ARMENIAN,
    SubLanguageId.ASSAMESE,
    SubLanguageId.ASTURIAN,
    SubLanguageId.AZERBAIJANI,
    SubLanguageId.BASQUE,
    SubLanguageId.BELARUSIAN,
    SubLanguageId.BENGALI,
    SubLanguageId.BOSNIAN,
    SubLanguageId.BRETON,
    SubLanguageId.BULGARIAN,
    SubLanguageId.BURMESE,
    SubLanguageId.CATALAN,
    SubLanguageId.CANTONESE_CANTONESE,
    SubLanguageId.CHINESE_SIMPLIFIED,
    SubLanguageId.CHINESE_TRADITIONAL,
    SubLanguageId.CHINESE_BILINGUAL,
    SubLanguageId.CROATIAN,
    SubLanguageId.CZECH,
    SubLanguageId.DANISH,
    SubLanguageId.DARI,
    SubLanguageId.DUTCH,
    SubLanguageId.ENGLISH,
    SubLanguageId.ESPERANTO,
    SubLanguageId.ESTONIAN,
    SubLanguageId.EXTREMADURAN,
    SubLanguageId.FINNISH,
    SubLanguageId.FRENCH,
    SubLanguageId.GAELIC,
    SubLanguageId.GALICIAN,
    SubLanguageId.GEORGIAN,
    SubLanguageId.GERMAN,
    SubLanguageId.GREEK,
    SubLanguageId.HEBREW,
    SubLanguageId.HINDI,
    SubLanguageId.HUNGARIAN,
    SubLanguageId.ICELANDIC,
    SubLanguageId.IGBO,
    SubLanguageId.INDONESIAN,
    SubLanguageId.INTERLINGUA,
    SubLanguageId.IRISH,
    SubLanguageId.ITALIAN,
    SubLanguageId.JAPANESE,
    SubLanguageId.KANNADA,
    SubLanguageId.KAZAKH,
    SubLanguageId.KHMER,
    SubLanguageId.KOREAN,
    SubLanguageId.KURDISH,
    SubLanguageId.LATVIAN,
    SubLanguageId.LITHUANIAN,
    SubLanguageId.LUXEMBOURGISH,
    SubLanguageId.MACEDONIAN,
    SubLanguageId.MALAY,
    SubLanguageId.MALAYAM,
    SubLanguageId.MANIPURI,
    SubLanguageId.MARATHI,
    SubLanguageId.MONGOLIAN,
    SubLanguageId.MONTENEGRIN,
    SubLanguageId.NAVAJO,
    SubLanguageId.NEPALI,
    SubLanguageId.NORTHERN_SAMI,
    SubLanguageId.NORWEGIAN,
    SubLanguageId.OCCITAN,
    SubLanguageId.ODIA,
    SubLanguageId.PERSIAN,
    SubLanguageId.POLISH,
    SubLanguageId.PORTUGUESE,
    SubLanguageId.PORTUGUESE_BR,
    SubLanguageId.PORTUGUESE_MZ,
    SubLanguageId.PUSHTO,
    SubLanguageId.ROMANIAN,
    SubLanguageId.RUSSIAN,
    SubLanguageId.SANTALI,
    SubLanguageId.SERBIAN,
    SubLanguageId.SINDHI,
    SubLanguageId.SINHALESE,
    SubLanguageId.SLOVAK,
    SubLanguageId.SLOVENIAN,
    SubLanguageId.SOMALI,
    SubLanguageId.SPANISH,
    SubLanguageId.SPANISH_EU,
    SubLanguageId.SPANISH_LA,
    SubLanguageId.SWAHILI,
    SubLanguageId.SWEDISH,
    SubLanguageId.SYRIAC,
    SubLanguageId.TAGALOG,
    SubLanguageId.TAMIL,
    SubLanguageId.TATAR,
    SubLanguageId.TELUGU,
    SubLanguageId.THAI,
    SubLanguageId.TOKI_PONA,
    SubLanguageId.TURKISH,
    SubLanguageId.TURKMEN,
    SubLanguageId.URKAINIAN,
    SubLanguageId.URDU,
    SubLanguageId.UZBEK,
    SubLanguageId.VIETNAMESE,
    SubLanguageId.WELSH
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