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
    val monster = MutableStateFlow(Monster())
    val material = MutableStateFlow(Material())
    val oneEquipment = MutableStateFlow(Equipment())
    val treasure = MutableStateFlow(Treasure())

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

    fun getOneMonster(id: Int)
    {
        viewModelScope.launch { monster.value = repository.getOneMonster(id) }
    }

    fun getOneMaterial(id: Int)
    {
        viewModelScope.launch { material.value = repository.getOneMaterial(id) }
    }

    fun getOneEquipment(id: Int)
    {
        viewModelScope.launch { oneEquipment.value = repository.getOneEquipment(id) }
    }

    fun getOneTreasure(id: Int)
    {
        viewModelScope.launch { treasure.value = repository.getOneTreasure(id) }
    }
}