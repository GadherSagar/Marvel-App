package com.example.marvelapp.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Thumbnail(
    @Json(name = "path")
    var path: String? = null,
    @Json(name = "extension")
    var extension: String? = null,
){
    val url: String
        get() = "$path.$extension"
}
