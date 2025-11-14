package com.example.hyrule_encyclopedia

import kotlinx.serialization.Serializable

@Serializable
// Classe qui permet de récupérer la réponse de l'API directement
data class ApiResponse(
    val data: List<Creature>,
    val message: String,
    val status: Int
)

@Serializable
class Creature(val name: String,
               val id: Integer,
               val category: String,
               val description: String,
               val image: String,
               val common_locations: MutableList<String>? = null,
               val edible: Boolean,
               val drops: MutableList<String>? = null,
               val dlc: Boolean)