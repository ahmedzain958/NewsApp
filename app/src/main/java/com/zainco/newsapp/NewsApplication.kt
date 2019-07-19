package com.zainco.newsapp

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.zainco.newsapp.data.db.NewsDatabase
import com.zainco.newsapp.data.network.NewsApiService
import com.zainco.newsapp.data.network.NewsNetworkDataSource
import com.zainco.newsapp.data.network.NewsNetworkDataSourceImpl
import com.zainco.newsapp.data.network.interceptor.ConnectivityInterceptor
import com.zainco.newsapp.data.network.interceptor.ConnectivityInterceptorImpl
import com.zainco.newsapp.data.repository.NewsListRepository
import com.zainco.newsapp.data.repository.NewsListRepositoryImpl
import com.zainco.newsapp.ui.list.NewsListViewModelFactory
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NewsApplication() : Application(), KodeinAware {
    //all injected dependencies
    override val kodein = Kodein.lazy {
        import(androidXModule(this@NewsApplication))
        bind() from singleton { instance<NewsDatabase>().newsDao() }
        bind() from singleton { NewsDatabase(instance()) }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { NewsApiService(instance()) }
        bind<NewsNetworkDataSource>() with singleton { NewsNetworkDataSourceImpl(instance()) }
        bind<NewsListRepository>() with singleton {
            NewsListRepositoryImpl(
                instance(),
                instance()
            )
        }
        bind() from provider { NewsListViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}