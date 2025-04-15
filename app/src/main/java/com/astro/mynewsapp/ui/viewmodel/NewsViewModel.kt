package com.astro.mynewsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astro.mynewsapp.data.model.NewsResponse
import com.astro.mynewsapp.data.network.Resource
import com.astro.mynewsapp.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {
    private val _newsData = MutableLiveData<Resource<NewsResponse>>()
    val weatherData: LiveData<Resource<NewsResponse>> get() = _newsData

//    fun fetchNews(category: String) {
//        viewModelScope.launch {
//            _newsData.postValue(Resource.Loading()) // show loader in UI
//            val result = repository.getNewsByHeadlines(category)
//            _newsData.postValue(result) // Success or Error will be posted
//        }
//    }
}