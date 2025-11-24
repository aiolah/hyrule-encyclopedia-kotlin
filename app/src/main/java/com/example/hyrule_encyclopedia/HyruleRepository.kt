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
    suspend fun getCreatures() : List<Creature> {
        val response: ApiResponseCreatures = client.get(urlCategory + "creatures").body()
        return response.data
    }

    // Récupération de tous les équipements
    suspend fun getEquipment() : List<Equipment> {
        val response: ApiResponseEquipment = client.get(urlCategory + "equipment").body()
        return response.data
    }

    // Récupération de tous les monstres
    suspend fun getMonsters() : List<Monster> {
        val response: ApiResponseMonsters = client.get(urlCategory + "monsters").body()
        return response.data
    }

    // Récupération de tous les types de trésors
    suspend fun getTreasures() : List<Treasure> {
        val response: ApiResponseTreasures = client.get(urlCategory + "treasure").body()
        return response.data
    }

    // Récupération de tous les matériaux
    suspend fun getMaterials() : List<Material> {
        val response: ApiResponseMaterials = client.get(urlCategory + "materials").body()
        return response.data
    }

    // Récupération d'une créature
    suspend fun getOneCreature(id: Int): Creature {
        val response: ApiResponseOneCreature = client.get(urlEntry + id).body()
        return response.data
    }

    suspend fun getOneMonster(id: Int): Monster {
        val response: ApiResponseOneMonster = client.get(urlEntry + id).body()
        return response.data
    }

    suspend fun getOneMaterial(id: Int): Material {
        val response: ApiResponseOneMaterial = client.get(urlEntry + id).body()
        return response.data
    }

    suspend fun getOneEquipment(id: Int): Equipment {
        val response: ApiResponseOneEquipment = client.get(urlEntry + id).body()
        return response.data
    }

    suspend fun getOneTreasure(id: Int): Treasure {
        val response: ApiResponseOneTreasure = client.get(urlEntry + id).body()
        return response.data
    }

    /*suspend fun getOneEntry(id: Int): Entry {
        val response: ApiResponseOneEntry = client.get(urlEntry + id).body()
        return response.data
    }*/

    val database = Room.databaseBuilder(context = application, AppDatabase::class.java, "hyrule-database")
        .fallbackToDestructiveMigration()
        .build()
    val dao = database.searchedItemDao()
}