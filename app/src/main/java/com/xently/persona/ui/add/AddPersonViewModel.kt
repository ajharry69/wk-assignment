package com.xently.persona.ui.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xently.persona.data.Destination
import com.xently.persona.data.TaskResult
import com.xently.persona.data.model.Person
import com.xently.persona.data.repository.person.IPersonRepository
import kotlinx.coroutines.launch
import java.io.File

class AddPersonViewModel @ViewModelInject constructor(private val repository: IPersonRepository) :
    ViewModel() {
    private val _personResult = MutableLiveData<TaskResult<Person>>()
    val personResult: LiveData<TaskResult<Person>>
        get() = _personResult

    fun addPerson(person: Person, photo: File?, destination: Destination? = null) {
        _personResult.value = TaskResult.Loading
        viewModelScope.launch {
            _personResult.value = repository.addPerson(person, photo, destination)
        }
    }
}