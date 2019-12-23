package com.dvidal.samplearticles.features.articles.data.remote

import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-18.
 */
class ArticlesRemoteDataSourceImpl @Inject constructor(
    private val remote: ArticlesRemoteApi
): ArticlesRemoteDataSource {

    override fun fetchAllArticles(): EitherResult<List<ArticleView>> {
        TODO()
    }
}