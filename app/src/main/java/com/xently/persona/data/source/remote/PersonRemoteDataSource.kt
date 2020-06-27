package com.xently.persona.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.xently.persona.data.TaskResult.Success
import com.xently.persona.data.listData
import com.xently.persona.data.model.Person
import com.xently.persona.data.source.IPersonDataSource
import com.xently.persona.data.source.Source
import com.xently.persona.data.source.remote.services.PersonService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PersonRemoteDataSource internal constructor(
    private val service: PersonService,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRemoteDataSource(ioDispatcher), IPersonDataSource {
    override suspend fun addPerson(person: Person) =
        sendRequest(suspend { service.addPerson(person) })

    override suspend fun addPeople(people: List<Person>) = Success(people)

    override suspend fun getPeople() = sendRequest(suspend { service.getPeople() })

    override suspend fun getObservablePeople(source: Source) = MutableLiveData(getPeople().listData)
}