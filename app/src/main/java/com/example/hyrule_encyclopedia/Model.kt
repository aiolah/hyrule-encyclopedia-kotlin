package com.example.hyrule_encyclopedia

import kotlinx.serialization.Serializable

@Serializable
// Classe qui permet de récupérer la réponse de l'API directement
data class ApiResponseCreatures(
    val data: List<Creature>,
    val message: String,
    val status: Int
)

@Serializable
class Creature(val name: String = "",
               val id: Int = 0,
               val category: String = "",
               val description: String = "",
               val image: String = "",
               val common_locations: MutableList<String> ?= null,
               val edible: Boolean ?= null,
               val drops: MutableList<String> ?= null,
               val dlc: Boolean ?= null
)

@Serializable
data class ApiResponseOneCreature(
    val data: Creature,
    val message: String,
    val status: Int
)

@Serializable
// Classe qui permet de récupérer la réponse de l'API directement
data class ApiResponseMonsters(
    val data: List<Monster>,
    val message: String,
    val status: Int
)

@Serializable
class Monster(
    val name: String = "", // string; entry name
    val id: Int = 0,  // integer; ID as shown in compendium
    val category: String = "", // string; "monsters"
    val description: String = "", // string; short paragraph
    val image: String = "", // string; URL of image
    val common_locations: MutableList<String> ?= null, // array of strings or null for unknown; where the entry is commonly seen
    val drops: MutableList<String> ?= null, // array of strings or null for unknown; recoverable materials from killing
    val dlc: Boolean = false // boolean; whether the entry is from a DLC pack
)

@Serializable
data class ApiResponseOneMonster(
    val data: Monster,
    val message: String,
    val status: Int
)

@Serializable
// Classe qui permet de récupérer la réponse de l'API directement
data class ApiResponseMaterials(
    val data: List<Material>,
    val message: String,
    val status: Int
)

@Serializable
class Material(
    val name: String = "", // string; entry name
    val id: Int = 0, // integer; ID as shown in compendium
    val category: String = "", // string; "materials"
    val description: String = "", // string; short paragraph
    val image: String = "", // string; URL of image
    val common_locations: MutableList<String> ?= null, // array of strings or null for unknown; where the entry is commonly seen
    val hearts_recovered: Float = 0.0f, // float; health recovered when eaten raw
    val cooking_effect: String = "", // string; special effect when used in a dish/elixir (e.g. "stamina recovery"), empty if none
    val dlc: Boolean = false, // boolean; whether the entry is from a DLC pack
    /* TEARS OF THE KINGDOM ONLY */
    // "fuse_attack_power": 0 // integer; damage added when fused with a weapon
    /* */
)

@Serializable
data class ApiResponseOneMaterial(
    val data: Material,
    val message: String,
    val status: Int
)

@Serializable
// Classe qui permet de récupérer la réponse de l'API directement
data class ApiResponseEquipment(
    val data: List<Equipment>,
    val message: String,
    val status: Int
)

@Serializable
class Equipment(
    val name: String = "", // string; entry name
    val id: Int = 0,  // integer; ID as shown in compendium
    val category: String = "", // string; "equipment"
    val description: String = "", // string; short paragraph
    val image: String = "", // string; URL of image
    val common_locations: MutableList<String> ?= null, // array of strings or null for unknown; where the entry is commonly seen
    val properties: Properties = Properties(),
    val dlc: Boolean = false // boolean; whether the entry is from a DLC pack
)

@Serializable
data class ApiResponseOneEquipment(
    val data: Equipment,
    val message: String,
    val status: Int
)

@Serializable
class Properties(
    val attack: Int? = null, // integer; damage the entry does (0 for sheilds and arrows)
    val defense: Int? = null // integer; defense the entry offers (0 for equipment that aren't shields)
    /* TEARS OF THE KINGDOM ONLY */
    // "effect": "...", // string; special effect of the weapon (e.g. "wind razor"), empty if none
    // "type": "..." // string; type of weapon (e.g. "one-handed weapon")
    /* */
)

@Serializable
// Classe qui permet de récupérer la réponse de l'API directement
data class ApiResponseTreasures(
    val data: List<Treasure>,
    val message: String,
    val status: Int
)

@Serializable
class Treasure(
    val name: String = "", // string
    val id: Int = 0,  // integer; ID as shown in compendium
    val category: String = "", // string; "treasure"
    val description: String = "", // string; short paragraph
    val image: String = "", // string; URL of image
    val common_locations: MutableList<String> ?= null, // array of strings or null for unknown; where the entry is commonly seen
    val drops: MutableList<String> ?= null, // array of strings or null for unknown; recoverable materials when accessed
    val dlc: Boolean = false // boolean; whether the entry is from a DLC pack
)

@Serializable
data class ApiResponseOneTreasure(
    val data: Treasure,
    val message: String,
    val status: Int
)

@Serializable
data class ApiResponseOneEntry(
    val data: Entry,
    val message: String,
    val status: Int
)

// Classe permettant de récupérer une entry de n'importe quelle catégorie
@Serializable
class Entry(
    val name: String = "", // string
    val id: Int = 0,  // integer; ID as shown in compendium
    val category: String = "", // string; "treasure"
    val image: String = "", // string; URL of image
)