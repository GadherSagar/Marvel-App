package com.example.marvelapp.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comic(
    @Json(name = "resourceURI")
    var resourceURI: String? = null,
    @Json(name = "name")
    var name: String? = null,){

    fun mapToComicEntity(): com.example.marvelapp.database.entity.Comic {
        return com.example.marvelapp.database.entity.Comic(name, resourceURI)
    }
}
