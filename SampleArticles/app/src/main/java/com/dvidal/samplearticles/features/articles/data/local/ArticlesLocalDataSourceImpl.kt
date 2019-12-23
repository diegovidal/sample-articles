package com.dvidal.samplearticles.features.articles.data.local

import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.core.common.catching
import com.dvidal.samplearticles.core.datasource.local.AppDatabase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-18.
 */
class ArticlesLocalDataSourceImpl @Inject constructor (
    private val appDatabase: AppDatabase
): ArticlesLocalDataSource {

    override fun insertAllArticles(listArticlesDto: List<ArticleDto>){
        appDatabase.articlesDao().insertAllArticles(listArticlesDto)
    }

    override fun fetchAllArticles(): EitherResult<List<ArticleView>> {
        return catching { appDatabase.articlesDao().fetchAllArticles().map { it.mapperToArticleView() } }
    }

    override fun clearAllArticles() {
        appDatabase.articlesDao().clearAllArticles()
    }

    override fun reviewArticle(sku: String) {
        appDatabase.articlesDao().reviewArticle(sku)
    }

    override fun favoriteArticle(sku: String) {
        appDatabase.articlesDao().favoriteArticle(sku)
    }

    override fun fetchFavoriteArticles(): EitherResult<List<ArticleView>> {
        return catching { appDatabase.articlesDao().fetchFavoriteArticles().map { it.mapperToArticleView() } }
    }

    override fun fetchUnreviewedArticles(): EitherResult<List<ArticleView>> {
        return catching { appDatabase.articlesDao().fetchUnreviewedArticles().map { it.mapperToArticleView() } }
    }
}