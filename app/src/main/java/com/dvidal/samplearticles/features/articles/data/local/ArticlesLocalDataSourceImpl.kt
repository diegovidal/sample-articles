package com.dvidal.samplearticles.features.articles.data.local

import androidx.lifecycle.LiveData
import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.core.common.catching
import com.dvidal.samplearticles.core.datasource.local.AppDatabase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-18.
 */

class ArticlesLocalDataSourceImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : ArticlesLocalDataSource {

    override suspend fun insertAllArticles(listArticlesDto: List<ArticleDto>?): EitherResult<Unit> {
        return catching { appDatabase.articlesDao().insertAllArticles(listArticlesDto) }
    }

    override suspend fun fetchAllArticles(): EitherResult<List<ArticleView>> {
        return catching {
            appDatabase.articlesDao().fetchAllArticles().map { it.mapperToArticleView() }
        }
    }

    override suspend fun clearAllArticles(): EitherResult<Unit> {
        return catching { appDatabase.articlesDao().clearAllArticles() }
    }

    override suspend fun reviewArticle(sku: String): EitherResult<Unit> {
        return catching { appDatabase.articlesDao().reviewArticle(sku) }
    }

    override suspend fun favoriteArticle(sku: String): EitherResult<Unit> {
        return catching { appDatabase.articlesDao().favoriteArticle(sku) }
    }

    override suspend fun fetchFavoriteArticles(): EitherResult<List<ArticleView>> {
        return catching {
            appDatabase.articlesDao().fetchFavoriteArticles().map { it.mapperToArticleView() }
        }
    }

    override fun fetchUnreviewedArticles(): EitherResult<LiveData<List<ArticleDto>>> {
        return catching {
            appDatabase.articlesDao().fetchUnreviewedArticles()
        }
    }

    override suspend fun fetchReviewedArticles(): EitherResult<List<ArticleView>> {
        return catching {
            appDatabase.articlesDao().fetchReviewedArticles().map { it.mapperToArticleView() }
        }
    }
}