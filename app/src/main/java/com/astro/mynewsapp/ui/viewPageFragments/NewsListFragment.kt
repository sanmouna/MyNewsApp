package com.astro.mynewsapp.ui.viewPageFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.astro.mynewsapp.data.local.entities.ArticleEntity
import com.astro.mynewsapp.data.model.Articles
import com.astro.mynewsapp.data.network.Resource
import com.astro.mynewsapp.databinding.FragmentNewsListBinding
import com.astro.mynewsapp.ui.MainActivity
import com.astro.mynewsapp.ui.screens.WebViewFragment
import com.astro.mynewsapp.utils.NewsAppUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment() {
    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var category: String

    private val newsListViewModel: NewsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString(ARG_CATEGORY) ?: "general"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsListViewModel.newsData.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.progressBarLayout.visibility = View.GONE
                    binding.viewPagerRecyclerView.visibility = View.VISIBLE
                    resource.data?.let { newsResponse ->
                        val adapter = NewsListAdapter(activity as MainActivity) { clickedArticle ->
                            if (!NewsAppUtils.isOnline(requireContext())) {
                                NewsAppUtils.showCustomDialog(
                                    requireContext(),
                                    "Oops!",
                                    "Please Check Your Internet Connection And Try Again."
                                )
                                return@NewsListAdapter
                            }
                            val url = clickedArticle.url
                            println(url)

                            val bundle = Bundle().apply {
                                putString("article_url", url)
                            }

                            val webViewFragment = WebViewFragment().apply {
                                arguments = bundle
                            }

                            (activity as? MainActivity)?.switchFragment(webViewFragment)

                        }
                        val savedArticles = resource.data.map { it.toArticles() }
                        adapter.submitList(savedArticles)
                        binding.viewPagerRecyclerView.adapter = adapter
                        binding.viewPagerRecyclerView.layoutManager =
                            LinearLayoutManager(requireContext())
                    }
                }

                is Resource.Error -> {
                    newsListViewModel.fetchSavedNews()
                    NewsAppUtils.showCustomDialog(
                        requireContext(),
                        resource.message.toString(),
                        "Please Check Your API Key"
                    )
                    // Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    binding.progressBarLayout.visibility = View.VISIBLE
                    binding.viewPagerRecyclerView.visibility = View.GONE
                }
            }
        })

        if (NewsAppUtils.isOnline(requireContext())) {
            newsListViewModel.fetchNewsByCategory(category)
        } else {
            newsListViewModel.fetchSavedCategoryNews(category)
        }


        //  newsListViewModel.fetchNewsCategory(category)

    }

    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: String) = NewsListFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CATEGORY, category)
            }
        }
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