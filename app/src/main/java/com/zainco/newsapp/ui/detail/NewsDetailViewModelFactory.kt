package com.zainco.newsapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zainco.newsapp.data.repository.NewsRepository


class NewsDetailViewModelFactory(
    private val newsId: Int,
    private val newsRepository: NewsRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsDetailViewModel(newsId,
            newsRepository
        ) as T
    }
}