package com.dvidal.samplearticles.features.articles.data.remote

import com.dvidal.samplearticles.core.datasource.remote.NetworkHandler
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 2019-12-23.
 */
class ArticlesRemoteDataSourceTest {

    private val remoteApi = mockk<ArticlesRemoteApi>()
    private val networkHandler = mockk<NetworkHandler>()

    private val mockRemoteResponse = ArticlesRemoteResponse.empty()

    private lateinit var remoteDataSource: ArticlesRemoteDataSource

    @Before
    fun setup() {

        remoteDataSource = ArticlesRemoteDataSourceImpl(remoteApi, networkHandler)
        every { networkHandler.isConnected } returns true
    }

    @Test
    fun `when fetch all articles should return and call remoteApi fetch all articles`() = runBlocking {

        val numArticles = 10

        val remoteResponse = ArticlesRemoteResponse.empty()
        coEvery { remoteApi.fetchAllArticles(numArticles) } returns mockRemoteResponse

        val expectedRemoteResponse = remoteDataSource.fetchAllArticles(numArticles).rightOrNull()
        coVerify(exactly = 1) {remoteApi.fetchAllArticles(numArticles)}
        assertEquals(expectedRemoteResponse, remoteResponse.embedded.articles)
    }
}