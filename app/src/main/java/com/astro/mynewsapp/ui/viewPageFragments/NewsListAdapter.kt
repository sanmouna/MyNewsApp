package com.astro.mynewsapp.ui.viewPageFragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astro.mynewsapp.data.model.Articles
import com.astro.mynewsapp.databinding.ItemNewsListPagerBinding
import com.astro.mynewsapp.ui.MainActivity
import com.astro.mynewsapp.utils.NewsAppUtils.extractName
import com.astro.mynewsapp.utils.NewsAppUtils.getTimeAgo
import com.bumptech.glide.Glide

class NewsListAdapter(val activity: MainActivity ,  private val onItemClick: (Articles) -> Unit) :
    RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {

    private var articles: List<Articles> = emptyList()

    fun submitList(newList: List<Articles>) {
        articles = newList
        notifyDataSetChanged()
    }

    inner class NewsListViewHolder(val binding: ItemNewsListPagerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Articles) {
            binding.titleNews.text = article.title
            binding.authorName.text =   extractName(article.author.toString())
            binding.dateOfPost.text = getTimeAgo(article.publishedAt.toString())

            Glide.with(activity)
                .load(article.urlToImage)
                .into(binding.imageView)

            binding.parentView.setOnClickListener {
                onItemClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val binding = ItemNewsListPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsListViewHolder(binding)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        holder.bind(articles[position])
    }
}
