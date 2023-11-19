package com.example.marvelapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marvelapp.database.entity.BookMarkEntity

@Dao
interface BookMarkDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllBookMarks(bookMarkEntities: List<BookMarkEntity>)

    @Query("UPDATE BookMarkEntity SET is_book_mark = :isBookMark WHERE character_id = :id")
    suspend fun toggleBookMark(id: String?, isBookMark: Boolean?)
}
