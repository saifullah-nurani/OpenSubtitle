package io.github.saifullah.nurani.opensubtitle.data

import android.os.Parcel
import android.os.Parcelable

data class SubtitleData(
    val page: Int,
    val totalPage: Int,
    val subtitles: List<Subtitle>
) : Parcelable {
    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(page)
        dest.writeInt(totalPage)
        dest.writeTypedList(subtitles)
    }

    companion object CREATOR : Parcelable.Creator<SubtitleData> {
        override fun createFromParcel(parcel: Parcel): SubtitleData {
            val page = parcel.readInt()
            val totalPage = parcel.readInt()
            val subtitles = parcel.createTypedArrayList(Subtitle) ?: emptyList()
            return SubtitleData(page, totalPage, subtitles)
        }

        override fun newArray(size: Int): Array<SubtitleData?> = arrayOfNulls(size)
    }
}