package com.example.marvelapp.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comics(
    @Json(name = "items")
    var items: List<Comic>? = null,
) {

    fun mapToComicEntities(): ArrayList<com.example.marvelapp.database.entity.Comic>?{
        val comics: ArrayList<com.example.marvelapp.database.entity.Comic> = ArrayList()
        for (comic in items!!) {
            comics.add(comic.mapToComicEntity())
        }
        return comics
    }
}
