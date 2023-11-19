package com.example.marvelapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.database.entity.CharacterWithBookMarkEntity
import com.example.marvelapp.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MarvelDetailViewModel @Inject constructor(private val marvelRepo: MarvelRepository) :
    ViewModel() {
    var characterLD: LiveData<CharacterWithBookMarkEntity>? =
        marvelRepo.characterId.flatMapLatest { id -> marvelRepo.getCharacterById(id) }
            .asLiveData()

    fun setCharacterId(characterId: String) {
        marvelRepo.setCharacterId(characterId)
    }

    fun toggleBookMark(characterWithBookMark: CharacterWithBookMarkEntity) = viewModelScope.launch {
        marvelRepo.toggleBookMark(
            characterWithBookMark.character?.id,
            !(characterWithBookMark.bookMark?.bookMark ?: false)
        )
    }
}
