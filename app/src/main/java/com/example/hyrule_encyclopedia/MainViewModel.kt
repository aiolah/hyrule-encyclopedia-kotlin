package com.example.hyrule_encyclopedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val repository = HyruleRepository()
    val creatures = MutableStateFlow(listOf<Creature>())

    fun getCreatures()
    {
        viewModelScope.launch { creatures.value = repository.getCreatures() }
    }
}