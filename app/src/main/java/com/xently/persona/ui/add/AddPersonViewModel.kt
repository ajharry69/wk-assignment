package com.xently.persona.ui.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.xently.persona.data.repository.person.IPersonRepository

class AddPersonViewModel @ViewModelInject constructor(private val repository: IPersonRepository) :
    ViewModel() {
    // TODO: Implement the ViewModel
}