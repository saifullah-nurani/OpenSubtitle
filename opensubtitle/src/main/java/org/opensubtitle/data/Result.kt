package org.opensubtitle.data

import android.os.Parcel
import android.os.Parcelable

data class Result(
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

    companion object CREATOR : Parcelable.Creator<Result> {
        override fun createFromParcel(parcel: Parcel): Result {
            val page = parcel.readInt()
            val totalPage = parcel.readInt()
            val subtitles = parcel.createTypedArrayList(Subtitle.CREATOR) ?: emptyList()
            return Result(page, totalPage, subtitles)
        }

        override fun newArray(size: Int): Array<Result?> = arrayOfNulls(size)
    }
}