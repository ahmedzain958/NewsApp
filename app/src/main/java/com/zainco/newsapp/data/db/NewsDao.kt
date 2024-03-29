package com.zainco.newsapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zainco.newsapp.data.network.response.Article
import org.threeten.bp.LocalDate

@Dao
interface NewsDao {
    /*
    update and insert List of articles
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(articles: List<Article>)

    @Query("select * from article")
    fun getNews(): LiveData<List<Article>>

    /*
    count news older than 2 days ago
     */
    @Query("select count(id) from article where date(publishedAt) >= date(:twoDaysAgo)")
    fun countLegacyRecords(twoDaysAgo: LocalDate): Int

    @Query("select count(id) from article")
    fun countNews(): Int

    @Query("select * from article where id=:id")
    fun getNewsDetail(id: Int): LiveData<Article>
}