package com.example.marvelapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelapp.database.entity.CharacterWithBookMarkEntity
import com.example.marvelapp.repository.MarvelRepository
import com.example.marvelapp.ui.usecase.FetchCharactersUpdateLocalUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MarvelViewModel @Inject constructor(
    private val fetchCharactersUpdateLocalUC: FetchCharactersUpdateLocalUC,
    private val marvelRepo: MarvelRepository
) : ViewModel() {
    var charactersLD: LiveData<List<CharacterWithBookMarkEntity>>? = marvelRepo.getCharacters()

    init {
        viewModelScope.launch {
            marvelRepo.refreshTrigger.collectLatest {
                fetchCharactersUpdateLocalUC()
            }
        }
    }

    fun refresh() {
        marvelRepo.triggerRefresh()
    }


    fun toggleBookMark(character: CharacterWithBookMarkEntity) = viewModelScope.launch {
        marvelRepo.toggleBookMark(character.character?.id, !(character.bookMark?.bookMark ?: false))
    }
}
