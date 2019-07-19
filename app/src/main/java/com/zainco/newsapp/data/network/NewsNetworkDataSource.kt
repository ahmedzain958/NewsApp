package com.zainco.newsapp.data.network

import androidx.lifecycle.LiveData
import com.zainco.newsapp.data.network.response.NewsResponse

interface NewsNetworkDataSource {
    val downloadedNews: LiveData<NewsResponse>
    suspend fun fetchNews(query: String)
}