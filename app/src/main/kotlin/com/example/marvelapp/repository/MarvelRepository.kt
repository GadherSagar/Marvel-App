package com.example.marvelapp.repository

import androidx.lifecycle.LiveData
import com.example.marvelapp.BuildConfig.PRIVATE_KEY
import com.example.marvelapp.BuildConfig.PUBLIC_KEY
import com.example.marvelapp.database.AppDatabase
import com.example.marvelapp.database.entity.BookMarkEntity
import com.example.marvelapp.database.entity.CharacterEntity
import com.example.marvelapp.database.entity.CharacterWithBookMarkEntity
import com.example.marvelapp.di.IoDispatcher
import com.example.marvelapp.network.ApiService
import com.example.marvelapp.network.model.CharacterRes
import com.example.marvelapp.network.model.CustomException
import com.example.marvelapp.util.md5
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random


class MarvelRepository @Inject constructor(
    private val service: ApiService,
    private val db: AppDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    private val _refreshTrigger = MutableStateFlow(Random.nextInt())
    val refreshTrigger = _refreshTrigger.asStateFlow()

    fun triggerRefresh() {
        _refreshTrigger.value = Random.nextInt()
    }

    private val _characterId = MutableStateFlow<String?>(null)
    val characterId = _characterId.asStateFlow()

    fun setCharacterId(characterId:String){
        _characterId.value = characterId
    }

    suspend fun fetchCharacters() = withContext(dispatcher) {
        val ts = System.currentTimeMillis()
        val hash: String = "$ts${PRIVATE_KEY}${PUBLIC_KEY}".md5()

        val characters: List<CharacterRes> =
            service.getMarvelCharacters(ts.toString(), PUBLIC_KEY, hash).data?.results
                ?: throw CustomException("Data Not Found")
        return@withContext characters.map { character -> character.mapToCharacterEntity() }
    }

    suspend fun insertAllCategories(characterEntities: List<CharacterEntity>) =
        withContext(dispatcher) {
            db.characterDao().insertAllCategories(characterEntities)
        }

    suspend fun insertAllBookMarks(bookMarkEntities: List<BookMarkEntity>) =
        withContext(dispatcher) {
            db.bookMarkDao().insertAllBookMarks(bookMarkEntities)
        }

    fun getCharacters(): LiveData<List<CharacterWithBookMarkEntity>> {
        return db.characterWithBookMarkDao().getAllCharacterWithBookMark()
    }

    fun getCharacterById(id: String?): Flow<CharacterWithBookMarkEntity> {
        return db.characterWithBookMarkDao().getCharacterWithBookMarkById(id)
    }

    suspend fun toggleBookMark(id: String?, isBookMark: Boolean?) = withContext(dispatcher) {
        db.bookMarkDao().toggleBookMark(id, isBookMark)
    }
}
