package com.astro.mynewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.astro.mynewsapp.data.model.Articles
import com.astro.mynewsapp.databinding.ItemHeadlineBinding
import com.astro.mynewsapp.utils.NewsAppUtils.extractName
import com.astro.mynewsapp.utils.NewsAppUtils.getShortenedTitle
import com.astro.mynewsapp.utils.NewsAppUtils.getTimeAgo
import com.bumptech.glide.Glide

class HeadlinesPagerAdapter(
    private val onItemClick: (Articles) -> Unit
) : RecyclerView.Adapter<HeadlinesPagerAdapter.HeadlineViewHolder>() {


    private var articles: List<Articles> = emptyList()

    fun submitList(newList: List<Articles>) {
        articles = newList
        notifyDataSetChanged()
    }



    inner class HeadlineViewHolder(val binding: ItemHeadlineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Articles) {
            binding.headline.text = getShortenedTitle(article.title.toString())
            binding.dateOfPost.text = getTimeAgo(article.publishedAt.toString())
            binding.authorName.text = extractName(article.author.toString())
            Glide.with(binding.root.context)
                .load(article.urlToImage)
                .into(binding.imageView)

            binding.imageView.setOnClickListener {
                onItemClick(article)
            }
        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHeadlineBinding.inflate(inflater, parent, false)
        return HeadlineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size


}