package com.astro.mynewsapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.astro.mynewsapp.R
import com.astro.mynewsapp.data.network.Resource
import com.astro.mynewsapp.ui.home.NewsFeedFragment
import com.astro.mynewsapp.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val newsViewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        newsViewModel.weatherData.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show progress bar
                    //  findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    //  findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                    resource.data?.let { newsResponse ->

                        println(newsResponse.status)
//                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//                        recyclerView.adapter = NewsAdapter(newsResponse.articles)
                    }
                }

                is Resource.Error -> {

//                  findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                    Toast.makeText(
                        this,
                        resource.message ?: "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // newsViewModel.fetchNews("us")

        if (savedInstanceState == null) {
            switchFragment(NewsFeedFragment())
        }
    }

    fun switchFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null) // Optional: Adds the fragment to the back stack so you can navigate back
        transaction.commit()
    }
}
