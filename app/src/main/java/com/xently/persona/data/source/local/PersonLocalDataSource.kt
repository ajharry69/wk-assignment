package com.xently.persona.data.source.local

import com.xently.persona.data.TaskResult
import com.xently.persona.data.TaskResult.Success
import com.xently.persona.data.model.Person
import com.xently.persona.data.source.IPersonDataSource
import com.xently.persona.data.source.Source
import com.xently.persona.data.source.local.dao.PersonDao

class PersonLocalDataSource internal constructor(private val dao: PersonDao) : IPersonDataSource {
    override suspend fun addPerson(person: Person): TaskResult<Person> {
        dao.addPerson(person)
        return Success(person) // TODO: Get from dao.getPerson(...)
    }

    override suspend fun addPeople(people: List<Person>): TaskResult<List<Person>> {
        dao.getPeople()
        return getPeople()
    }

    override suspend fun getPeople() = Success(dao.getPeople())

    override suspend fun getObservablePeople(source: Source) = dao.getObservablePeople()
}