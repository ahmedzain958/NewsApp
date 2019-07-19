package com.zainco.newsapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zainco.newsapp.data.network.response.NewsResponse
import com.zainco.newsapp.internal.NoConnectivityException

class NewsNetworkDataSourceImpl(private val newsApiService: NewsApiService) : NewsNetworkDataSource {

    private val _downloadedNews = MutableLiveData<NewsResponse>()
    override val downloadedNews: LiveData<NewsResponse>
        get() = _downloadedNews

    override suspend fun fetchNews(query: String, date: String) {
        try {
            val fetchedNews = newsApiService
                .getNews(query, date)
                .await()
            _downloadedNews.postValue(fetchedNews)
        } catch (e: NoConnectivityException) {
            //todo
            Log.e("Connectivity", "No internet connection.", e)
        }

    }
}