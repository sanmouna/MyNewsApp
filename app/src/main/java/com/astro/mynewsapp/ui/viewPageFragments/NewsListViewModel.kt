package com.astro.mynewsapp.ui.viewPageFragments

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
class NewsListViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _newsCategory = MutableLiveData<Resource<List<ArticleEntity>>>()
    val newsData: LiveData<Resource<List<ArticleEntity>>> = _newsCategory

    fun fetchNewsByCategory(category: String) {
        viewModelScope.launch {
            _newsCategory.value = Resource.Loading()
            val apiResponse = repository.fetchNewsByCategory(category)
            if (apiResponse is Resource.Success) {
                val articles = apiResponse.data?.articles?.map {
                    ArticleEntity(
                        title = it.title ?: "",
                        description = it.description ?: "",
                        author = it.author,
                        publishedAt = it.publishedAt ?: "",
                        imageUrl = it.urlToImage ?: "",
                        url = it.url ?: "",
                        category = category
                    )
                } ?: emptyList()
                repository.saveArticles(articles)
                _newsCategory.value = Resource.Success(articles)
            } else {
                _newsCategory.value = Resource.Error("API failed. Showing offline data.")
            }

        }
    }

    fun fetchSavedNews() {
        viewModelScope.launch {
            repository.getSavedArticles().collect { savedArticles ->
                _newsCategory.postValue(
                    if (savedArticles.isNotEmpty()) Resource.Success(savedArticles)
                    else Resource.Error("No articles saved.")
                )
            }
        }
    }

    fun fetchSavedCategoryNews(category: String) {
        viewModelScope.launch {
            repository.getSavedCategoryArticles(category).collect { savedArticles ->
                _newsCategory.postValue(
                    if (savedArticles.isNotEmpty()) Resource.Success(savedArticles)
                    else Resource.Error("No articles saved.")
                )
            }
        }
    }
}