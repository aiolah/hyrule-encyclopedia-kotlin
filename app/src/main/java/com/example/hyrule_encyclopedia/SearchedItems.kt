package com.example.hyrule_encyclopedia

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.example.hyrule_encyclopedia.ui.theme.hyliaFontFamily
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SearchedItemsScreen(viewModel: MainViewModel, backStack: NavBackStack<NavKey>)
{
    val searchedItemsBotw by viewModel.searchedItemsBotw.collectAsStateWithLifecycle()
    val searchedItemsTotk by viewModel.searchedItemsTotk.collectAsStateWithLifecycle()

    val searchedEntriesBotw = remember { mutableStateListOf<Entry>() }
    val searchedEntriesTotk = remember { mutableStateListOf<Entry>() }

    var isLoading by remember { mutableStateOf(true) }

    viewModel.getAllSearchedItemsBotw()
    viewModel.getAllSearchedItemsTotk()

    LaunchedEffect(searchedItemsBotw, searchedItemsTotk) {
        isLoading = true

        val entriesBotw = coroutineScope {
            searchedItemsBotw.map {
                async { viewModel.getSearchedEntry(it.idItem, game = "botw")
                }
            }.awaitAll()
        }

        val entriesTotk = coroutineScope {
            searchedItemsTotk.map {
                async { viewModel.getSearchedEntry(it.idItem, game = "totk")
                }
            }.awaitAll()
        }

        searchedEntriesBotw.clear()
        searchedEntriesBotw.addAll(entriesBotw)

        searchedEntriesTotk.clear()
        searchedEntriesTotk.addAll(entriesTotk)

        isLoading = false
    }

    Log.d("Valeur isLoading", isLoading.toString())

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(0.dp, 100.dp, 0.dp, 130.dp)
    ) {
        item {
            Text(
                "Breath of the Wild",
                fontFamily = hyliaFontFamily,
                fontSize = 20.sp,
                modifier = Modifier.padding(15.dp)
            )

            if (!isLoading && searchedEntriesBotw.size > 0) {
                SearchedItemsGrid(searchedEntriesBotw, backStack, "botw")
            } else if (!isLoading && searchedEntriesBotw.size == 0) {
                Text(
                    "Aucun élément recherché pour l'instant",
                    modifier = Modifier.padding(18.dp, 100.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.padding(0.dp, 20.dp))

            Text(
                "Tears of the Kingdom",
                fontFamily = hyliaFontFamily,
                fontSize = 20.sp,
                modifier = Modifier.padding(15.dp)
            )

            if (!isLoading && searchedEntriesTotk.size > 0) {
                SearchedItemsGrid(searchedEntriesTotk, backStack, "totk")
            } else if (!isLoading && searchedEntriesTotk.size == 0) {
                Text(
                    "Aucun élément recherché pour l'instant",
                    modifier = Modifier.padding(18.dp, 100.dp)
                )
            }
        }
    }
}

@Composable
fun SearchedItemsGrid(searchedEntries: SnapshotStateList<Entry>, backStack: NavBackStack<NavKey>, game: String) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp)
                            .heightIn(max = 800.dp) // taille estimée
                            .fillMaxWidth()/*,
        contentPadding = PaddingValues(start = 0.dp, top = 100.dp, end = 0.dp, bottom = 130.dp)*/
    ) {
        items(searchedEntries.sortedBy { it.id }) { entry ->
            ItemCard(entry.name, entry.id, entry.image, entry.category, backStack, game)
        }
    }
}