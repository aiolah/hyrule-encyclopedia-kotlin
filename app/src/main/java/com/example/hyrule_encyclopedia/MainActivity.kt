package com.example.hyrule_encyclopedia

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.room.Room
import coil.compose.AsyncImage
import com.example.hyrule_encyclopedia.local_database.AppDatabase
import com.example.hyrule_encyclopedia.ui.theme.HyruleEncyclopediaTheme
import com.example.hyrule_encyclopedia.ui.theme.backgroundNavigationBar2
import com.example.hyrule_encyclopedia.ui.theme.funnelSansFontFamily
import com.example.hyrule_encyclopedia.ui.theme.grayText
import com.example.hyrule_encyclopedia.ui.theme.hyliaFontFamily
import com.example.hyrule_encyclopedia.ui.theme.onPrimaryContainerLight
import com.example.hyrule_encyclopedia.ui.theme.primaryContainerLight
import com.example.hyrule_encyclopedia.ui.theme.secondaryContainerLight
import com.example.hyrule_encyclopedia.ui.theme.white
import kotlinx.serialization.Serializable

@Serializable
data object CreaturesScreen: NavKey
@Serializable
data object MonstersScreen: NavKey
@Serializable
data object MaterialsScreen: NavKey
@Serializable
data object EquipmentScreen: NavKey
@Serializable
data object TreasuresScreen: NavKey
@Serializable
data class DetailEntryScreen(val id: Int, val category: String): NavKey
@Serializable
data object SearchedItems: NavKey

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
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
            // var screenSelected by remember { mutableStateOf("créatures") }
            val backStack = rememberNavBackStack(CreaturesScreen)
            var titlePage = "Créatures"
            var returnArrow = false

            when(backStack.lastOrNull()) {
                is CreaturesScreen -> titlePage = "Créatures"
                is MonstersScreen -> titlePage = "Monstres"
                is MaterialsScreen -> titlePage = "Matériaux"
                is EquipmentScreen -> titlePage = "Équipement"
                is TreasuresScreen -> titlePage = "Trésors"
                is SearchedItems -> titlePage = "Éléments recherchés"
                is DetailEntryScreen -> returnArrow = true
            }

            Log.d("Backstack", backStack.lastOrNull().toString())

            HyruleEncyclopediaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                if (!returnArrow) {
                                    Text(titlePage)
                                }
                                else
                                {
                                    Text("")
                                }
                            },
                            /*actions = {

                            }*/
                            navigationIcon = {
                                if (returnArrow) {
                                    IconButton(onClick = { backStack.removeLastOrNull() }) {
                                        Icon(
                                            Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Retour"
                                        )
                                    }
                                }
                            },

                            /*actions = {
                                IconButton(onClick = { backStack.add(Destination3) }) {
                                    Icon(
                                        Icons.Default.FavoriteBorder,
                                        contentDescription = "Favorite"
                                    )
                                }
                            }*/
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = backgroundNavigationBar2,
                            contentColor = white
                        ) {
                            NavigationBarItem(
                                icon = {
                                    Image(painter = painterResource(R.drawable.icon_creatures), contentDescription = "Icône créatures menu")
                                },
                                selected = backStack.lastOrNull() == CreaturesScreen,
                                onClick = { backStack.add(CreaturesScreen) },
                                label = { Text("Créatures", fontFamily = funnelSansFontFamily, fontWeight = FontWeight.Bold) },
                                colors = NavigationBarItemDefaults.colors(
                                    unselectedTextColor = grayText,
                                    selectedTextColor = Color.Black,
                                    indicatorColor = white
                                )
                            )
                            NavigationBarItem(
                                icon = {
                                    Image(painter = painterResource(R.drawable.icon_monsters), contentDescription = "Icône monstres menu")
                                },
                                selected = backStack.lastOrNull() == MonstersScreen,
                                onClick = { backStack.add(MonstersScreen) },
                                label = { Text("Monstres", fontFamily = funnelSansFontFamily, fontWeight = FontWeight.Bold) },
                                colors = NavigationBarItemDefaults.colors(
                                    unselectedTextColor = grayText,
                                    selectedTextColor = Color.Black,
                                    indicatorColor = white
                                )
                            )
                            NavigationBarItem(
                                icon = {
                                    Image(painter = painterResource(R.drawable.icon_materials), contentDescription = "Icône matériaux menu")
                                },
                                selected = backStack.lastOrNull() == MaterialsScreen,
                                onClick = { backStack.add(MaterialsScreen) },
                                label = { Text("Matériaux", fontFamily = funnelSansFontFamily, fontWeight = FontWeight.Bold) },
                                colors = NavigationBarItemDefaults.colors(
                                    unselectedTextColor = grayText,
                                    selectedTextColor = Color.Black,
                                    indicatorColor = white
                                )
                            )
                            NavigationBarItem(
                                icon = {
                                    Image(painter = painterResource(R.drawable.icon_equipment), contentDescription = "Icône équipement menu")
                                },
                                selected = backStack.lastOrNull() == EquipmentScreen,
                                onClick = { backStack.add(EquipmentScreen) },
                                label = { Text("Équipement", fontFamily = funnelSansFontFamily, fontWeight = FontWeight.Bold) },
                                colors = NavigationBarItemDefaults.colors(
                                    unselectedTextColor = grayText,
                                    selectedTextColor = Color.Black,
                                    indicatorColor = white
                                )
                            )
                            NavigationBarItem(
                                icon = {
                                    Image(painter = painterResource(R.drawable.icon_treasures), contentDescription = "Icône trésors menu")
                                },
                                selected = backStack.lastOrNull() == TreasuresScreen,
                                onClick = { backStack.add(TreasuresScreen) },
                                label = { Text("Trésors", fontFamily = funnelSansFontFamily, fontWeight = FontWeight.Bold) },
                                colors = NavigationBarItemDefaults.colors(
                                    unselectedTextColor = grayText,
                                    selectedTextColor = Color.Black,
                                    indicatorColor = white
                                )
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { backStack.add(SearchedItems) }) {
                            // Icon(painter = painterResource(R.drawable.search), "Loupe")
                            Icon(Icons.Filled.Search, "Loupe")
                        }
                    }
                ) { innerPadding ->
                    // CreatureText(viewmodel)
                    // logCreature(viewmodel, modifier = Modifier.padding(innerPadding))

                    // MaterialsGrid(viewmodel)
                    // TreasuresGrid(viewmodel)
                    // MonstersGrid(viewmodel)
                    // EquipmentGrid(viewmodel)
                    // CreaturesGrid(viewmodel)

                    NavDisplay(
                        backStack = backStack,
                        /*entryProvider = { key ->
                            when (key) {
                                is CreaturesScreen -> NavEntry(CreaturesScreen) {
                                    CreaturesGrid(viewmodel)
                                    screenSelected = "créatures";
                                }

                                is MonstersScreen -> NavEntry(MonstersScreen) {
                                    MonstersGrid(viewmodel)
                                    screenSelected = "monsters";
                                }

                                is MaterialsScreen -> NavEntry(MaterialsScreen) {
                                    MaterialsGrid(viewmodel)
                                    screenSelected = "materials";
                                }

                                is EquipmentScreen -> NavEntry(EquipmentScreen) {
                                    EquipmentGrid(viewmodel)
                                    screenSelected = "equipment";
                                }

                                is TreasuresScreen -> NavEntry(TreasuresScreen) {
                                    TreasuresGrid(viewmodel)
                                    screenSelected = "treasures";
                                }

                                else -> NavEntry(Unit) { Text("Unknown route") }
                            }
                        }*/

                        entryProvider = entryProvider {
                            entry<CreaturesScreen> { CreaturesGrid(viewmodel, backStack) }
                            entry<MonstersScreen> { MonstersGrid(viewmodel, backStack) }
                            entry<MaterialsScreen> { MaterialsGrid(viewmodel, backStack) }
                            entry<EquipmentScreen> { EquipmentGrid(viewmodel, backStack) }
                            entry<TreasuresScreen> { TreasuresGrid(viewmodel, backStack) }

                            entry<DetailEntryScreen> {
                                key -> DetailEntry(key.id, key.category, viewmodel, backStack)
                            }

                            entry<SearchedItems> { SearchedItemsScreen(viewmodel, backStack) }
                        }
                    )
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
fun CreaturesGrid(viewModel: MainViewModel, backStack: NavBackStack<NavKey>) {
    val creatures by viewModel.creatures.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(start = 0.dp, top = 100.dp, end = 0.dp, bottom = 130.dp)
    ) {
        items(creatures.sortedBy { it.id }) { creature ->
            ItemCard(creature.name, creature.id, creature.image, creature.category, backStack)
        }
    }
}

