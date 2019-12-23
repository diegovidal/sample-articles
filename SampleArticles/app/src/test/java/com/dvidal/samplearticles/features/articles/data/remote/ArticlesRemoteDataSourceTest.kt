package com.dvidal.samplearticles.features.articles.data.remote

import com.dvidal.samplearticles.core.datasource.remote.NetworkHandler
import com.dvidal.samplearticles.features.articles.data.local.ArticleDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

/**
 * @author diegovidal on 2019-12-23.
 */
class ArticlesRemoteDataSourceTest {

    private val remoteApi = mockk<ArticlesRemoteApi>()
    private val networkHandler = mockk<NetworkHandler>()

    private val mockCall = mockk<Call<ArticlesRemoteResponse>>()

    private lateinit var remoteDataSource: ArticlesRemoteDataSource

    @Before
    fun setup() {

        remoteDataSource = ArticlesRemoteDataSourceImpl(remoteApi, networkHandler)
        every { networkHandler.isConnected } returns true
    }

    @Test
    fun `when fetch all articles should return and call remoteApi fetch all articles`() {

        val numArticles = 10

        val remoteResponse = ArticlesRemoteResponse.empty()
        every { mockCall.execute() } returns Response.success(remoteResponse)
        every { remoteApi.fetchAllArticles(numArticles) } returns mockCall

        val expectedRemoteResponse = remoteDataSource.fetchAllArticles(numArticles).rightOrNull()
        verify(exactly = 1) {remoteApi.fetchAllArticles(numArticles)}
        assertEquals(expectedRemoteResponse, remoteResponse.articles)
    }
}