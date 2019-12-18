package com.dvidal.samplearticles.features.articles.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author diegovidal on 2019-12-18.
 */

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addArticle(articleDto: ArticleDto): Long

    @Query("SELECT * FROM articledto")
    fun fetchArticles(): List<ArticleDto>
}