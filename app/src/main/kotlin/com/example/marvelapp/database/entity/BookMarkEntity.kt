package com.example.marvelapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookMarkEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "character_id")
    val characterId: String,
    @ColumnInfo(name = "is_book_mark")
    val bookMark: Boolean? = null
)
