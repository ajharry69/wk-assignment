package com.xently.persona.data.repository.person

import com.xently.persona.data.data
import com.xently.persona.data.model.Person
import com.xently.persona.data.source.IPersonDataSource
import com.xently.persona.data.source.SourceOrDestination
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class PersonRepository internal constructor(
    private val local: IPersonDataSource,
    private val remote: IPersonDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IPersonRepository {
    override suspend fun addPerson(
        person: Person,
        photo: File?,
        destination: SourceOrDestination?
    ) = withContext(ioDispatcher) {
        if (destination == SourceOrDestination.LOCAL) {
            local.addPerson(person, photo, destination)
        } else {
            remote.addPerson(person, photo, destination).run {
                local.addPerson(data ?: person, photo, destination)
            }
        }
    }

    override suspend fun addPeople(people: List<Person>) = withContext(ioDispatcher) {
        remote.addPeople(people).run {
            local.addPeople(if (data.isNullOrEmpty()) people else data!!)
        }
    }

    override suspend fun getPeople() = withContext(ioDispatcher) {
        remote.getPeople().run {
            data?.let {
                local.addPeople(it) // Cache people records
//                local.getPeople() // Unnecessary - done by above
            } ?: this
        }
    }

    override suspend fun getObservablePeople(source: SourceOrDestination) = when (source) {
        SourceOrDestination.REMOTE -> remote.getObservablePeople(source)
        SourceOrDestination.LOCAL -> local.getObservablePeople(source)
    }
}