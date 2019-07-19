package com.zainco.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.zainco.newsapp.data.network.response.Article

interface NewsRepository {
    suspend fun getNewsList(): LiveData<out List<Article>>
    suspend fun getNewsDetail(id: Int): LiveData<out Article>
}