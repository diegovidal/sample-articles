package com.dvidal.samplearticles.features.articles.data.local

import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import kotlinx.coroutines.flow.Flow

/**
 * @author diegovidal on 2019-12-18.
 */
interface ArticlesLocalDataSource {

    suspend fun insertAllArticles(listArticlesDto: List<ArticleDto>?): EitherResult<Unit>

    suspend fun fetchAllArticles(): EitherResult<List<ArticleView>>

    suspend fun clearAllArticles(): EitherResult<Unit>

    suspend fun reviewArticle(sku: String): EitherResult<Unit>

    suspend fun favoriteArticle(sku: String): EitherResult<Unit>

    suspend fun fetchFavoriteArticles(): EitherResult<List<ArticleView>>

    fun fetchUnreviewedArticles(): EitherResult<Flow<List<ArticleView>>>

    suspend fun fetchReviewedArticles(): EitherResult<List<ArticleView>>
}
