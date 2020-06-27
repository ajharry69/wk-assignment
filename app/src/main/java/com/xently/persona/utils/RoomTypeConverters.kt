package com.xently.persona.utils

import androidx.room.TypeConverter
import com.xently.persona.data.model.Person.Gender
import java.util.*

class GenderTypeConverter {
    @TypeConverter
    fun fromGenderToString(gender: Gender): String = gender.name

    @TypeConverter
    fun fromStringToGender(gender: String): Gender = Gender.valueOf(gender.toUpperCase(Locale.ROOT))
}