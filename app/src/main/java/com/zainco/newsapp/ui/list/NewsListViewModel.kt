package com.zainco.newsapp.ui.list

import androidx.lifecycle.ViewModel
import com.zainco.newsapp.data.repository.NewsRepository
import com.zainco.newsapp.internal.lazyDeferred

class NewsListViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    val newsEntries by lazyDeferred {
        newsRepository.getNewsList()
    }
}
