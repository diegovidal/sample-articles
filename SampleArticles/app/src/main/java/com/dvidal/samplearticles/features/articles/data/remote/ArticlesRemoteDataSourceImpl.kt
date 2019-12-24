package com.dvidal.samplearticles.features.articles.data.remote

import com.dvidal.samplearticles.core.common.BaseRequester
import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.core.datasource.remote.NetworkHandler
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-18.
 */
class ArticlesRemoteDataSourceImpl @Inject constructor(
    private val remoteApi: ArticlesRemoteApi,
    networkHandler: NetworkHandler
): BaseRequester(networkHandler), ArticlesRemoteDataSource {

    override fun fetchAllArticles(numArticles: Int): EitherResult<List<ArticleView>> {

        return request(remoteApi.fetchAllArticles(limit = numArticles), { response ->
            response.embedded.articles.map { it.mapperToArticleView()}}, ArticlesRemoteResponse.empty())
    }
}