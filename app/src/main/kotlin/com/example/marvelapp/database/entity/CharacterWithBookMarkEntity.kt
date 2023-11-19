package com.example.marvelapp.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithBookMarkEntity(
    @Embedded
    var character: CharacterEntity? = null,
    @Relation(parentColumn = "id", entityColumn = "character_id")
    var bookMark: BookMarkEntity? = null
)
