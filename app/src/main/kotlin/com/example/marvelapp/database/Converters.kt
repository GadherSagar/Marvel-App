package com.example.marvelapp.database

import androidx.room.TypeConverter
import com.example.marvelapp.database.entity.Comic
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<Comic>? {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<List<Comic>?> = moshi.adapter<List<Comic>?>(
            Types.newParameterizedType(
                List::class.java,
                Comic::class.java
            )
        )
        return value?.let { adapter.fromJson(it) }
    }

    @TypeConverter
    fun fromArrayList(list: List<Comic>?): String {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<List<Comic>?> = moshi.adapter<List<Comic>?>(
            Types.newParameterizedType(
                List::class.java,
                Comic::class.java
            )
        )
        return adapter.toJson(list)
    }
}
