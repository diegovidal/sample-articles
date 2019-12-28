package com.dvidal.samplearticles.features.articles.domain

import androidx.lifecycle.LiveData
import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.features.articles.data.local.ArticleDto
import com.dvidal.samplearticles.features.articles.presentation.ArticleView

/**
 * @author diegovidal on 2019-12-23.
 */
interface ArticlesRepository {

    fun insertAllArticles(list: List<ArticleView>?): EitherResult<Unit>

    fun fetchAllArticles(): EitherResult<List<ArticleView>>

    fun clearAllArticles(): EitherResult<Unit>

    fun reviewArticle(sku: String): EitherResult<Unit>

    fun favoriteArticle(sku: String): EitherResult<Unit>

    fun fetchFavoriteArticles(): EitherResult<List<ArticleView>>

    fun fetchUnreviewedArticles(): EitherResult<LiveData<List<ArticleDto>>>

    fun fetchReviewedArticles(): EitherResult<List<ArticleView>>
}