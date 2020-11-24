package com.dvidal.samplearticles.features.articles.data.local

import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.core.common.catching
import com.dvidal.samplearticles.core.datasource.local.AppDatabase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun fetchUnreviewedArticles(): EitherResult<Flow<List<ArticleView>>> {
        return catching {
            appDatabase.articlesDao().fetchUnreviewedArticles().map { it.map { articleDto -> articleDto.mapperToArticleView() } }
        }
    }

    override suspend fun fetchReviewedArticles(): EitherResult<List<ArticleView>> {
        return catching {
            appDatabase.articlesDao().fetchReviewedArticles().map { it.mapperToArticleView() }
        }
    }
}
