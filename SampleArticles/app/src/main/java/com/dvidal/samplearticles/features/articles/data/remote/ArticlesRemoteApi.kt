package com.dvidal.samplearticles.features.articles.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author diegovidal on 2019-12-23.
 */
interface ArticlesRemoteApi {

    @GET("categories/100/articles?appDomain={appDomain}&locale={locale}&limit={limit}")
    fun fetchAllArticles(
        @Path("limit") limit: Int,
        @Path("appDomain") appDomain: String,
        @Path("locale") locale: String
    ): Call<ArticlesRemoteResponse>
}