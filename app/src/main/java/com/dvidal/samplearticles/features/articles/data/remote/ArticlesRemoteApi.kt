package com.dvidal.samplearticles.features.articles.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author diegovidal on 2019-12-23.
 */
interface ArticlesRemoteApi {

    @GET("categories/100/articles")
    suspend fun fetchAllArticles(
        @Query("limit") limit: Int,
        @Query("appDomain") appDomain: String = "1",
        @Query("locale") locale: String = "de_DE"
    ): ArticlesRemoteResponse
}
