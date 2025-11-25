package com.example.hyrule_encyclopedia.local_database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemEntity::class], version = 7)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchedItemDao(): SearchedItemDao
}