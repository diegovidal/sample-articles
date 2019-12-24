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
    fun insertAllArticles(listArticlesDto: List<ArticleDto>?)

    @Query("SELECT * FROM articledto")
    fun fetchAllArticles(): List<ArticleDto>

    @Query("DELETE FROM articledto")
    fun clearAllArticles()

    @Query("UPDATE articledto SET isReview = 1 WHERE sku = :sku")
    fun reviewArticle(sku: String)

    @Query("UPDATE articledto SET isFavorite = 1 WHERE sku = :sku")
    fun favoriteArticle(sku: String)

    @Query("SELECT * FROM articledto WHERE isFavorite = 1")
    fun fetchFavoriteArticles(): List<ArticleDto>

    @Query("SELECT * FROM articledto WHERE isReview = 0")
    fun fetchUnreviewedArticles(): List<ArticleDto>

    @Query("SELECT * FROM articledto WHERE isReview = 1")
    fun fetchReviewedArticles(): List<ArticleDto>
}