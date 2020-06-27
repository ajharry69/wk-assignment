package com.xently.persona.data.model

import android.os.Parcel
import android.os.Parcelable

data class Location(
    val latitude: Float = 0f,
    val longitude: Float = 0f,
    val altitude: Float = 0f,
    val name: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.run {
            writeFloat(latitude)
            writeFloat(longitude)
            writeFloat(altitude)
            writeString(name)
        }
    }

    override fun describeContents(): Int = 0

    override fun hashCode(): Int {
        var result = latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + altitude.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Location

        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (altitude != other.altitude) return false
        if (name != other.name) return false

        return true
    }

    companion object CREATOR : Parcelable.Creator<Location> {
        override fun createFromParcel(parcel: Parcel): Location = Location(parcel)

        override fun newArray(size: Int): Array<Location?> = arrayOfNulls(size)
    }

}