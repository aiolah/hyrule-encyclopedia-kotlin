package com.example.hyrule_encyclopedia

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.hyrule_encyclopedia.ui.theme.HyruleEncyclopediaTheme
import com.example.hyrule_encyclopedia.ui.theme.hyliaFontFamily
import com.example.hyrule_encyclopedia.ui.theme.onPrimaryContainerLight
import com.example.hyrule_encyclopedia.ui.theme.primaryContainerLight
import com.example.hyrule_encyclopedia.ui.theme.primaryLight
import com.example.hyrule_encyclopedia.ui.theme.secondaryContainerLight

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewmodel: MainViewModel by viewModels()
        viewmodel.getCreatures()
        viewmodel.getEquipment()
        viewmodel.getMonsters()
        viewmodel.getTreasures()
        viewmodel.getMaterials()

        setContent {
            HyruleEncyclopediaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // CreatureText(viewmodel)
                    // logCreature(viewmodel, modifier = Modifier.padding(innerPadding))

                    MaterialsGrid(viewmodel)
                    // TreasuresGrid(viewmodel)
                    // MonstersGrid(viewmodel)
                    // EquipmentGrid(viewmodel)
                    // CreaturesGrid(viewmodel)
                }
            }
        }
    }
}

@Composable
fun logCreature(viewModel: MainViewModel, modifier: Modifier)
{
    val creatures by viewModel.creatures.collectAsStateWithLifecycle()

    creatures.forEach { Log.d("API", it.name) }
}

@Composable
fun CreatureText(viewModel: MainViewModel)
{
    val creatures by viewModel.creatures.collectAsStateWithLifecycle()

    Box() {
        Column {
            creatures.forEach {
                Text(it.name)
            }
        }
    }
}

// Grille verticale contenant les cards des créatures
@Composable
fun CreaturesGrid(viewModel: MainViewModel) {
    val creatures by viewModel.creatures.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(vertical = 50.dp)
    ) {
        items(creatures) { creature ->
            ItemCard(creature.name, creature.id, creature.image)
        }
    }
}

// Grille verticale contenant les cards des équipements
@Composable
fun EquipmentGrid(viewModel: MainViewModel) {
    val equipment by viewModel.equipment.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(vertical = 50.dp)
    ) {
        items(equipment) { item ->
            ItemCard(item.name, item.id, item.image)
        }
    }
}

// Grille verticale contenant les cards des monstres
@Composable
fun MonstersGrid(viewModel: MainViewModel) {
    val monsters by viewModel.monsters.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(vertical = 50.dp)
    ) {
        items(monsters) { monster ->
            ItemCard(monster.name, monster.id, monster.image)
        }
    }
}

// Grille verticale contenant les cards des trésors
@Composable
fun TreasuresGrid(viewModel: MainViewModel) {
    val treasures by viewModel.treasures.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(vertical = 50.dp)
    ) {
        items(treasures) { treasure ->
            ItemCard(treasure.name, treasure.id, treasure.image)
        }
    }
}

// Grille verticale contenant les cards des matériaux
@Composable
fun MaterialsGrid(viewModel: MainViewModel) {
    val materials by viewModel.materials.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(vertical = 50.dp)
    ) {
        items(materials) { material ->
            ItemCard(material.name, material.id, material.image)
        }
    }
}

// Card unique à toutes les catégories qui affiche l'image, le nom et l'id d'un élément
@Composable
fun ItemCard(name: String, id: Int, url: String) {

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    ElevatedCard(
        modifier = Modifier.size(height = 160.dp, width = 200.dp).padding(7.dp),
        colors = CardDefaults.cardColors(containerColor = onPrimaryContainerLight),
        // shape = RoundedCornerShape(0),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.size(height = 160.dp, width = screenWidth)
        ) {
            Row {
                AsyncImage(
                    model = url,
                    contentDescription = "Image de " + name,
                    modifier = Modifier.clip(RoundedCornerShape(5))
                )
            }
            Row {
                Text(
                    fontFamily = hyliaFontFamily,
                    text = name.replaceFirstChar { it.uppercase() },
                    textAlign = TextAlign.Center,
                    color = secondaryContainerLight
                )
            }
            Row {
                Text(
                    id.toString(),
                    fontFamily = hyliaFontFamily,
                    color = primaryContainerLight
                )
            }
        }
    }
}