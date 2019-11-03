package com.remember.password.data

import android.os.Parcel
import android.os.Parcelable

data class UiRecord(
    var id: Int,
    var title: String?,
    var userName: String?,
    var pwd: String? = "",
    var isHeader: Boolean = false,
    var showPassword: Boolean = false
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(userName)
        parcel.writeString(pwd)
        parcel.writeByte(if (isHeader) 1 else 0)
        parcel.writeByte(if (showPassword) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UiRecord> {
        override fun createFromParcel(parcel: Parcel): UiRecord {
            return UiRecord(parcel)
        }

        override fun newArray(size: Int): Array<UiRecord?> {
            return arrayOfNulls(size)
        }
    }
}