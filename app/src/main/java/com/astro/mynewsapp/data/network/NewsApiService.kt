package com.astro.mynewsapp.data.network

import com.astro.mynewsapp.data.model.NewsResponse
import com.astro.mynewsapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getNewsByHeadlines(
        @Query("country") category: String,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getNewsByCategory(
        @Query("q") category: String,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>
}


