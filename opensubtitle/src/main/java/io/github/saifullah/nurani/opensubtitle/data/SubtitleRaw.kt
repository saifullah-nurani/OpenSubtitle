package io.github.saifullah.nurani.opensubtitle.data

internal data class SubtitleRaw(
    var id: Long = 0,
    var title: String = "",
    var pageUrl: String = "",
    var releaseYear: Int = 0,
    var subFormat: String = "",
    var cd: String = "",
    var totalDownloads: Int = 0,
    var publishedIsoTimeStamp: String = "",
    var publishedDate: String = "",
    var publishedTime: String = "",
    var votes: Double = .0,
    var voteCount: Int = 0,
    var imdbId: String = "",
    var imdbRating: Double = .0,
    var imdbUrl: String = "",
    var imdbVoteCount: Int = 0,
    var subLanguageName: String = "",
    var subLanguageId: String = "",
    var subLanguageUrl: String = "",
    var uploaderId: Long = 0,
    var uploaderName: String = "",
    var uploaderProfileUrl: String = "",
) {
    override fun toString(): String {
        return "SubtitleRaw(id=$id, title='$title', pageUrl='$pageUrl', releaseYear=$releaseYear, subFormat='$subFormat', cd='$cd', totalDownloads=$totalDownloads, publishedIsoTimeStamp='$publishedIsoTimeStamp', publishedDate='$publishedDate', publishedTime='$publishedTime', votes=$votes, voteCount=$voteCount, imdbId='$imdbId', imdbRating=$imdbRating, imdbUrl='$imdbUrl', subLanguageName='$subLanguageName', subLanguageId='$subLanguageId', subLanguageUrl='$subLanguageUrl', uploaderId=$uploaderId, uploaderName='$uploaderName', uploaderProfileUrl='$uploaderProfileUrl')"
    }
}

internal fun SubtitleRaw.toSubtitle(): Subtitle {
    return Subtitle(
        id = id,
        title = title,
        pageUrl = "https://www.opensubtitles.org$pageUrl",
        releaseYear = releaseYear,
        subFormat = subFormat,
        cd = cd,
        zipFileUrl = "https://www.opensubtitles.org/en/subtitleserve/sub/$id",
        totalDownloads = totalDownloads,
        publishedIsoTimeStamp = publishedIsoTimeStamp,
        publishedDate = publishedDate,
        publishedTime = publishedTime,
        votes = votes,
        voteCount = voteCount,
        subLanguage = Subtitle.SubLanguage(
            name = subLanguageName,
            id = subLanguageId,
            url = "https://www.opensubtitles.org$subLanguageUrl"
        ),
        imdb = if (imdbUrl.isNotEmpty()) Subtitle.Imdb(
            id = imdbId,
            rating = imdbRating,
            url = imdbUrl,
            voteCount = imdbVoteCount
        ) else null,
        uploader = if (uploaderProfileUrl.isNotBlank()) Subtitle.Uploader(
            id = uploaderId,
            name = uploaderName,
            profileUrl = "https://www.opensubtitles.org$uploaderProfileUrl"
        ) else null,
    )
}