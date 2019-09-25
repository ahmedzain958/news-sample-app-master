package com.rakshitjain.presentation.news

import androidx.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.news_articles.*
import org.koin.android.viewmodel.ext.android.viewModel
import rakshitjain.news_sample_app.R
import com.rakshitjain.presentation.entities.Status

class NewsActivity : AppCompatActivity() {

    private val newsViewModel: NewsViewModel by viewModel()
    private lateinit var listAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_articles)

        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        newsViewModel.fetchNews()
    }

    override fun onStart() {
        super.onStart()
        newsViewModel.getNewsLiveData().observe(this, Observer {
            when (it?.responseType) {
                Status.ERROR -> {
                    //Error handling
                }
                Status.LOADING -> {
                    //Progress
                }
                Status.SUCCESSFUL -> {
                    // On Successful response
                }
            }
            it?.data?.let { response ->
             Log.d("ZAIIn",response.articles.toString())
                listAdapter = NewsListAdapter(response.articles)
                recycler_view.adapter = listAdapter
            }
        })
    }
}