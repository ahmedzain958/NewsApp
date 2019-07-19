package com.zainco.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.zainco.newsapp.data.db.NewsDao
import com.zainco.newsapp.data.network.NewsNetworkDataSource
import com.zainco.newsapp.data.network.response.Article
import com.zainco.newsapp.data.network.response.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

const val ZERO_NUMBER_OF_NEWS_COUNT = 0
const val TWO_DAYS_COUNT = 2L
const val POLITICS = "politics"

class NewsListRepositoryImpl(
    private val newsDao: NewsDao,
    private val newsNetworkDataSource: NewsNetworkDataSource
) : NewsListRepository {

    init {
        newsNetworkDataSource.apply {
            downloadedNews.observeForever { updatedNews ->
                persistFetchedNews(updatedNews)
            }
        }
    }

    private fun persistFetchedNews(updatedNews: NewsResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            newsDao.upsert(updatedNews.articles)
        }
    }

    override suspend fun getNewsList(): LiveData<out List<Article>> {
        return withContext(Dispatchers.IO) {
            if (!isExistNews() || isFetchNewsNeeded()) {
                newsNetworkDataSource.fetchNews(POLITICS)
            }
            return@withContext newsDao.getNews()
        }
    }


    private fun isFetchNewsNeeded(): Boolean {
        val twoDaysAgo: ZonedDateTime = ZonedDateTime.now().minusDays(TWO_DAYS_COUNT)
        val legacyNewsCounter = newsDao.countLegacyRecords(twoDaysAgo.toLocalDate())
        return legacyNewsCounter < ZERO_NUMBER_OF_NEWS_COUNT
    }

    private fun isExistNews(): Boolean {
        val newsCounter = newsDao.countNews()
        return newsCounter > ZERO_NUMBER_OF_NEWS_COUNT
    }
}