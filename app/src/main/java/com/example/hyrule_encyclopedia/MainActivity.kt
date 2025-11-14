package com.example.hyrule_encyclopedia

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hyrule_encyclopedia.ui.theme.HyruleEncyclopediaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewmodel: MainViewModel by viewModels()
        viewmodel.getCreatures()

        setContent {
            HyruleEncyclopediaTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Creature(viewmodel)
                    logCreature(viewmodel, modifier = Modifier.padding(innerPadding))
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
fun Creature(viewModel: MainViewModel)
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