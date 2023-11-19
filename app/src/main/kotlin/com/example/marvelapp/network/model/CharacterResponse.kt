package com.example.marvelapp.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResponse(
    @Json(name = "code")
    var code: String? = null,
    @Json(name = "status")
    var status: String? = null,
    @Json(name = "data")
    var data: CharacterResult? = null,
)
