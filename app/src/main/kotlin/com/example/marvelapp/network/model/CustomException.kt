package com.example.marvelapp.network.model

data class CustomException(override val message: String) : Exception(message)
