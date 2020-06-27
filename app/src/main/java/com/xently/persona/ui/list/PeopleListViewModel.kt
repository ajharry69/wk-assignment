package com.xently.persona.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.xently.persona.data.repository.person.IPersonRepository

class PeopleListViewModel @ViewModelInject constructor(private val repository: IPersonRepository) :
    ViewModel() {
    // TODO: Implement the ViewModel
}