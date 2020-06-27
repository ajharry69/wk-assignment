package com.xently.persona.data.source

import androidx.lifecycle.LiveData
import com.xently.persona.data.TaskResult
import com.xently.persona.data.model.Person

interface IPersonDataSource {
    suspend fun addPerson(person: Person): TaskResult<Person>

    suspend fun addPeople(people: List<Person>): TaskResult<List<Person>>

    suspend fun getPeople(): TaskResult<List<Person>>

    suspend fun getObservablePeople(source: Source = Source.LOCAL): LiveData<List<Person>>
}