package com.dvidal.samplearticles.features.articles.data.remote

import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-18.
 */
class ArticlesRemoteDataSourceImpl @Inject constructor(
    private val remote: ArticlesRemoteApi
): ArticlesRemoteDataSource {

    override fun fetchAllArticles(): List<ArticlesRemoteResponse> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}