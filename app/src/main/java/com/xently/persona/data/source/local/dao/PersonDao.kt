package com.xently.persona.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.xently.persona.data.model.Person

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPerson(person: Person)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPeople(people: List<Person>)

    @Query("SELECT * FROM people")
    suspend fun getPeople(): List<Person>

    @Query("SELECT * FROM people")
    fun getObservablePeople(): LiveData<List<Person>>
}