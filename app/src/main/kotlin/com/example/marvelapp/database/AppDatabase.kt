package com.example.marvelapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.marvelapp.database.dao.BookMarkDao
import com.example.marvelapp.database.dao.CharacterDao
import com.example.marvelapp.database.dao.CharacterWithBookMarkDao
import com.example.marvelapp.database.entity.BookMarkEntity
import com.example.marvelapp.database.entity.CharacterEntity
import com.example.marvelapp.util.Constants.DATABASE_VERSION

@Database(entities = [CharacterEntity::class, BookMarkEntity::class], version = DATABASE_VERSION)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun bookMarkDao(): BookMarkDao
    abstract fun characterWithBookMarkDao(): CharacterWithBookMarkDao
}
