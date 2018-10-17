package com.apia22018.sportactivities.data.listActivities

import android.os.Parcel
import android.os.Parcelable

data class Activities(
        val title: String = "",
        val description: String = "",
        val totalSeats: Int = 0,
        val occupiedSeats: Int = 0,
        val activityDate: Long = 0,
        val city: String = "",
        val streetName: String = "",
        val createdBy: String = ""
) : Parcelable {
    var activityId: String = ""

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!) {
        activityId = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeInt(totalSeats)
        parcel.writeInt(occupiedSeats)
        parcel.writeLong(activityDate)
        parcel.writeString(city)
        parcel.writeString(streetName)
        parcel.writeString(createdBy)
        parcel.writeString(activityId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Activities> {
        override fun createFromParcel(parcel: Parcel): Activities {
            return Activities(parcel)
        }

        override fun newArray(size: Int): Array<Activities?> {
            return arrayOfNulls(size)
        }
    }

    fun emptySeaterinos(): String {
        val emptySeats = totalSeats - occupiedSeats
        return emptySeats.toString()
    }
}