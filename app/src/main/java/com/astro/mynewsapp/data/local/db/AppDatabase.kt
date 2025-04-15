package com.astro.mynewsapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.astro.mynewsapp.data.local.dao.ArticleDao
import com.astro.mynewsapp.data.local.entities.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}