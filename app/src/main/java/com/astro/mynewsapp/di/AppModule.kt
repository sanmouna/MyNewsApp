package com.astro.mynewsapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.astro.mynewsapp.data.local.db.AppDatabase
import com.astro.mynewsapp.data.local.dao.ArticleDao
import com.astro.mynewsapp.data.network.ApiClient
import com.astro.mynewsapp.data.network.NewsApiService
import com.astro.mynewsapp.repository.NewsRepository
import dagger.hilt.InstallIn
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide Network dependencies
    @Provides
    @Singleton
    fun provideNewsApiService(): NewsApiService = ApiClient.newsApiService

    @Provides
    @Singleton
    fun provideNewsRepository(apiService: NewsApiService, articleDao: ArticleDao): NewsRepository {
        return NewsRepository(apiService, articleDao)
    }

    // Provide Database dependencies
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "news_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(appDatabase: AppDatabase): ArticleDao {
        return appDatabase.articleDao()
    }

    // Provide Context (Hilt needs this for dependencies requiring Context)
    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext
}
