package com.xently.persona.ui.list

import androidx.recyclerview.widget.DiffUtil
import com.xently.persona.data.model.Person

class PersonDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Person, newItem: Person) = oldItem == newItem
}