// Grille verticale contenant les cards des équipements
@Composable
fun EquipmentGrid(viewModel: MainViewModel, backStack: NavBackStack<NavKey>) {
    val equipment by viewModel.equipment.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(start = 0.dp, top = 100.dp, end = 0.dp, bottom = 130.dp)
    ) {
        items(equipment.sortedBy { it.id }) { item ->
            ItemCard(item.name, item.id, item.image, item.category, backStack)
        }
    }
}

// Grille verticale contenant les cards des monstres
@Composable
fun MonstersGrid(viewModel: MainViewModel, backStack: NavBackStack<NavKey>) {
    val monsters by viewModel.monsters.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(start = 0.dp, top = 100.dp, end = 0.dp, bottom = 130.dp)
    ) {
        items(monsters.sortedBy { it.id }) { monster ->
            ItemCard(monster.name, monster.id, monster.image, monster.category, backStack)
        }
    }
}

// Grille verticale contenant les cards des trésors
@Composable
fun TreasuresGrid(viewModel: MainViewModel, backStack: NavBackStack<NavKey>) {
    val treasures by viewModel.treasures.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(start = 0.dp, top = 100.dp, end = 0.dp, bottom = 130.dp)
    ) {
        items(treasures.sortedBy { it.id }) { treasure ->
            ItemCard(treasure.name, treasure.id, treasure.image, treasure.category, backStack)
        }
    }
}

// Grille verticale contenant les cards des matériaux
@Composable
fun MaterialsGrid(viewModel: MainViewModel, backStack: NavBackStack<NavKey>) {
    val materials by viewModel.materials.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(5.dp),
        contentPadding = PaddingValues(start = 0.dp, top = 100.dp, end = 0.dp, bottom = 130.dp)
    ) {
        items(materials.sortedBy { it.id }) { material ->
            ItemCard(material.name, material.id, material.image, material.category, backStack)
        }
    }
}

// Card unique à toutes les catégories qui affiche l'image, le nom et l'id d'un élément
@Composable
fun ItemCard(name: String, id: Int, url: String?, category: String, backStack: NavBackStack<NavKey>) {

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    ElevatedCard(
        modifier = Modifier.size(height = 160.dp, width = 200.dp).padding(7.dp),
        colors = CardDefaults.cardColors(containerColor = onPrimaryContainerLight),
        // shape = RoundedCornerShape(0),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            backStack.add(DetailEntryScreen(id, category))
        }
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