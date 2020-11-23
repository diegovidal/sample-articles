package com.dvidal.samplearticles.features.articles.data.local

import androidx.lifecycle.MutableLiveData
import com.dvidal.samplearticles.core.datasource.local.AppDatabase
import io.mockk.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 2019-12-23.
 */
class ArticlesLocalDataSourceTest {

    private val appDatabase = mockk<AppDatabase>()

    lateinit var dataSource: ArticlesLocalDataSource

    @Before
    fun setup() {
        dataSource = ArticlesLocalDataSourceImpl(appDatabase)
    }

    @Test
    fun `when insert all articles should call articlesDao insert all articles`() = runBlocking {

        val list = listOf<ArticleDto>()
        coEvery { appDatabase.articlesDao().insertAllArticles(list) } returns Unit

        dataSource.insertAllArticles(list)
        coVerify(exactly = 1) {appDatabase.articlesDao().insertAllArticles(list)}
    }

    @Test
    fun `when fetch all articles should return and call articlesDao fetch all articles`() = runBlocking {

        val list = listOf<ArticleDto>()
        coEvery { appDatabase.articlesDao().fetchAllArticles() } returns list

        val articles = dataSource.fetchAllArticles().rightOrNull()
        coVerify(exactly = 1) {appDatabase.articlesDao().fetchAllArticles()}
        assertEquals(list, articles)
    }

    @Test
    fun `when clear all articles should call articlesDao clear all articles`() = runBlocking {

        coEvery { appDatabase.articlesDao().clearAllArticles() } returns Unit

        dataSource.clearAllArticles()
        coVerify(exactly = 1) {appDatabase.articlesDao().clearAllArticles()}
    }

    @Test
    fun `when review article should call articlesDao review article`() = runBlocking {

        val foo = "foo"

        coEvery { appDatabase.articlesDao().reviewArticle(foo) } returns Unit

        dataSource.reviewArticle(foo)
        coVerify(exactly = 1) {appDatabase.articlesDao().reviewArticle(foo)}
    }

    @Test
    fun `when favorite article should call articlesDao favorite article`() = runBlocking {

        val foo = "foo"

        coEvery { appDatabase.articlesDao().favoriteArticle(foo) } returns Unit

        dataSource.favoriteArticle(foo)
        coVerify(exactly = 1) {appDatabase.articlesDao().favoriteArticle(foo)}
    }

    @Test
    fun `when fetch favorite articles should return and call articlesDao fetch favorite articles`() = runBlocking {

        val list = listOf<ArticleDto>()
        coEvery { appDatabase.articlesDao().fetchFavoriteArticles() } returns list

        val articles = dataSource.fetchFavoriteArticles().rightOrNull()
        coVerify(exactly = 1) {appDatabase.articlesDao().fetchFavoriteArticles()}
        assertEquals(list, articles)
    }

    @Test
    fun `when fetch unreviewed articles should return and call articlesDao fetch unreviewed articles`() {

        val list = listOf<ArticleDto>()
        val flowList = flow { emit(list) }
        every { appDatabase.articlesDao().fetchUnreviewedArticles() } returns flowList

        val articles = dataSource.fetchUnreviewedArticles().rightOrNull()
        verify(exactly = 1) {appDatabase.articlesDao().fetchUnreviewedArticles()}
        assertEquals(list, articles)
    }

    @Test
    fun `when fetch reviewed articles should return and call articlesDao fetch reviewed articles`() = runBlocking {

        val list = listOf<ArticleDto>()
        coEvery { appDatabase.articlesDao().fetchReviewedArticles() } returns list

        val articles = dataSource.fetchReviewedArticles().rightOrNull()
        coVerify(exactly = 1) {appDatabase.articlesDao().fetchReviewedArticles()}
        assertEquals(list, articles)
    }
}