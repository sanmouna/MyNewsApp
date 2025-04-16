package com.astro.mynewsapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.astro.mynewsapp.data.local.entities.ArticleEntity
import com.astro.mynewsapp.data.network.Resource
import com.astro.mynewsapp.repository.NewsRepository
import com.astro.mynewsapp.ui.home.NewsFeedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class NewsFeedViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: NewsFeedViewModel
    private lateinit var repository: NewsRepository

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = mock(NewsRepository::class.java)
        viewModel = NewsFeedViewModel(repository)
    }

    @Test
    fun `fetchSavedNews should return list of saved articles`() = runTest {
        // Arrange
        val dummyArticles = getDummyArticleEntities()
        whenever(repository.getSavedArticles()).thenReturn(flowOf(dummyArticles))

        val observer = mock<Observer<Resource<List<ArticleEntity>>>>()
        viewModel.newsData.observeForever(observer)

        // Act
        viewModel.fetchSavedNews()

        // Assert
        verify(observer).onChanged(Resource.Success(dummyArticles))

        // Cleanup
        viewModel.newsData.removeObserver(observer)
    }

    private fun getDummyArticleEntities(): List<ArticleEntity> {
        return listOf(
            ArticleEntity(
                id = 1,
                title = "Title 1",
                description = "Desc 1",
                url = "https://url1.com",
                imageUrl = "https://img1.com",
                publishedAt = "2025-04-15",
                author = "Author 1",
                category = "general"
            ),
            ArticleEntity(
                id = 2,
                title = "Title 2",
                description = "Desc 2",
                url = "https://url2.com",
                imageUrl = "https://img2.com",
                publishedAt = "2025-04-15",
                author = "Author 2",
                category = "general"
            )
        )
    }
}
