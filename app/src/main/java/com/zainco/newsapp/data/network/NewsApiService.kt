package com.zainco.newsapp.data.network

import com.zainco.newsapp.data.network.response.NewsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET
    fun getNews(
        @Query("q") query: String,
        @Query("from") date: String = "2019-06-19"
    ): Deferred<NewsResponse>
}