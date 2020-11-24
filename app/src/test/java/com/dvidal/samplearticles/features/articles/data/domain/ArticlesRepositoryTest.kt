package com.dvidal.samplearticles.features.articles.data.domain

import com.dvidal.samplearticles.core.common.Either
import com.dvidal.samplearticles.core.datasource.remote.RemoteFailure
import com.dvidal.samplearticles.features.articles.data.local.ArticlesLocalDataSource
import com.dvidal.samplearticles.features.articles.data.remote.ArticlesRemoteDataSource
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepositoryImpl
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 2019-12-23.
 */
class ArticlesRepositoryTest {

    private val localDataSource = mockk<ArticlesLocalDataSource>()
    private val remoteDataSource = mockk<ArticlesRemoteDataSource>()

    private lateinit var repository: ArticlesRepository

    @Before
    fun setup() {

        repository =
            ArticlesRepositoryImpl(
                localDataSource,
                remoteDataSource
            )
    }

    @Test
    fun `when fetch all articles and there are articles on local should return and call localDataSource fetch all articles`() = runBlocking {

        val articleView = ArticleView("foo")
        val list = listOf(articleView)
        coEvery { localDataSource.fetchAllArticles() } returns Either.right(list)

        val articles = repository.fetchAllArticles().rightOrNull()
        coVerify(exactly = 1) { localDataSource.fetchAllArticles() }
        Assert.assertEquals(list, articles)
    }

    @Test
    fun `when fetch all articles and there are not articles on local should call remoteDataSource fetch all articles`() = runBlocking {

        val foo = "foo"

        val articleView = ArticleView(foo)

        val list = listOf(articleView)
        coEvery { localDataSource.fetchAllArticles() } returns Either.right(emptyList())
        coEvery { remoteDataSource.fetchAllArticles(any()) } returns Either.right(list)

        val listDto = listOf(articleView).map { it.mapperToArticleDto() }
        coEvery { localDataSource.insertAllArticles(listDto) } returns Either.right(Unit)

        repository.fetchAllArticles()
        coVerify(exactly = 1) { remoteDataSource.fetchAllArticles(any()) }
        coVerify(exactly = 2) { localDataSource.fetchAllArticles() }
    }

    @Test
    fun `when fetch all articles and there are not articles on local and error on insert on local should return ErrorLoadingData`() = runBlocking {

        val foo = "foo"
        val remoteFailure = RemoteFailure.ErrorLoadingData()

        val articleView = ArticleView(foo)

        val list = listOf(articleView)
        coEvery { localDataSource.fetchAllArticles() } returns Either.right(emptyList())
        coEvery { remoteDataSource.fetchAllArticles(any()) } returns Either.right(list)

        val listDto = listOf(articleView).map { it.mapperToArticleDto() }
        coEvery { localDataSource.insertAllArticles(listDto) } returns Either.left(remoteFailure)

        val error = repository.fetchAllArticles()
        coVerify(exactly = 1) { remoteDataSource.fetchAllArticles(any()) }
        coVerify(exactly = 1) { localDataSource.fetchAllArticles() }
        assertTrue(error.isLeft)
    }

    @Test
    fun `when insert all articles should call localDataSource insert all articles`() = runBlocking {

        val list = listOf<ArticleView>()
        val listConverted = list.map { it.mapperToArticleDto() }
        coEvery { localDataSource.insertAllArticles(listConverted) } returns Either.right(Unit)

        repository.insertAllArticles(list)
        coVerify(exactly = 1) { localDataSource.insertAllArticles(listConverted) }
    }

    @Test
    fun `when clear all articles should call localDataSource clear all articles`() = runBlocking {

        coEvery { localDataSource.clearAllArticles() } returns Either.right(Unit)

        repository.clearAllArticles()
        coVerify(exactly = 1) { localDataSource.clearAllArticles() }
    }

    @Test
    fun `when review article should call localDataSource review article`() = runBlocking {

        val foo = "foo"

        coEvery { localDataSource.reviewArticle(foo) } returns Either.right(Unit)

        repository.reviewArticle(foo)
        coVerify(exactly = 1) { localDataSource.reviewArticle(foo) }
    }

    @Test
    fun `when favorite article should call localDataSource favorite article`() = runBlocking {

        val foo = "foo"

        coEvery { localDataSource.favoriteArticle(foo) } returns Either.right(Unit)

        repository.favoriteArticle(foo)
        coVerify(exactly = 1) { localDataSource.favoriteArticle(foo) }
    }

    @Test
    fun `when fetch favorite articles should return and call localDataSource fetch favorite articles`() = runBlocking {

        val list = listOf<ArticleView>()
        coEvery { localDataSource.fetchFavoriteArticles() } returns Either.right(list)

        val articles = repository.fetchFavoriteArticles().rightOrNull()
        coVerify(exactly = 1) { localDataSource.fetchFavoriteArticles() }
        Assert.assertEquals(list, articles)
    }

    @Test
    fun `when fetch reviewed articles should return and call localDataSource fetch reviewed articles`() = runBlocking {

        val list = listOf<ArticleView>()
        coEvery { localDataSource.fetchReviewedArticles() } returns Either.right(list)

        val articles = repository.fetchReviewedArticles().rightOrNull()
        coVerify(exactly = 1) { localDataSource.fetchReviewedArticles() }
        Assert.assertEquals(list, articles)
    }
}
