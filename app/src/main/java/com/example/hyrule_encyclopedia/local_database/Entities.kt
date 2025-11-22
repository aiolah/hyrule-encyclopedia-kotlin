package com.example.hyrule_encyclopedia.local_database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.hyrule_encyclopedia.Creature
import com.squareup.moshi.Moshi

/*@Entity
data class CreatureEntity(
    @PrimaryKey val id: String,
    val creature: Creature
)

@ProvidedTypeConverter
class Converters(moshi: Moshi) {
    val creatureAdaptater = moshi.adapter(Creature::class.java)

    @TypeConverter
    fun StringToCreature(value: String): Creature? {
        return creatureAdaptater.fromJson(value)
    }

    @TypeConverter
    fun CreatureToString(creature: Creature): String {
        return creatureAdaptater.toJson(creature)
    }
}*/

@Entity(tableName = "searchedItems")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idItem: Int,
    val category: String
)