package com.dvidal.samplearticles.features.articles.domain

import androidx.lifecycle.LiveData
import com.dvidal.samplearticles.BuildConfig
import com.dvidal.samplearticles.core.common.Either
import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.core.datasource.remote.RemoteFailure
import com.dvidal.samplearticles.features.articles.data.local.ArticleDto
import com.dvidal.samplearticles.features.articles.data.local.ArticlesLocalDataSource
import com.dvidal.samplearticles.features.articles.data.remote.ArticlesRemoteDataSource
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-23.
 */
class ArticlesRepositoryImpl @Inject constructor(
    private val localDataSource: ArticlesLocalDataSource,
    private val remoteDataSource: ArticlesRemoteDataSource
) : ArticlesRepository {

    override suspend fun insertAllArticles(list: List<ArticleView>?): EitherResult<Unit> {
        val articlesDto = list?.map { it.mapperToArticleDto() }
        return localDataSource.insertAllArticles(articlesDto)
    }

    override suspend fun fetchAllArticles(): EitherResult<List<ArticleView>> {

        val localResult = localDataSource.fetchAllArticles()
        val localArticlesReviews = localResult.rightOrNull()

        if (localResult.rightOrNull() != null && localArticlesReviews?.isNotEmpty() == true) {
            return localResult
        } else {
            val remoteResult = remoteDataSource.fetchAllArticles(NUM_ARTICLES)
            val remoteArticlesReviews = remoteResult.rightOrNull()
            val resultInsertAllArticles = insertAllArticles(remoteArticlesReviews)
            if (resultInsertAllArticles.isRight){
                return localDataSource.fetchAllArticles()
            }
        }

        return Either.left(RemoteFailure.ErrorLoadingData())
    }

    override suspend fun clearAllArticles(): EitherResult<Unit> {
        return localDataSource.clearAllArticles()
    }

    override suspend fun reviewArticle(sku: String): EitherResult<Unit> {
        return localDataSource.reviewArticle(sku)
    }

    override suspend fun favoriteArticle(sku: String): EitherResult<Unit> {
        return localDataSource.favoriteArticle(sku)
    }

    override suspend fun fetchFavoriteArticles(): EitherResult<List<ArticleView>> {
        return localDataSource.fetchFavoriteArticles()
    }

    override fun fetchUnreviewedArticles(): EitherResult<Flow<List<ArticleView>>> {
        return localDataSource.fetchUnreviewedArticles()
    }

    override suspend fun fetchReviewedArticles(): EitherResult<List<ArticleView>> {
        return localDataSource.fetchReviewedArticles()
    }

    companion object {

        const val NUM_ARTICLES = BuildConfig.NUM_ARTICLES
    }
}