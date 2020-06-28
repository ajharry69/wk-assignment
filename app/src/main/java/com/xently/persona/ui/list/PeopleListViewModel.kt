package com.xently.persona.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.xently.persona.data.TaskResult
import com.xently.persona.data.model.Person
import com.xently.persona.data.repository.person.IPersonRepository
import com.xently.persona.data.source.SourceOrDestination
import com.xently.persona.data.source.SourceOrDestination.LOCAL
import kotlinx.coroutines.launch

class PeopleListViewModel @ViewModelInject constructor(private val repository: IPersonRepository) :
    ViewModel() {
    private val _peopleResults = MutableLiveData<TaskResult<List<Person>>>()
    val peopleResults: LiveData<TaskResult<List<Person>>>
        get() = _peopleResults

    fun getPeople() {
        _peopleResults.value = TaskResult.Loading
        viewModelScope.launch {
            _peopleResults.value = repository.getPeople()
        }
    }

    fun getObservablePeople(source: SourceOrDestination = LOCAL) = liveData {
        emitSource(repository.getObservablePeople(source))
    }
}