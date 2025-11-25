package com.example.hyrule_encyclopedia

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import coil.compose.AsyncImage
import com.example.hyrule_encyclopedia.ui.theme.hyliaFontFamily
import com.example.hyrule_encyclopedia.ui.theme.onPrimaryContainerLight

@Composable
fun DetailEntry(id: Int, category: String, game: String, viewModel: MainViewModel, backStack: NavBackStack<NavKey>) {
    when(category) {
        "creatures" -> {
            val creature by viewModel.creature.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = true) { viewModel.getOneCreature(id, game) }

            DetailComposable(creature.name, creature.image, creature.description, creature.id, creature.category, creature.common_locations, creature.drops, "", viewModel, game)
        }
        "monsters" -> {
            val monster by viewModel.monster.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = true) { viewModel.getOneMonster(id, game) }

            DetailComposable(monster.name, monster.image, monster.description, monster.id, monster.category, monster.common_locations, monster.drops, "", viewModel, game)
        }
        "materials" -> {
            val material by viewModel.material.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = true) { viewModel.getOneMaterial(id, game) }

            DetailComposable(material.name, material.image, material.description, material.id, material.category, material.common_locations, null, material.cooking_effect, viewModel, game)
        }
        "equipment" -> {
            val equipment by viewModel.oneEquipment.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = true) { viewModel.getOneEquipment(id, game) }

            DetailComposable(equipment.name, equipment.image, equipment.description, equipment.id, equipment.category, equipment.common_locations, null, "", viewModel, game)
        }
        "treasure" -> {
            val treasure by viewModel.treasure.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = true) { viewModel.getOneTreasure(id, game) }

            DetailComposable(treasure.name, treasure.image, treasure.description, treasure.id, treasure.category, treasure.common_locations, treasure.drops, "", viewModel, game)
        }
    }
}

@Composable
fun DetailComposable(name: String, image: String, description : String, id: Int, category: String, common_locations: MutableList<String>?, drops: MutableList<String>?, cooking_effect: String, viewModel: MainViewModel, game: String) {
    val itemEntity by viewModel.entry.collectAsStateWithLifecycle()
    viewModel.getOneItem(id, game)

    var isSearched by remember { mutableStateOf(true) }

    // Si l'item existe en bdd
    if(itemEntity.idItem !== 0)
    {
        isSearched = true
    }
    else
    {
        isSearched = false
    }

    Column(
        modifier = Modifier
            .padding(PaddingValues(start = 50.dp, top = 100.dp, end = 50.dp, bottom = 140.dp))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Image de l'item
        Row(
            modifier = Modifier.padding(0.dp, 20.dp)
        ) {
            var iconGame: Int = R.drawable.logo_botw

            if(game == "botw")
            {
                iconGame = R.drawable.logo_botw
            }
            else if(game == "totk")
            {
                iconGame = R.drawable.logo_totk
            }

            Box() {
                AsyncImage(
                    model = image,
                    contentDescription = name,
                    modifier = Modifier.size(250.dp).clip(RoundedCornerShape(5))
                )

                // Badge
                Icon(
                    painter = painterResource(iconGame),
                    "Logo jeu",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(75.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = 23.dp, y = 23.dp)
                        .background(Color.White, CircleShape)  // fond rond
                        .border(2.dp, Color.LightGray, CircleShape) // contour blanc
                        .padding(5.dp)
                )
            }
        }

        // Nom de l'item
        Row(
            modifier = Modifier.padding(0.dp, 20.dp)
        ) {
            Text(
                fontFamily = hyliaFontFamily,
                text = name.replaceFirstChar { it.uppercase() },
                textAlign = TextAlign.Center,
                color = onPrimaryContainerLight,
                fontSize = 30.sp
            )
        }

        // Description
        Row(
            modifier = Modifier.padding(0.dp, 20.dp)
        ) {
            Text(
                text = description,
                textAlign = TextAlign.Justify
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Common locations
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Common locations", fontFamily = hyliaFontFamily, modifier = Modifier.padding(0.dp, 10.dp))

                if(common_locations != null)
                {
                    common_locations.forEach {
                        Text(it)
                    }
                }
                else
                {
                    Text("None")
                }
            }

            // Créatures, monstres et trésors
            if(category != "equipment" && category != "materials")
            {
                // Drops
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Drops", fontFamily = hyliaFontFamily, modifier = Modifier.padding(0.dp, 10.dp))

                    if(drops != null)
                    {
                        drops.forEach {
                            Text(it)
                        }
                    }
                    else
                    {
                        Text("None")
                    }
                }
            }
            // Nourriture
            else if(category == "materials")
            {
                // Cooking effect
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Cooking effect", fontFamily = hyliaFontFamily, modifier = Modifier.padding(0.dp, 10.dp))

                    if(cooking_effect != "")
                    {
                        Text(cooking_effect)
                    }
                    else
                    {
                        Text("None")
                    }
                }
            }
        }

        Row(
            modifier = Modifier.padding(0.dp, 35.dp)
        ) {
            // Item recherché DONC présent en bdd : bouton suppression
            if(isSearched)
            {
                Button(
                    onClick = {
                        viewModel.deleteItem(id, game)
                        isSearched = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC40505))
                ) {
                    Text("Supprimer des éléments recherchés")
                }
            }
            // Item non recherché DONC absent en bdd : bouton Ajouter dans éléments recherchés
            else
            {
                Button(
                    onClick = {
                        viewModel.addSearchedItem(idItem = id, category = category, game = game)
                        isSearched = true
                    }) {
                    Text("Ajouter dans éléments recherchés")
                }
            }
        }
    }
}