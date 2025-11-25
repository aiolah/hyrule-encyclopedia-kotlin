package com.example.hyrule_encyclopedia

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
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
    val searchedItemsBotw = MutableStateFlow(listOf<ItemEntity>())
    val searchedItemsTotk = MutableStateFlow(listOf<ItemEntity>())
    val entry = MutableStateFlow(ItemEntity())

    fun getCreatures(game: String)
    {
        viewModelScope.launch { creatures.value = repository.getCreatures(game) }
    }

    fun getEquipment(game: String)
    {
        viewModelScope.launch { equipment.value = repository.getEquipment(game) }
    }

    fun getMonsters(game: String)
    {
        viewModelScope.launch { monsters.value = repository.getMonsters(game) }
    }

    fun getTreasures(game: String)
    {
        viewModelScope.launch { treasures.value = repository.getTreasures(game) }
    }

    fun getMaterials(game: String)
    {
        viewModelScope.launch { materials.value = repository.getMaterials(game) }
    }

    fun getOneCreature(id: Int, game: String)
    {
        viewModelScope.launch { creature.value = repository.getOneCreature(id, game) }
    }

    suspend fun getSearchedEntry(id: Int, game: String): Entry
    {
        return repository.getOneEntry(id, game)
    }

    fun getOneMonster(id: Int, game: String)
    {
        viewModelScope.launch { monster.value = repository.getOneMonster(id, game) }
    }

    fun getOneMaterial(id: Int, game: String)
    {
        viewModelScope.launch { material.value = repository.getOneMaterial(id, game) }
    }

    fun getOneEquipment(id: Int, game: String)
    {
        viewModelScope.launch { oneEquipment.value = repository.getOneEquipment(id, game) }
    }

    fun getOneTreasure(id: Int, game: String)
    {
        viewModelScope.launch { treasure.value = repository.getOneTreasure(id, game) }
    }

    fun addSearchedItem(idItem: Int, category: String, game: String)
    {
        val itemEntity = ItemEntity(idItem = idItem, category = category, game = game)
        viewModelScope.launch { repository.dao.addSearchedItem(itemEntity) }
    }

    fun getAllSearchedItems()
    {
        viewModelScope.launch { searchedItems.value = repository.dao.getAllSearchedItems() }
    }

    fun getAllSearchedItemsBotw()
    {
        viewModelScope.launch { searchedItemsBotw.value = repository.dao.getAllSearchedItemsBotw() }
    }

    fun getAllSearchedItemsTotk()
    {
        viewModelScope.launch { searchedItemsTotk.value = repository.dao.getAllSearchedItemsTotk() }
    }

    fun getOneItem(id: Int, game: String)
    {
        viewModelScope.launch {
            val result = repository.dao.getOneItem(id, game)

            if(result != null)
            {
                entry.value = result
            }
            else
            {
                entry.value = ItemEntity()
            }
        }
    }

    fun deleteItem(id: Int, game: String)
    {
        viewModelScope.launch { repository.dao.deleteItem(id, game) }
    }
}