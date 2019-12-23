package com.dvidal.samplearticles.features.articles.data

import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.features.articles.data.remote.ArticlesRemoteResponse
import com.dvidal.samplearticles.features.articles.presentation.ArticleView

/**
 * @author diegovidal on 2019-12-23.
 */
interface ArticlesRepository {

    fun insertAllArticles(list: List<ArticlesRemoteResponse>): EitherResult<Unit>

    fun fetchAllArticles(): EitherResult<List<ArticleView>>

    fun clearAllArticles(): EitherResult<Unit>

    fun reviewArticle(sku: String): EitherResult<Long>

    fun favoriteArticle(sku: String, isFavorite: Boolean): EitherResult<Long>

    fun fetchFavoriteArticles(): EitherResult<Int>

    fun fetchUnreviewedArticles(): EitherResult<List<ArticleView>>
}