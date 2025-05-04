package io.github.saifullah.nurani.opensubtitle.data

import android.os.Build
import android.os.Parcel
import android.os.Parcelable

data class Subtitle(
    val id: Long,
    val title: String,
    val pageUrl: String,
    val releaseYear: Int,
    val subFormat: String,
    val cd: String,
    val zipFileUrl: String,
    val totalDownloads: Int,
    val publishedIsoTimeStamp: String,
    val publishedDate: String,
    val publishedTime: String,
    val votes: Double,
    val voteCount: Int,
    val subLanguage: SubLanguage,
    val imdb: Imdb?,
    val uploader: Uploader?
) : Parcelable {

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(id)
        dest.writeString(title)
        dest.writeString(pageUrl)
        dest.writeInt(releaseYear)
        dest.writeString(subFormat)
        dest.writeString(cd)
        dest.writeString(zipFileUrl)
        dest.writeInt(totalDownloads)
        dest.writeString(publishedIsoTimeStamp)
        dest.writeString(publishedDate)
        dest.writeString(publishedTime)
        dest.writeDouble(votes)
        dest.writeInt(voteCount)
        dest.writeParcelable(subLanguage, flags)
        dest.writeParcelable(imdb, flags)
        dest.writeParcelable(uploader, flags)
    }

    companion object CREATOR : Parcelable.Creator<Subtitle> {
        override fun createFromParcel(parcel: Parcel): Subtitle {
            val id = parcel.readLong()
            val title = parcel.readString() ?: ""
            val url = parcel.readString() ?: ""
            val releaseYear = parcel.readInt()
            val subFormat = parcel.readString() ?: ""
            val cd = parcel.readString() ?: ""
            val zipFileUrl = parcel.readString() ?: ""
            val totalDownloads = parcel.readInt()
            val dateTime = parcel.readString() ?: ""
            val publishedDate = parcel.readString() ?: ""
            val publishedTime = parcel.readString() ?: ""
            val votes = parcel.readDouble()
            val voteCount = parcel.readInt()
            val subLanguage = parcel.readParcelable(SubLanguage::class.java)!!
            val imdb = parcel.readParcelable(Imdb::class.java)!!
            val uploader = parcel.readParcelable(Uploader::class.java)!!
            return Subtitle(
                id,
                title,
                url,
                releaseYear,
                subFormat,
                cd,
                zipFileUrl,
                totalDownloads,
                dateTime,
                publishedDate,
                publishedTime,
                votes,
                voteCount,
                subLanguage,
                imdb,
                uploader
            )
        }

        override fun newArray(size: Int): Array<Subtitle?> = arrayOfNulls(size)
    }

    data class Imdb(
        val id: String,
        val rating: Double,
        val url: String,
        val voteCount: Int,
    ) : Parcelable {
        override fun describeContents(): Int = 0

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(id)
            dest.writeDouble(rating)
            dest.writeString(url)
            dest.writeInt(voteCount)
        }

        companion object CREATOR : Parcelable.Creator<Imdb> {
            override fun createFromParcel(parcel: Parcel): Imdb {
                val id = parcel.readString() ?: ""
                val rating = parcel.readDouble()
                val url = parcel.readString() ?: ""
                val voteCount = parcel.readInt()
                return Imdb(id, rating, url, voteCount)
            }

            override fun newArray(size: Int): Array<Imdb?> = arrayOfNulls(size)
        }
    }

    data class Uploader(
        val id: Long,
        val name: String,
        val profileUrl: String,
    ) : Parcelable {
        override fun describeContents(): Int = 0

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeLong(id)
            dest.writeString(name)
            dest.writeString(profileUrl)
        }


        companion object CREATOR : Parcelable.Creator<Uploader> {
            override fun createFromParcel(parcel: Parcel): Uploader {
                val id = parcel.readLong()
                val name = parcel.readString() ?: ""
                val profileUrl = parcel.readString() ?: ""
                return Uploader(id, name, profileUrl)
            }

            override fun newArray(size: Int): Array<Uploader?> = arrayOfNulls(size)
        }
    }

    data class SubLanguage(
        val name: String,
        val id: String,
        val url: String,
    ) : Parcelable {
        override fun describeContents(): Int = 0

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(name)
            dest.writeString(id)
            dest.writeString(url)
        }

        override fun toString(): String {
            return "SubLanguage(name='$name', id='$id', url='$url')"
        }

        companion object CREATOR : Parcelable.Creator<SubLanguage> {
            override fun createFromParcel(parcel: Parcel): SubLanguage {
                val language = parcel.readString() ?: ""
                val languageId = parcel.readString() ?: ""
                val languageUrl = parcel.readString() ?: ""
                return SubLanguage(language, languageId, languageUrl)
            }

            override fun newArray(size: Int): Array<SubLanguage?> = arrayOfNulls(size)
        }
    }
}


private fun <T : Parcelable?> Parcel.readParcelable(clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        readParcelable(clazz.classLoader, clazz)
    } else {
        @Suppress("DEPRECATION")
        readParcelable(clazz.classLoader)
    }
}