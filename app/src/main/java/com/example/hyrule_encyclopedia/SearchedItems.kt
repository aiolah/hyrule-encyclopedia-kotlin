package com.example.hyrule_encyclopedia

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SearchedItemsScreen(viewModel: MainViewModel, backStack: NavBackStack<NavKey>)
{
    val searchedItems by viewModel.searchedItems.collectAsStateWithLifecycle()
    val idCreatures = remember { mutableStateListOf<Int>() }
    val idMonsters = remember { mutableStateListOf<Int>() }
    val idMaterials = remember { mutableStateListOf<Int>() }
    val idEquipment = remember { mutableStateListOf<Int>() }
    val idTreasures = remember { mutableStateListOf<Int>() }

    val searchedCreatures = remember { mutableStateListOf<Creature>() }
    val searchedMonsters = remember { mutableStateListOf<Monster>() }
    val searchedMaterials = remember { mutableStateListOf<Material>() }
    val searchedEquipment = remember { mutableStateListOf<Equipment>() }
    val searchedTreasures = remember { mutableStateListOf<Treasure>() }

    // val searchedEntries = remember { mutableStateListOf<Entry>() }

    var isLoading by remember { mutableStateOf(true) }

    viewModel.getAllSearchedItems()

    // ----------- LOGS
    /*searchedItems.forEach {
        Log.d("Id retourné par la bdd", it.idItem.toString())
    }*/

    /*idCreatures.forEach {
        Log.d("Id liste idCreatures", it.toString())
    }*/

    /*var listId: String = ""

    idCreatures.forEach {
        listId += it.toString() + ", "
    }

    Log.d("Liste id créatures", listId)*/

    LaunchedEffect(searchedItems) {
        /*idCreatures.forEach {
            val creature: Creature = viewModel.getSearchedCreature(it)

            Log.d("Créature récupérée", creature.name)
            searchedCreatures.add(creature)
        }*/

        idCreatures.clear()
        idMonsters.clear()
        idMaterials.clear()
        idEquipment.clear()
        idTreasures.clear()

        // Pas utile
        // val newIds = searchedItems.filter { it.category == "creatures" }.map { it.idItem }

        // Fonctionne
        searchedItems.forEach {
            when(it.category) {
                "creatures" -> idCreatures.add(it.idItem)
                "monsters" -> idMonsters.add(it.idItem)
                "materials" -> idMaterials.add(it.idItem)
                "equipment" -> idEquipment.add(it.idItem)
                "treasure" -> idTreasures.add(it.idItem)
            }
        }

        isLoading = true

        val creatures = coroutineScope {
            idCreatures.map { id ->
                async { viewModel.getSearchedCreature(id) }
            }.awaitAll()
        }

        searchedCreatures.clear()
        searchedCreatures.addAll(creatures)

        val monsters = coroutineScope {
            idMonsters.map { id ->
                async { viewModel.getSearchedMonster(id) }
            }.awaitAll()
        }

        searchedMonsters.clear()
        searchedMonsters.addAll(monsters)

        val materials = coroutineScope {
            idMaterials.map { id ->
                async { viewModel.getSearchedMaterial(id) }
            }.awaitAll()
        }

        searchedMaterials.clear()
        searchedMaterials.addAll(materials)

        val equipment = coroutineScope {
            idEquipment.map { id ->
                async { viewModel.getSearchedEquipment(id) }
            }.awaitAll()
        }

        searchedEquipment.clear()
        searchedEquipment.addAll(equipment)

        val treasures = coroutineScope {
            idTreasures.map { id ->
                async { viewModel.getSearchedTreasure(id) }
            }.awaitAll()
        }

        searchedTreasures.clear()
        searchedTreasures.addAll(treasures)

        /*val entries = coroutineScope {
            searchedItems.map {
                async { viewModel.getSearchedEntry(it.idItem)
                }
            }.awaitAll()
        }

        searchedEntries.clear()
        searchedEntries.addAll(entries)*/

        isLoading = false
    }

    Log.d("Valeur isLoading", isLoading.toString())

    if(!isLoading)
    {
        /*Column(modifier = Modifier.padding(100.dp)) {
            searchedItems.forEach {
                Row {
                    Text(it.idItem.toString())
                }
            }

            searchedCreatures.forEach {
                Row {
                    Text(it.name)
                }
            }
        }*/

        SearchedItemsGrid(searchedCreatures, searchedMonsters, searchedMaterials, searchedEquipment, searchedTreasures, /*searchedEntries,*/ backStack)
    }
}

@Composable
fun SearchedItemsGrid(searchedCreatures: SnapshotStateList<Creature>, searchedMonsters: SnapshotStateList<Monster>, searchedMaterials: SnapshotStateList<Material>, searchedEquipment: SnapshotStateList<Equipment>, searchedTreasure: SnapshotStateList<Treasure>, /*searchedEntries: SnapshotStateList<Entry>,*/ backStack: NavBackStack<NavKey>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(start = 0.dp, top = 50.dp, end = 0.dp, bottom = 130.dp)
    ) {
        items(searchedCreatures) { creature ->
            ItemCard(creature.name, creature.id, creature.image, creature.category, backStack)
        }
        items(searchedMonsters) { monster ->
            ItemCard(monster.name, monster.id, monster.image, monster.category, backStack)
        }
        items(searchedMaterials) { material ->
            ItemCard(material.name, material.id, material.image, material.category, backStack)
        }
        items(searchedEquipment) { equipment ->
            ItemCard(equipment.name, equipment.id, equipment.image, equipment.category, backStack)
        }
        items(searchedTreasure) { treasure ->
            ItemCard(treasure.name, treasure.id, treasure.image, treasure.category, backStack)
        }

        /*items(searchedEntries.sortedBy { it.id }) { entry ->
            ItemCard(entry.name, entry.id, entry.image, entry.category, backStack)
        }*/
    }
}