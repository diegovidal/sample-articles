package com.dvidal.samplearticles.features.articles.data.local

import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.features.articles.presentation.ArticleView

/**
 * @author diegovidal on 2019-12-18.
 */
interface ArticlesLocalDataSource {

    fun insertAllArticles(listArticlesDto: List<ArticleDto>)

    fun fetchAllArticles(): EitherResult<List<ArticleView>>

    fun clearAllArticles()

    fun reviewArticle(sku: String)

    fun favoriteArticle(sku: String)

    fun fetchFavoriteArticles(): EitherResult<List<ArticleView>>

    fun fetchUnreviewedArticles(): EitherResult<List<ArticleView>>
}