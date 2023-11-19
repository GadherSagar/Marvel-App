package com.example.marvelapp.network.model

import com.example.marvelapp.database.entity.CharacterEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterRes(
    @Json(name = "id") var id: String,
    @Json(name = "name") var name: String? = null,
    @Json(name = "description") var description: String? = null,
    @Json(name = "thumbnail") var thumbnail: Thumbnail? = null,
    @Json(name = "comics") var comics: Comics? = null,
) {
    fun mapToCharacterEntity(): CharacterEntity {
        return CharacterEntity(
            id= id,
            name= name,
            description= description,
            image= thumbnail!!.url,
            comics= comics!!.mapToComicEntities()
        )
    }
}
