package com.dvidal.samplearticles.features.articles.data.local

import com.dvidal.samplearticles.core.datasource.local.AppDatabase
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

    override fun fetchAllArticles(): List<ArticleDto> {
        return appDatabase.articlesDao().fetchAllArticles()
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

    override fun fetchFavoriteArticles(): List<ArticleDto> {
        return appDatabase.articlesDao().fetchFavoriteArticles()
    }

    override fun fetchUnreviewedArticles(): List<ArticleDto> {
        return appDatabase.articlesDao().fetchUnreviewedArticles()
    }
}