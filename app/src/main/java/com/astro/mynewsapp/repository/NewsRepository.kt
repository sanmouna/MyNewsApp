package com.astro.mynewsapp.repository


import com.astro.mynewsapp.data.local.dao.ArticleDao
import com.astro.mynewsapp.data.local.entities.ArticleEntity
import com.astro.mynewsapp.data.model.NewsResponse
import com.astro.mynewsapp.data.network.NewsApiService
import com.astro.mynewsapp.data.network.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: NewsApiService,
    private val articleDao: ArticleDao
) {
    suspend fun fetchNewsByHeadlines(city: String): Resource<NewsResponse> {
        return try {
            val response = apiService.getNewsByHeadlines(city)
            if (response.isSuccessful) {
                response.body()?.let {
                    // Save the articles in the Room Database
                    val articleList = it.articles.map { article ->
                        ArticleEntity(
                            title = article.title ?: "",
                            description = article.description ?: "",
                            author = article.author,
                            publishedAt = article.publishedAt ?: "",
                            imageUrl = article.urlToImage ?: "",
                            url = article.url ?:"",
                            category = article.category ?:""
                        )
                    }
                    // Insert the articles into the database
                    articleDao.insertArticles(articleList)
                    return Resource.Success(it)
                } ?: Resource.Error("Empty response")
            } else {
                Resource.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }

    suspend fun fetchNewsByCategory(category: String): Resource<NewsResponse> {
        return try {
            val response = apiService.getNewsByCategory(category)
            if (response.isSuccessful) {
                response.body()?.let {
                    // Save the articles in the Room Database
                    val articleList = it.articles.map { article ->
                        ArticleEntity(
                            title = article.title ?: "",
                            description = article.description ?: "",
                            author = article.author,
                            publishedAt = article.publishedAt ?: "",
                            imageUrl = article.urlToImage ?: "",
                            url = article.url ?: "",
                            category = article.category?:""
                        )
                    }
                    // Insert the articles into the database
                    articleDao.insertArticles(articleList)
                    return Resource.Success(it)
                } ?: Resource.Error("Empty response")
            } else {
                Resource.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }


    // ROOM: Save articles locally
    suspend fun saveArticles(articles: List<ArticleEntity>) {
        articleDao.insertArticles(articles)
    }

    // ROOM: Get all saved articles
    fun getSavedArticles(): Flow<List<ArticleEntity>> {
        return articleDao.getAllArticles()
    }

    fun getSavedCategoryArticles(category: String): Flow<List<ArticleEntity>> {
        return articleDao.getArticlesByCategory(category)
    }

    // ROOM: Delete all
    suspend fun clearAllArticles() {
        articleDao.clearAll()
    }
}
