package com.example.marvelapp.network

import com.example.marvelapp.network.model.CharacterResponse
import com.example.marvelapp.util.Constants.CHARACTERS
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(CHARACTERS)
    suspend fun getMarvelCharacters(
        @Query("ts") ts: String?,
        @Query("apikey") apikey: String?,
        @Query("hash") hash: String?
    ): CharacterResponse
}
