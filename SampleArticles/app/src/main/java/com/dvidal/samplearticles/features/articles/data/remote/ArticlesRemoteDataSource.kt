package com.dvidal.samplearticles.features.articles.data.remote

/**
 * @author diegovidal on 2019-12-18.
 */
interface ArticlesRemoteDataSource {

    fun fetchAllArticles(): List<ArticlesResponse>
}