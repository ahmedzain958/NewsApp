package com.zainco.newsapp.ui.detail

import androidx.lifecycle.ViewModel;
import com.zainco.newsapp.data.repository.NewsRepository
import com.zainco.newsapp.internal.lazyDeferred

class NewsDetailViewModel(
    private val newsDetailsId: Int
    , private val newsRepository: NewsRepository
) : ViewModel() {
    val detailsNews by lazyDeferred {
        newsRepository.getNewsDetail(newsDetailsId)
    }
}
