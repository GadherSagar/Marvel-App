package com.example.marvelapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marvelapp.util.isValidString

@Entity
data class CharacterEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "description")
    private var description: String? = null,
    @ColumnInfo(name = "image")
    var image: String? = null,
    @ColumnInfo(name = "comics")
    var comics: List<Comic>? = null,
){
    fun getDescription(): String? {
        return if (description.isValidString()) {
            description
        } else "NA"
    }
}
