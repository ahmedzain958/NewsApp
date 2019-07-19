package com.zainco.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.zainco.newsapp.data.network.response.Article

interface NewsListRepository {
    fun getNewsList(): LiveData<out List<Article>>
}