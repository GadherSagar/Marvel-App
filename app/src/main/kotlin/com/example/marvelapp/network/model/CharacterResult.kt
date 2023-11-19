package com.example.marvelapp.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterResult(
    @Json(name = "results")
    var results: List<CharacterRes>? = null,
)
