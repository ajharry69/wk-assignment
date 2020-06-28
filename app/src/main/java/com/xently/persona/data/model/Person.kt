package com.xently.persona.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xently.persona.R
import com.xently.persona.utils.JSON_CONVERTER

@Entity(tableName = "people")
data class Person(
    @PrimaryKey(autoGenerate = false) val id: Int = 1,
    val name: String,
    val gender: Gender,
    val imageUrl: String? = null,
    @Embedded(prefix = "location_") val location: Location = Location()
) : Parcelable {
    enum class Gender(@StringRes val nameRes: Int) {
        FEMALE(R.string.female),
        MALE(R.string.male)
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        Gender.valueOf(parcel.readString()!!),
        parcel.readString(),
        parcel.readParcelable(Location::class.java.classLoader) ?: Location()
    )

    override fun toString(): String = JSON_CONVERTER.toJson(this)

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + location.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (id != other.id) return false
        if (name != other.name) return false
        if (gender != other.gender) return false
        if (imageUrl != other.imageUrl) return false
        if (location != other.location) return false

        return true
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.run {
            writeInt(id)
            writeString(name)
            writeString(gender.name)
            writeString(imageUrl)
            writeParcelable(location, flags)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person = Person(parcel)

        override fun newArray(size: Int): Array<Person?> = arrayOfNulls(size)
    }
}