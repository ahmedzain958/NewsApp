package com.zainco.newsapp.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.zainco.newsapp.BuildConfig
import com.zainco.newsapp.data.network.interceptor.ConnectivityInterceptor
import com.zainco.newsapp.data.network.interceptor.ErrorMappingInterceptor
import com.zainco.newsapp.data.network.response.NewsResponse
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("everything")
    fun getNews(
        @Query("q") query: String
    ): Deferred<NewsResponse>


    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor,
            errorMappingInterceptor: ErrorMappingInterceptor
        ): NewsApiService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("apiKey", BuildConfig.API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val builder = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(errorMappingInterceptor)
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(HttpLoggingInterceptor())
            }
            val okHttpClient = builder.build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsApiService::class.java)
        }
    }
}