package com.example.hyrule_encyclopedia.local_database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchedItemDao {
    @Query("SELECT * FROM searchedItems")
    suspend fun getAllSearchedItems(): List<ItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSearchedItem(itemEntity: ItemEntity)

    //@Query("DELETE FROM creaturesSearched WHERE id = :id")
    //suspend fun deleteFilm(id: String)
}