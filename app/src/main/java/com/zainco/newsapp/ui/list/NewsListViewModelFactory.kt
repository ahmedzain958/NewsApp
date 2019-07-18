package com.zainco.newsapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zainco.newsapp.data.repository.NewsListRepository


class NewsListViewModelFactory(
    private val newsListRepository: NewsListRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsListViewModel(
            newsListRepository
        ) as T
    }
}