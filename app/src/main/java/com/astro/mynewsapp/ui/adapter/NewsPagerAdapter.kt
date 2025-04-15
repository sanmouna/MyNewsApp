package com.astro.mynewsapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.astro.mynewsapp.ui.viewPageFragments.*

class NewsPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private val categories = listOf("business", "entertainment", "general", "health", "science", "technology")

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        return NewsListFragment.newInstance(categories[position])
    }
}