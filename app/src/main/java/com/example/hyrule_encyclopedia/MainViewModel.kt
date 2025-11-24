package com.example.hyrule_encyclopedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hyrule_encyclopedia.local_database.ItemEntity
import com.example.hyrule_encyclopedia.local_database.SearchedItemDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val repository = HyruleRepository(application)
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
    val searchedItems = MutableStateFlow(listOf<ItemEntity>())

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

    suspend fun getSearchedCreature(id: Int): Creature
    {
        return repository.getOneCreature(id)
    }

    suspend fun getSearchedMonster(id: Int): Monster
    {
        return repository.getOneMonster(id)
    }

    suspend fun getSearchedMaterial(id: Int): Material
    {
        return repository.getOneMaterial(id)
    }

    suspend fun getSearchedEquipment(id: Int): Equipment
    {
        return repository.getOneEquipment(id)
    }

    suspend fun getSearchedTreasure(id: Int): Treasure
    {
        return repository.getOneTreasure(id)
    }

    /*suspend fun getSearchedEntry(id: Int): Entry
    {
        return repository.getOneEntry(id)
    }*/

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

    fun addSearchedItem(idItem: Int, category: String)
    {
        val itemEntity = ItemEntity(idItem = idItem, category = category)
        viewModelScope.launch { repository.dao.addSearchedItem(itemEntity) }
    }

    fun getAllSearchedItems()
    {
        viewModelScope.launch { searchedItems.value = repository.dao.getAllSearchedItems() }
    }
}