package com.example.hyrule_encyclopedia.local_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "searchedItems")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idItem: Int = 0,
    val category: String = ""
)