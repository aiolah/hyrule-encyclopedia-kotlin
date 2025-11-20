package com.example.hyrule_encyclopedia

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val repository = HyruleRepository()
    val creatures = MutableStateFlow(listOf<Creature>())
    val equipment = MutableStateFlow(listOf<Equipment>())
    val monsters = MutableStateFlow(listOf<Monster>())
    val treasures = MutableStateFlow(listOf<Treasure>())
    val materials = MutableStateFlow(listOf<Material>())
    val creature = MutableStateFlow(Creature())

    fun getCreatures()
    {
        viewModelScope.launch { creatures.value = repository.getCreatures() }
    }

    fun getEquipment()
    {
        viewModelScope.launch { equipment.value = repository.getEquipment() }
    }

    fun getMonsters()
    {
        viewModelScope.launch { monsters.value = repository.getMonsters() }
    }

    fun getTreasures()
    {
        viewModelScope.launch { treasures.value = repository.getTreasures() }
    }

    fun getMaterials()
    {
        viewModelScope.launch { materials.value = repository.getMaterials() }
    }

    fun getOneCreature(id: Int)
    {
        viewModelScope.launch { creature.value = repository.getOneCreature(id) }
    }
}