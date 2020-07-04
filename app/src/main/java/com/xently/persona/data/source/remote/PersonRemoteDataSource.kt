package com.xently.persona.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.xently.persona.data.TaskResult.Success
import com.xently.persona.data.listData
import com.xently.persona.data.model.Person
import com.xently.persona.data.Destination
import com.xently.persona.data.source.IPersonDataSource
import com.xently.persona.data.Source
import com.xently.persona.data.source.remote.services.PersonService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class PersonRemoteDataSource internal constructor(
    private val service: PersonService,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseRemoteDataSource(ioDispatcher), IPersonDataSource {
    override suspend fun addPerson(
        person: Person,
        photo: File?,
        destination: Destination?
    ) = sendRequest(suspend {
        if (photo == null) {
            service.addPerson(person)
        } else {
            val picture =
                MultipartBody.Part.createFormData("photo", photo.name, photo.asRequestBody())
            val human = MultipartBody.Part.createFormData("person", person.toString())
            service.addPerson(picture, human)
        }
    })

    override suspend fun addPeople(people: List<Person>) = Success(people)

    override suspend fun getPeople() = sendRequest(suspend { service.getPeople() })

    override suspend fun getObservablePeople(source: Source) =
        MutableLiveData(getPeople().listData)
}