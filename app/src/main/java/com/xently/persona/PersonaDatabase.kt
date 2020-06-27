package com.xently.persona

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.xently.persona.data.model.Person
import com.xently.persona.data.source.local.dao.PersonDao
import com.xently.persona.utils.GenderTypeConverter

@Database(
    entities = [Person::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(GenderTypeConverter::class)
abstract class PersonaDatabase : RoomDatabase() {
    abstract val personDao: PersonDao
}