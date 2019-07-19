package com.zainco.newsapp.ui.list

import androidx.lifecycle.ViewModel
import com.zainco.newsapp.data.repository.NewsListRepository
import com.zainco.newsapp.internal.lazyDeferred

class NewsListViewModel(private val newsListRepository: NewsListRepository) : ViewModel() {
    val newsEntries by lazyDeferred {
        newsListRepository.getNewsList()
    }
}
