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

    val searchedEntries = remember { mutableStateListOf<Entry>() }

    var isLoading by remember { mutableStateOf(true) }

    viewModel.getAllSearchedItems()

    LaunchedEffect(searchedItems) {
        isLoading = true

        val entries = coroutineScope {
            searchedItems.map {
                async { viewModel.getSearchedEntry(it.idItem)
                }
            }.awaitAll()
        }

        searchedEntries.clear()
        searchedEntries.addAll(entries)

        isLoading = false
    }

    Log.d("Valeur isLoading", isLoading.toString())

    if(!isLoading)
    {
        SearchedItemsGrid(searchedEntries, backStack)
    }
}

@Composable
fun SearchedItemsGrid(searchedEntries: SnapshotStateList<Entry>, backStack: NavBackStack<NavKey>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(start = 0.dp, top = 50.dp, end = 0.dp, bottom = 130.dp)
    ) {
        items(searchedEntries.sortedBy { it.id }) { entry ->
            ItemCard(entry.name, entry.id, entry.image, entry.category, backStack)
        }
    }
}