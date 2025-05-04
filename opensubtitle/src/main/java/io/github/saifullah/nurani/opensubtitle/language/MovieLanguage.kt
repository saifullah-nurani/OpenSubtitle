package io.github.saifullah.nurani.opensubtitle.language

import androidx.annotation.StringDef

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
@StringDef(
    MovieLanguage.AFGHANISTAN,
    MovieLanguage.ALBANIA,
    MovieLanguage.ALGERIA,
    MovieLanguage.AMERICAN_SAMOA,
    MovieLanguage.ANDORRA,
    MovieLanguage.ANGOLA,
    MovieLanguage.ANGUILLA,
    MovieLanguage.ANTARCTICA,
    MovieLanguage.ANTIGUA_AND_BARBUDA,
    MovieLanguage.ARGENTINA,
    MovieLanguage.ARMENIA,
    MovieLanguage.ARUBA,
    MovieLanguage.AUSTRALIA,
    MovieLanguage.AUSTRIA,
    MovieLanguage.AZERBAIJAN,
    MovieLanguage.BAHAMAS,
    MovieLanguage.BAHRAIN,
    MovieLanguage.BANGLADESH,
    MovieLanguage.BELARUS,
    MovieLanguage.BELGIUM,
    MovieLanguage.BENIN,
    MovieLanguage.BERMUDA,
    MovieLanguage.BHUTAN,
    MovieLanguage.BOLIVIA,
    MovieLanguage.BOSNIA_AND_HERZEGOVINA,
    MovieLanguage.BOTSWANA,
    MovieLanguage.BRAZIL,
    MovieLanguage.BRITISH_VIRGIN_ISLANDS,
    MovieLanguage.BRUNEI,
    MovieLanguage.BULGARIA,
    MovieLanguage.BURKINA_FASO,
    MovieLanguage.BURMA,
    MovieLanguage.BURUNDI,
    MovieLanguage.CAMBODIA,
    MovieLanguage.CAMEROON,
    MovieLanguage.CANADA,
    MovieLanguage.CAPE_VERDE,
    MovieLanguage.CAYMAN_ISLANDS,
    MovieLanguage.CHAD,
    MovieLanguage.CHILE,
    MovieLanguage.CHINA,
    MovieLanguage.COLOMBIA,
    MovieLanguage.CONGO,
    MovieLanguage.COSTA_RICA,
    MovieLanguage.COTE_D_IVOIRE,
    MovieLanguage.CROATIA,
    MovieLanguage.CUBA,
    MovieLanguage.CYPRUS,
    MovieLanguage.CZECH_REPUBLIC,
    MovieLanguage.CZECHOSLOVAKIA,
    MovieLanguage.DEMOCRATIC_REPUBLIC_OF_CONGO,
    MovieLanguage.DENMARK,
    MovieLanguage.DJIBOUTI,
    MovieLanguage.DOMINICA,
    MovieLanguage.DOMINICAN_REPUBLIC,
    MovieLanguage.EAST_GERMANY,
    MovieLanguage.ECUADOR,
    MovieLanguage.EGYPT,
    MovieLanguage.EL_SALVADOR,
    MovieLanguage.EQUATORIAL_GUINEA,
    MovieLanguage.ESTONIA,
    MovieLanguage.ETHIOPIA,
    MovieLanguage.FAROE_ISLANDS,
    MovieLanguage.FINLAND,
    MovieLanguage.FRANCE,
    MovieLanguage.GABON,
    MovieLanguage.GAMBIA,
    MovieLanguage.GEORGIA,
    MovieLanguage.GERMANY,
    MovieLanguage.GHANA,
    MovieLanguage.GREECE,
    MovieLanguage.GREENLAND,
    MovieLanguage.GUATEMALA,
    MovieLanguage.GUINEA,
    MovieLanguage.GUINEA_BISSAU,
    MovieLanguage.HAITI,
    MovieLanguage.HONDURAS,
    MovieLanguage.HONG_KONG,
    MovieLanguage.HUNGARY,
    MovieLanguage.ICELAND,
    MovieLanguage.INDIA,
    MovieLanguage.INDONESIA,
    MovieLanguage.IRAN,
    MovieLanguage.IRAQ,
    MovieLanguage.IRELAND,
    MovieLanguage.ISRAEL,
    MovieLanguage.ITALY,
    MovieLanguage.JAMAICA,
    MovieLanguage.JAPAN,
    MovieLanguage.JORDAN,
    MovieLanguage.KAZAKHSTAN,
    MovieLanguage.KENYA,
    MovieLanguage.KOREA,
    MovieLanguage.KOSOVO,
    MovieLanguage.KUWAIT,
    MovieLanguage.KYRGYZSTAN,
    MovieLanguage.LAOS,
    MovieLanguage.LATVIA,
    MovieLanguage.LEBANON,
    MovieLanguage.LESOTHO,
    MovieLanguage.LIBERIA,
    MovieLanguage.LIBYA,
    MovieLanguage.LITHUANIA,
    MovieLanguage.LUXEMBOURG,
    MovieLanguage.MACAU,
    MovieLanguage.MACEDONIA,
    MovieLanguage.MADAGASCAR,
    MovieLanguage.MALAWI,
    MovieLanguage.MALAYSIA,
    MovieLanguage.MALDIVES,
    MovieLanguage.MALI,
    MovieLanguage.MALTA,
    MovieLanguage.MARSHALL_ISLANDS,
    MovieLanguage.MARTINIQUE,
    MovieLanguage.MAURITANIA,
    MovieLanguage.MAURITIUS,
    MovieLanguage.MEXICO,
    MovieLanguage.MOLDOVA,
    MovieLanguage.MONACO,
    MovieLanguage.MONGOLIA,
    MovieLanguage.MONTENEGRO,
    MovieLanguage.MOROCCO,
    MovieLanguage.MOZAMBIQUE,
    MovieLanguage.MYANMAR,
    MovieLanguage.NAMIBIA,
    MovieLanguage.NEPAL,
    MovieLanguage.NETHERLANDS,
    MovieLanguage.NEW_ZEALAND,
    MovieLanguage.NICARAGUA,
    MovieLanguage.NIGER,
    MovieLanguage.NIGERIA,
    MovieLanguage.NORTH_KOREA,
    MovieLanguage.NORWAY,
    MovieLanguage.OMAN,
    MovieLanguage.PAKISTAN,
    MovieLanguage.PALESTINE,
    MovieLanguage.PANAMA,
    MovieLanguage.PARAGUAY,
    MovieLanguage.PERU,
    MovieLanguage.PHILIPPINES,
    MovieLanguage.POLAND,
    MovieLanguage.PORTUGAL,
    MovieLanguage.QATAR,
    MovieLanguage.ROMANIA,
    MovieLanguage.RUSSIA,
    MovieLanguage.RWANDA,
    MovieLanguage.SAUDI_ARABIA,
    MovieLanguage.SENEGAL,
    MovieLanguage.SERBIA,
    MovieLanguage.SINGAPORE,
    MovieLanguage.SLOVAKIA,
    MovieLanguage.SLOVENIA,
    MovieLanguage.SOMALIA,
    MovieLanguage.SOUTH_AFRICA,
    MovieLanguage.SOUTH_KOREA,
    MovieLanguage.SPAIN,
    MovieLanguage.SRI_LANKA,
    MovieLanguage.SUDAN,
    MovieLanguage.SURINAME,
    MovieLanguage.SWAZILAND,
    MovieLanguage.SWEDEN,
    MovieLanguage.SWITZERLAND,
    MovieLanguage.SYRIA,
    MovieLanguage.TAIWAN,
    MovieLanguage.TANZANIA,
    MovieLanguage.THAILAND,
    MovieLanguage.TOGO,
    MovieLanguage.TONGA,
    MovieLanguage.TRINIDAD_AND_TOBAGO,
    MovieLanguage.TUNISIA,
    MovieLanguage.TURKEY,
    MovieLanguage.UGANDA,
    MovieLanguage.UKRAINE,
    MovieLanguage.UNITED_ARAB_EMIRATES,
    MovieLanguage.UNITED_KINGDOM,
    MovieLanguage.UNITED_STATES,
    MovieLanguage.URUGUAY,
    MovieLanguage.UZBEKISTAN,
    MovieLanguage.VENEZUELA,
    MovieLanguage.VIETNAM,
    MovieLanguage.YEMEN,
    MovieLanguage.ZAMBIA,
    MovieLanguage.ZIMBABWE
)
annotation class MovieLanguage {
    companion object {
        const val AFGHANISTAN = "Afghanistan"
        const val ALBANIA = "Albania"
        const val ALGERIA = "Algeria"
        const val AMERICAN_SAMOA = "American Samoa"
        const val ANDORRA = "Andorra"
        const val ANGOLA = "Angola"
        const val ANGUILLA = "Anguilla"
        const val ANTARCTICA = "Antarctica"
        const val ANTIGUA_AND_BARBUDA = "Antigua and Barbuda"
        const val ARGENTINA = "Argentina"
        const val ARMENIA = "Armenia"
        const val ARUBA = "Aruba"
        const val AUSTRALIA = "Australia"
        const val AUSTRIA = "Austria"
        const val AZERBAIJAN = "Azerbaijan"
        const val BAHAMAS = "Bahamas"
        const val BAHRAIN = "Bahrain"
        const val BANGLADESH = "Bangladesh"
        const val BELARUS = "Belarus"
        const val BELGIUM = "Belgium"
        const val BENIN = "Benin"
        const val BERMUDA = "Bermuda"
        const val BHUTAN = "Bhutan"
        const val BOLIVIA = "Bolivia"
        const val BOSNIA_AND_HERZEGOVINA = "Bosnia and Herzegovina"
        const val BOTSWANA = "Botswana"
        const val BRAZIL = "Brazil"
        const val BRITISH_VIRGIN_ISLANDS = "British Virgin Islands"
        const val BRUNEI = "Brunei"
        const val BULGARIA = "Bulgaria"
        const val BURKINA_FASO = "Burkina Faso"
        const val BURMA = "Burma"
        const val BURUNDI = "Burundi"
        const val CAMBODIA = "Cambodia"
        const val CAMEROON = "Cameroon"
        const val CANADA = "Canada"
        const val CAPE_VERDE = "Cape Verde"
        const val CAYMAN_ISLANDS = "Cayman Islands"
        const val CHAD = "Chad"
        const val CHILE = "Chile"
        const val CHINA = "China"
        const val COLOMBIA = "Colombia"
        const val CONGO = "Congo"
        const val COSTA_RICA = "Costa Rica"
        const val COTE_D_IVOIRE = "Côte d'Ivoire"
        const val CROATIA = "Croatia"
        const val CUBA = "Cuba"
        const val CYPRUS = "Cyprus"
        const val CZECH_REPUBLIC = "Czech Republic"
        const val CZECHOSLOVAKIA = "Czechoslovakia"
        const val DEMOCRATIC_REPUBLIC_OF_CONGO = "Democratic Republic of Congo"
        const val DENMARK = "Denmark"
        const val DJIBOUTI = "Djibouti"
        const val DOMINICA = "Dominica"
        const val DOMINICAN_REPUBLIC = "Dominican Republic"
        const val EAST_GERMANY = "East Germany"
        const val ECUADOR = "Ecuador"
        const val EGYPT = "Egypt"
        const val EL_SALVADOR = "El Salvador"
        const val EQUATORIAL_GUINEA = "Equatorial Guinea"
        const val ESTONIA = "Estonia"
        const val ETHIOPIA = "Ethiopia"
        const val FAROE_ISLANDS = "Faroe Islands"
        const val FINLAND = "Finland"
        const val FRANCE = "France"
        const val GABON = "Gabon"
        const val GAMBIA = "Gambia"
        const val GEORGIA = "Georgia"
        const val GERMANY = "Germany"
        const val GHANA = "Ghana"
        const val GREECE = "Greece"
        const val GREENLAND = "Greenland"
        const val GUATEMALA = "Guatemala"
        const val GUINEA = "Guinea"
        const val GUINEA_BISSAU = "Guinea-Bissau"
        const val HAITI = "Haiti"
        const val HONDURAS = "Honduras"
        const val HONG_KONG = "Hong Kong"
        const val HUNGARY = "Hungary"
        const val ICELAND = "Iceland"
        const val INDIA = "India"
        const val INDONESIA = "Indonesia"
        const val IRAN = "Iran"
        const val IRAQ = "Iraq"
        const val IRELAND = "Ireland"
        const val ISRAEL = "Israel"
        const val ITALY = "Italy"
        const val JAMAICA = "Jamaica"
        const val JAPAN = "Japan"
        const val JORDAN = "Jordan"
        const val KAZAKHSTAN = "Kazakhstan"
        const val KENYA = "Kenya"
        const val KOREA = "Korea"
        const val KOSOVO = "Kosovo"
        const val KUWAIT = "Kuwait"
        const val KYRGYZSTAN = "Kyrgyzstan"
        const val LAOS = "Laos"
        const val LATVIA = "Latvia"
        const val LEBANON = "Lebanon"
        const val LESOTHO = "Lesotho"
        const val LIBERIA = "Liberia"
        const val LIBYA = "Libya"
        const val LITHUANIA = "Lithuania"
        const val LUXEMBOURG = "Luxembourg"
        const val MACAU = "Macau"
        const val MACEDONIA = "Macedonia"
        const val MADAGASCAR = "Madagascar"
        const val MALAWI = "Malawi"
        const val MALAYSIA = "Malaysia"
        const val MALDIVES = "Maldives"
        const val MALI = "Mali"
        const val MALTA = "Malta"
        const val MARSHALL_ISLANDS = "Marshall Islands"
        const val MARTINIQUE = "Martinique"
        const val MAURITANIA = "Mauritania"
        const val MAURITIUS = "Mauritius"
        const val MEXICO = "Mexico"
        const val MOLDOVA = "Moldova"
        const val MONACO = "Monaco"
        const val MONGOLIA = "Mongolia"
        const val MONTENEGRO = "Montenegro"
        const val MOROCCO = "Morocco"
        const val MOZAMBIQUE = "Mozambique"
        const val MYANMAR = "Myanmar"
        const val NAMIBIA = "Namibia"
        const val NEPAL = "Nepal"
        const val NETHERLANDS = "Netherlands"
        const val NEW_ZEALAND = "New Zealand"
        const val NICARAGUA = "Nicaragua"
        const val NIGER = "Niger"
        const val NIGERIA = "Nigeria"
        const val NORTH_KOREA = "North Korea"
        const val NORWAY = "Norway"
        const val OMAN = "Oman"
        const val PAKISTAN = "Pakistan"
        const val PALESTINE = "Palestine"
        const val PANAMA = "Panama"
        const val PARAGUAY = "Paraguay"
        const val PERU = "Peru"
        const val PHILIPPINES = "Philippines"
        const val POLAND = "Poland"
        const val PORTUGAL = "Portugal"
        const val QATAR = "Qatar"
        const val ROMANIA = "Romania"
        const val RUSSIA = "Russia"
        const val RWANDA = "Rwanda"
        const val SAUDI_ARABIA = "Saudi Arabia"
        const val SENEGAL = "Senegal"
        const val SERBIA = "Serbia"
        const val SINGAPORE = "Singapore"
        const val SLOVAKIA = "Slovakia"
        const val SLOVENIA = "Slovenia"
        const val SOMALIA = "Somalia"
        const val SOUTH_AFRICA = "South Africa"
        const val SOUTH_KOREA = "South Korea"
        const val SPAIN = "Spain"
        const val SRI_LANKA = "Sri Lanka"
        const val SUDAN = "Sudan"
        const val SURINAME = "Suriname"
        const val SWAZILAND = "Swaziland"
        const val SWEDEN = "Sweden"
        const val SWITZERLAND = "Switzerland"
        const val SYRIA = "Syria"
        const val TAIWAN = "Taiwan"
        const val TANZANIA = "Tanzania"
        const val THAILAND = "Thailand"
        const val TOGO = "Togo"
        const val TONGA = "Tonga"
        const val TRINIDAD_AND_TOBAGO = "Trinidad and Tobago"
        const val TUNISIA = "Tunisia"
        const val TURKEY = "Turkey"
        const val UGANDA = "Uganda"
        const val UKRAINE = "Ukraine"
        const val UNITED_ARAB_EMIRATES = "United Arab Emirates"
        const val UNITED_KINGDOM = "United Kingdom"
        const val UNITED_STATES = "United States"
        const val URUGUAY = "Uruguay"
        const val UZBEKISTAN = "Uzbekistan"
        const val VENEZUELA = "Venezuela"
        const val VIETNAM = "Vietnam"
        const val YEMEN = "Yemen"
        const val ZAMBIA = "Zambia"
        const val ZIMBABWE = "Zimbabwe"
    }
}

