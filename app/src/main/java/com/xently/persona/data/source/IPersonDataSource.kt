package com.xently.persona.data.source

import androidx.lifecycle.LiveData
import com.xently.persona.data.Destination
import com.xently.persona.data.Source
import com.xently.persona.data.TaskResult
import com.xently.persona.data.model.Person
import com.xently.persona.data.Source.LOCAL
import java.io.File

interface IPersonDataSource {
    suspend fun addPerson(
        person: Person,
        photo: File? = null,
        destination: Destination? = null
    ): TaskResult<Person>

    suspend fun addPeople(people: List<Person>): TaskResult<List<Person>>

    suspend fun getPeople(): TaskResult<List<Person>>

    suspend fun getObservablePeople(source: Source = LOCAL): LiveData<List<Person>>
}