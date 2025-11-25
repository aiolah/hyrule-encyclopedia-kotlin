package com.example.hyrule_encyclopedia

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.hyrule_encyclopedia.local_database.AppDatabase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HyruleRepository(application: Application) {
    val client: HttpClient = HttpClient(CIO) {

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // ignore les champs inconnus
                isLenient = true
            })
        }

        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("Ktor-Logger", message)
                }
            }
        }
    }

    val urlCategory = "https://botw-compendium.herokuapp.com/api/v3/compendium/category/"
    val urlEntry = "https://botw-compendium.herokuapp.com/api/v3/compendium/entry/"

    // Récupération des créatures
    suspend fun getCreatures(game: String) : List<Creature> {
        var response: ApiResponseCreatures

        response = client.get(urlCategory + "creatures") {
            url {
                parameters.append("game", game)
            }
        }.body()

        return response.data
    }

    // Récupération de tous les équipements
    suspend fun getEquipment(game: String) : List<Equipment> {
        val response: ApiResponseEquipment = client.get(urlCategory + "equipment") {
            url {
                parameters.append("game", game)
            }
        }.body()
        return response.data
    }

    // Récupération de tous les monstres
    suspend fun getMonsters(game: String) : List<Monster> {
        val response: ApiResponseMonsters = client.get(urlCategory + "monsters") {
            url {
                parameters.append("game", game)
            }
        }.body()
        return response.data
    }

    // Récupération de tous les types de trésors
    suspend fun getTreasures(game: String) : List<Treasure> {
        val response: ApiResponseTreasures = client.get(urlCategory + "treasure") {
            url {
                parameters.append("game", game)
            }
        }.body()
        return response.data
    }

    // Récupération de tous les matériaux
    suspend fun getMaterials(game: String) : List<Material> {
        val response: ApiResponseMaterials = client.get(urlCategory + "materials") {
            url {
                parameters.append("game", game)
            }
        }.body()
        return response.data
    }

    // Récupération d'une créature
    suspend fun getOneCreature(id: Int, game: String): Creature {
        val response: ApiResponseOneCreature = client.get(urlEntry + id) {
            url {
                parameters.append("game", game)
            }
        }.body()
        return response.data
    }

    suspend fun getOneMonster(id: Int, game: String): Monster {
        val response: ApiResponseOneMonster = client.get(urlEntry + id) {
            url {
                parameters.append("game", game)
            }
        }.body()
        return response.data
    }

    suspend fun getOneMaterial(id: Int, game: String): Material {
        val response: ApiResponseOneMaterial = client.get(urlEntry + id) {
            url {
                parameters.append("game", game)
            }
        }.body()
        return response.data
    }

    suspend fun getOneEquipment(id: Int, game: String): Equipment {
        val response: ApiResponseOneEquipment = client.get(urlEntry + id) {
            url {
                parameters.append("game", game)
            }
        }.body()
        return response.data
    }

    suspend fun getOneTreasure(id: Int, game: String): Treasure {
        val response: ApiResponseOneTreasure = client.get(urlEntry + id) {
            url {
                parameters.append("game", game)
            }
        }.body()
        return response.data
    }

    suspend fun getOneEntry(id: Int, game: String): Entry {
        val response: ApiResponseOneEntry = client.get(urlEntry + id) {
            url {
                parameters.append("game", game)
            }
        }.body()
        return response.data
    }

    val database = Room.databaseBuilder(context = application, AppDatabase::class.java, "hyrule-database")
        .fallbackToDestructiveMigration()
        .build()
    val dao = database.searchedItemDao()
}