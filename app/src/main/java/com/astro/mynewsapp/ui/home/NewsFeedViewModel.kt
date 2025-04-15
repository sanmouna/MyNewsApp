package com.astro.mynewsapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astro.mynewsapp.data.local.entities.ArticleEntity
import com.astro.mynewsapp.data.network.Resource
import com.astro.mynewsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {


    private val _newsData = MutableLiveData<Resource<List<ArticleEntity>>>()
    val newsData: LiveData<Resource<List<ArticleEntity>>> = _newsData

    fun fetchNewsByHeadlines(category: String) {
        viewModelScope.launch {
            _newsData.value = Resource.Loading()
            val apiResponse = repository.fetchNewsByHeadlines(category)
            if (apiResponse is Resource.Success) {
                val articles = apiResponse.data?.articles?.map {
                    ArticleEntity(
                        title = it.title ?: "",
                        description = it.description ?: "",
                        author = it.author,
                        publishedAt = it.publishedAt ?: "",
                        imageUrl = it.urlToImage ?: "",
                        url = it.url ?: ""
                    )
                } ?: emptyList()
                repository.saveArticles(articles)
                _newsData.value = Resource.Success(articles)
            } else {
                _newsData.value = Resource.Error(apiResponse.message.toString())
            }

        }
    }

    fun fetchSavedNews() {
        viewModelScope.launch {
            repository.getSavedArticles().collect { savedArticles ->
                _newsData.postValue(
                    if (savedArticles.isNotEmpty()) Resource.Success(savedArticles)
                    else Resource.Error("No articles saved.")
                )
            }
        }
    }

}