package com.example.marvelapp.database.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Comic(
    @Json(name = "name") val name: String? = null,
    @Json(name = "resourceURI") val resourceURI: String? = null
)

