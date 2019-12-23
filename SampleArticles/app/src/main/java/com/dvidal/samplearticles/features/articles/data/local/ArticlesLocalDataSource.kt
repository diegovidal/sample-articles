package com.dvidal.samplearticles.features.articles.data.local

import com.dvidal.samplearticles.core.common.EitherResult

/**
 * @author diegovidal on 2019-12-18.
 */
interface ArticlesLocalDataSource {

    fun insertAllArticles(listArticlesDto: List<ArticleDto>)

    fun fetchAllArticles(): EitherResult<List<ArticleDto>>

    fun clearAllArticles()

    fun reviewArticle(sku: String)

    fun favoriteArticle(sku: String)

    fun fetchFavoriteArticles(): EitherResult<List<ArticleDto>>

    fun fetchUnreviewedArticles(): EitherResult<List<ArticleDto>>
}