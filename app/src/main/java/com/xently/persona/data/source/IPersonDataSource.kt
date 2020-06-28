package com.xently.persona.data.source

import androidx.lifecycle.LiveData
import com.xently.persona.data.TaskResult
import com.xently.persona.data.model.Person
import com.xently.persona.data.source.SourceOrDestination.LOCAL
import java.io.File

interface IPersonDataSource {
    suspend fun addPerson(
        person: Person,
        photo: File? = null,
        destination: SourceOrDestination? = null
    ): TaskResult<Person>

    suspend fun addPeople(people: List<Person>): TaskResult<List<Person>>

    suspend fun getPeople(): TaskResult<List<Person>>

    suspend fun getObservablePeople(source: SourceOrDestination = LOCAL): LiveData<List<Person>>
}