package com.example.hyrule_encyclopedia

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import coil.compose.AsyncImage
import com.example.hyrule_encyclopedia.ui.theme.hyliaFontFamily
import com.example.hyrule_encyclopedia.ui.theme.onPrimaryContainerLight
import com.example.hyrule_encyclopedia.ui.theme.secondaryContainerLight

@Composable
fun DetailCreature(id: Int, viewModel: MainViewModel, backStack: NavBackStack<NavKey>) {
    val creature by viewModel.creature.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = true) { viewModel.getOneCreature(id) }

    Log.d("Test", id.toString())
    Log.d("Test", creature.name)

    Column(
        modifier = Modifier.padding(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(0.dp, 20.dp)
        ) {
            AsyncImage(
                model = creature.image,
                contentDescription = creature.name,
                modifier = Modifier.size(250.dp)
            )
        }
        Row(
            modifier = Modifier.padding(0.dp, 20.dp)
        ) {
            Text(
                fontFamily = hyliaFontFamily,
                text = creature.name.replaceFirstChar { it.uppercase() },
                textAlign = TextAlign.Center,
                color = onPrimaryContainerLight,
                fontSize = 30.sp
            )
        }
        Row(
            modifier = Modifier.padding(0.dp, 20.dp)
        ) {
            Text(
                text = creature.description,
                textAlign = TextAlign.Justify
            )
        }
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Common locations", fontFamily = hyliaFontFamily)

            creature.common_locations?.forEach {
                Text(it)
            }
        }
    }
}