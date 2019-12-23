package com.dvidal.samplearticles.features.articles.data.local

/**
 * @author diegovidal on 2019-12-18.
 */
interface ArticlesLocalDataSource {

    fun insertAllArticles(listArticlesDto: List<ArticleDto>)

    fun fetchAllArticles(): List<ArticleDto>

    fun clearAllArticles()

    fun reviewArticle(sku: String)

    fun favoriteArticle(sku: String)

    fun fetchFavoriteArticles(): List<ArticleDto>

    fun fetchUnreviewedArticles(): List<ArticleDto>
}