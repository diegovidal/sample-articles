package com.dvidal.samplearticles.features.articles.domain

import com.dvidal.samplearticles.core.common.Either
import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.core.datasource.remote.RemoteFailure
import com.dvidal.samplearticles.features.articles.data.local.ArticlesLocalDataSource
import com.dvidal.samplearticles.features.articles.data.remote.ArticlesRemoteDataSource
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-23.
 */
class ArticlesRepositoryImpl @Inject constructor(
    private val localDataSource: ArticlesLocalDataSource,
    private val remoteDataSource: ArticlesRemoteDataSource
) : ArticlesRepository {

    override fun insertAllArticles(list: List<ArticleView>?): EitherResult<Unit> {
        val articlesDto = list?.map { it.mapperToArticleDto() }
        return localDataSource.insertAllArticles(articlesDto)
    }

    override fun fetchAllArticles(): EitherResult<List<ArticleView>> {

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

    override fun clearAllArticles(): EitherResult<Unit> {
        return localDataSource.clearAllArticles()
    }

    override fun reviewArticle(sku: String): EitherResult<Unit> {
        return localDataSource.reviewArticle(sku)
    }

    override fun favoriteArticle(sku: String): EitherResult<Unit> {
        return localDataSource.favoriteArticle(sku)
    }

    override fun fetchFavoriteArticles(): EitherResult<List<ArticleView>> {
        return localDataSource.fetchFavoriteArticles()
    }

    override fun fetchUnreviewedArticles(): EitherResult<List<ArticleView>> {
        return localDataSource.fetchUnreviewedArticles()
    }

    companion object {

        const val NUM_ARTICLES = 10
    }
}