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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.hyrule_encyclopedia.ui.theme.HyruleEncyclopediaTheme
import com.example.hyrule_encyclopedia.ui.theme.primaryContainerLight
import com.example.hyrule_encyclopedia.ui.theme.primaryLight
import com.example.hyrule_encyclopedia.ui.theme.prociono
import com.example.hyrule_encyclopedia.ui.theme.procionoFontFamily

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewmodel: MainViewModel by viewModels()
        viewmodel.getCreatures()

        setContent {
            HyruleEncyclopediaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // CreatureText(viewmodel)
                    // logCreature(viewmodel, modifier = Modifier.padding(innerPadding))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.verticalScroll(rememberScrollState())
                        ) {
                            CreatureCard(viewmodel)
                        }
                    }
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

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun CreatureCard(viewModel: MainViewModel)
{
    val creatures by viewModel.creatures.collectAsStateWithLifecycle()

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    creatures.forEach {
        ElevatedCard(
            modifier = Modifier.size(height = 160.dp, width = screenWidth).padding(7.dp),
            colors = CardDefaults.cardColors(
                containerColor = primaryLight,
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.size(height = 160.dp, width = screenWidth)
            ) {
                Row {
                    AsyncImage(model = it.image, contentDescription = "Image de " + it.name)
                }
                Row {
                    Text(
                        fontFamily = procionoFontFamily,
                        text = it.name.replaceFirstChar { it.uppercase() },
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = primaryContainerLight
                    )
                }
                Row {
                    Text(it.id.toString())
                }
            }
        }
    }
}