package com.example.marvelapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.marvelapp.database.entity.CharacterWithBookMarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterWithBookMarkDao {
    @Query("SELECT * FROM CharacterEntity")
    @Transaction
    fun getAllCharacterWithBookMark(): LiveData<List<CharacterWithBookMarkEntity>>

    @Transaction
    @Query("SELECT * FROM CharacterEntity WHERE id =:id")
    fun getCharacterWithBookMarkById(id: String?): Flow<CharacterWithBookMarkEntity>
}
