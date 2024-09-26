package com.myapplication.model.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharactersEntity>)

    @Query("SELECT * FROM CharactersEntity")
    fun getCharactersFlow(): Flow<List<CharactersEntity>>

    @Query("SELECT * FROM CharactersEntity where id = :id")
    suspend fun getCharacterById(id: Long): CharactersEntity

    @Query("DELETE FROM CharactersEntity")
    suspend fun clearCharacters()
}