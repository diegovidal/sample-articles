package com.dvidal.samplearticles.features.articles.data.domain

import com.dvidal.samplearticles.core.common.Either
import com.dvidal.samplearticles.core.datasource.remote.RemoteFailure
import com.dvidal.samplearticles.features.articles.data.local.ArticlesLocalDataSource
import com.dvidal.samplearticles.features.articles.data.remote.ArticlesRemoteDataSource
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepositoryImpl
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
    fun `when fetch all articles and there are articles on local should return and call localDataSource fetch all articles`() {

        val articleView = ArticleView("foo")
        val list = listOf(articleView)
        every { localDataSource.fetchAllArticles() } returns Either.right(list)

        val articles = repository.fetchAllArticles().rightOrNull()
        verify(exactly = 1) { localDataSource.fetchAllArticles() }
        Assert.assertEquals(list, articles)
    }

    @Test
    fun `when fetch all articles and there are not articles on local should call remoteDataSource fetch all articles`() {

        val foo = "foo"
        val numArticles = 10

        val articleView = ArticleView(foo)

        val list = listOf(articleView)
        every { localDataSource.fetchAllArticles() } returns Either.right(emptyList())
        every { remoteDataSource.fetchAllArticles(numArticles) } returns Either.right(list)

        val listDto = listOf(articleView).map { it.mapperToArticleDto() }
        every { localDataSource.insertAllArticles(listDto) } returns Either.right(Unit)

        repository.fetchAllArticles()
        verify(exactly = 1) { remoteDataSource.fetchAllArticles(numArticles) }
        verify(exactly = 2) { localDataSource.fetchAllArticles() }
    }

    @Test
    fun `when fetch all articles and there are not articles on local and error on insert on local should return ErrorLoadingData`() {

        val foo = "foo"
        val numArticles = 10
        val remoteFailure = RemoteFailure.ErrorLoadingData()

        val articleView = ArticleView(foo)

        val list = listOf(articleView)
        every { localDataSource.fetchAllArticles() } returns Either.right(emptyList())
        every { remoteDataSource.fetchAllArticles(numArticles) } returns Either.right(list)

        val listDto = listOf(articleView).map { it.mapperToArticleDto() }
        every { localDataSource.insertAllArticles(listDto) } returns Either.left(remoteFailure)

        val error = repository.fetchAllArticles()
        verify(exactly = 1) { remoteDataSource.fetchAllArticles(numArticles) }
        verify(exactly = 1) { localDataSource.fetchAllArticles() }
        assertTrue(error.isLeft)
    }

    @Test
    fun `when insert all articles should call localDataSource insert all articles`() {

        val list = listOf<ArticleView>()
        val listConverted = list.map { it.mapperToArticleDto() }
        every { localDataSource.insertAllArticles(listConverted) } returns Either.right(Unit)

        repository.insertAllArticles(list)
        verify(exactly = 1) { localDataSource.insertAllArticles(listConverted) }
    }

    @Test
    fun `when clear all articles should call localDataSource clear all articles`() {

        every { localDataSource.clearAllArticles() } returns Either.right(Unit)

        repository.clearAllArticles()
        verify(exactly = 1) { localDataSource.clearAllArticles() }
    }

    @Test
    fun `when review article should call localDataSource review article`() {

        val foo = "foo"

        every { localDataSource.reviewArticle(foo) } returns Either.right(Unit)

        repository.reviewArticle(foo)
        verify(exactly = 1) { localDataSource.reviewArticle(foo) }
    }

    @Test
    fun `when favorite article should call localDataSource favorite article`() {

        val foo = "foo"

        every { localDataSource.favoriteArticle(foo) } returns Either.right(Unit)

        repository.favoriteArticle(foo)
        verify(exactly = 1) { localDataSource.favoriteArticle(foo) }
    }

    @Test
    fun `when fetch favorite articles should return and call localDataSource fetch favorite articles`() {

        val list = listOf<ArticleView>()
        every { localDataSource.fetchFavoriteArticles() } returns Either.right(list)

        val articles = repository.fetchFavoriteArticles().rightOrNull()
        verify(exactly = 1) { localDataSource.fetchFavoriteArticles() }
        Assert.assertEquals(list, articles)
    }

    @Test
    fun `when fetch unreviewed articles should return and call localDataSource fetch unreviewed articles`() {

        val list = listOf<ArticleView>()
        every { localDataSource.fetchUnreviewedArticles() } returns Either.right(list)

        val articles = repository.fetchUnreviewedArticles().rightOrNull()
        verify(exactly = 1) { localDataSource.fetchUnreviewedArticles() }
        Assert.assertEquals(list, articles)
    }

    @Test
    fun `when fetch reviewed articles should return and call localDataSource fetch reviewed articles`() {

        val list = listOf<ArticleView>()
        every { localDataSource.fetchReviewedArticles() } returns Either.right(list)

        val articles = repository.fetchReviewedArticles().rightOrNull()
        verify(exactly = 1) { localDataSource.fetchReviewedArticles() }
        Assert.assertEquals(list, articles)
    }
}