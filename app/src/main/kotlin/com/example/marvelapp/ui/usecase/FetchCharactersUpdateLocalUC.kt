package com.example.marvelapp.ui.usecase

import com.example.marvelapp.database.entity.BookMarkEntity
import com.example.marvelapp.repository.MarvelRepository
import javax.inject.Inject

class FetchCharactersUpdateLocalUC @Inject constructor(
    private val repository: MarvelRepository,
) {
    suspend operator fun invoke() {
        val characters = repository.fetchCharacters()
        repository.insertAllCategories(characters)
        val bookMarkEntities = characters.map { character -> BookMarkEntity(character.id, false) }
        repository.insertAllBookMarks(bookMarkEntities)
    }
}
