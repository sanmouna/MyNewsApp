package com.astro.mynewsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.astro.mynewsapp.R
import com.astro.mynewsapp.data.local.entities.ArticleEntity
import com.astro.mynewsapp.data.model.Articles
import com.astro.mynewsapp.data.network.Resource
import com.astro.mynewsapp.databinding.NewsFeedFragmenBinding
import com.astro.mynewsapp.ui.MainActivity
import com.astro.mynewsapp.ui.adapter.HeadlinesPagerAdapter
import com.astro.mynewsapp.ui.adapter.NewsPagerAdapter
import com.astro.mynewsapp.ui.screens.WebViewFragment
import com.astro.mynewsapp.utils.NewsAppUtils
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class NewsFeedFragment : Fragment(R.layout.news_feed_fragmen) {

    private var _binding: NewsFeedFragmenBinding? = null
    private val binding get() = _binding!!

    private val newsFeedViewModel: NewsFeedViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = NewsFeedFragmenBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.todayDate.text = getFormattedDate()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsFeedViewModel.newsData.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.headlinesViewPager.visibility = View.VISIBLE
                    binding.progressBarLayout.visibility = View.GONE

                    resource.data?.let { newsResponse ->
                        val adapter = HeadlinesPagerAdapter { clickedArticle ->
                            if (!NewsAppUtils.isOnline(requireContext())) {
                                NewsAppUtils.showCustomDialog(
                                    requireContext(),
                                    "Oops!",
                                    "Please Check Your Internet Connection And Try Again."
                                )
                                return@HeadlinesPagerAdapter
                            }
                            val url = clickedArticle.url
                            val bundle = Bundle().apply {
                                putString("article_url", url)
                            }
                            val webViewFragment = WebViewFragment().apply {
                                arguments = bundle
                            }
                            (activity as? MainActivity)?.switchFragment(webViewFragment)
                        }
                        val savedArticles = resource.data.map { it.toArticles() }
                        val filteredList = savedArticles
                            .filter { !it.urlToImage.isNullOrEmpty() && !it.author.isNullOrEmpty() }
                            .take(5)
                        adapter.submitList(filteredList)
                        binding.swipeRefreshLayout.isRefreshing = false
                        binding.headlinesViewPager.adapter = adapter

                    }
                }

                is Resource.Error -> {
                    println(resource.message)
                    NewsAppUtils.showCustomDialog(
                        requireContext(),
                        resource.message.toString(),
                        "Please Check Your API Key"
                    )
                    newsFeedViewModel.fetchSavedNews()
                }

                is Resource.Loading -> {
                    binding.headlinesViewPager.visibility = View.GONE
                    binding.progressBarLayout.visibility = View.VISIBLE
                }
            }
        })

        if (NewsAppUtils.isOnline(requireContext())) {
            newsFeedViewModel.fetchNewsByHeadlines("us")
        } else {
            newsFeedViewModel.fetchSavedNews()
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            if (NewsAppUtils.isOnline(requireContext())) {
                newsFeedViewModel.fetchNewsByHeadlines("us")
            } else {
                newsFeedViewModel.fetchSavedNews()
            }
        }

        val tabTitles = listOf("Business", "Entertainment", "General", "Health", "Science", "Tech")
        val adapter = NewsPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
            val tabTextView = (tab.view as LinearLayout).getChildAt(1)
            if (tabTextView is TextView) {
                val typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_slab_regular)
                tabTextView.typeface = typeface
            }

        }.attach()




        binding.todayDate.setOnClickListener {
            (activity as? MainActivity)?.switchFragment(WebViewFragment())
        }

    }

    fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val suffix = when {
            day in 11..13 -> "th"
            day % 10 == 1 -> "st"
            day % 10 == 2 -> "nd"
            day % 10 == 3 -> "rd"
            else -> "th"
        }

        val dateFormat = SimpleDateFormat("EEE, dd MMMM yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        val dayWithSuffix = formattedDate.replaceFirst("\\d{2}".toRegex(), "$day$suffix")

        return dayWithSuffix
    }

    fun ArticleEntity.toArticles(): Articles {
        return Articles(
            title = this.title,
            description = this.description,
            author = this.author,
            publishedAt = this.publishedAt,
            urlToImage = this.imageUrl,
            url = this.url
        )
    }


}