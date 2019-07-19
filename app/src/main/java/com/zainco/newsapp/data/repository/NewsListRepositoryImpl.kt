package com.zainco.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.zainco.newsapp.data.network.response.Article

class NewsListRepositoryImpl : NewsListRepository {
    override fun getNewsList(): LiveData<out List<Article>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}