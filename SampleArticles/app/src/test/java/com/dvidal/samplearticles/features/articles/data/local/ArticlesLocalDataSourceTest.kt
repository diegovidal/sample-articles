package com.dvidal.samplearticles.features.articles.data.local

import androidx.lifecycle.MutableLiveData
import com.dvidal.samplearticles.core.datasource.local.AppDatabase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
    fun `when insert all articles should call articlesDao insert all articles`() {

        val list = listOf<ArticleDto>()
        every { appDatabase.articlesDao().insertAllArticles(list) } returns Unit

        dataSource.insertAllArticles(list)
        verify(exactly = 1) {appDatabase.articlesDao().insertAllArticles(list)}
    }

    @Test
    fun `when fetch all articles should return and call articlesDao fetch all articles`() {

        val list = listOf<ArticleDto>()
        every { appDatabase.articlesDao().fetchAllArticles() } returns list

        val articles = dataSource.fetchAllArticles().rightOrNull()
        verify(exactly = 1) {appDatabase.articlesDao().fetchAllArticles()}
        assertEquals(list, articles)
    }

    @Test
    fun `when clear all articles should call articlesDao clear all articles`() {

        every { appDatabase.articlesDao().clearAllArticles() } returns Unit

        dataSource.clearAllArticles()
        verify(exactly = 1) {appDatabase.articlesDao().clearAllArticles()}
    }

    @Test
    fun `when review article should call articlesDao review article`() {

        val foo = "foo"

        every { appDatabase.articlesDao().reviewArticle(foo) } returns Unit

        dataSource.reviewArticle(foo)
        verify(exactly = 1) {appDatabase.articlesDao().reviewArticle(foo)}
    }

    @Test
    fun `when favorite article should call articlesDao favorite article`() {

        val foo = "foo"

        every { appDatabase.articlesDao().favoriteArticle(foo) } returns Unit

        dataSource.favoriteArticle(foo)
        verify(exactly = 1) {appDatabase.articlesDao().favoriteArticle(foo)}
    }

    @Test
    fun `when fetch favorite articles should return and call articlesDao fetch favorite articles`() {

        val list = listOf<ArticleDto>()
        every { appDatabase.articlesDao().fetchFavoriteArticles() } returns list

        val articles = dataSource.fetchFavoriteArticles().rightOrNull()
        verify(exactly = 1) {appDatabase.articlesDao().fetchFavoriteArticles()}
        assertEquals(list, articles)
    }

    @Test
    fun `when fetch unreviewed articles should return and call articlesDao fetch unreviewed articles`() {

        val list = listOf<ArticleDto>()
        every { appDatabase.articlesDao().fetchUnreviewedArticles() } returns MutableLiveData(list)

        val articles = dataSource.fetchUnreviewedArticles().rightOrNull()?.value
        verify(exactly = 1) {appDatabase.articlesDao().fetchUnreviewedArticles()}
        assertEquals(list, articles)
    }

    @Test
    fun `when fetch reviewed articles should return and call articlesDao fetch reviewed articles`() {

        val list = listOf<ArticleDto>()
        every { appDatabase.articlesDao().fetchReviewedArticles() } returns list

        val articles = dataSource.fetchReviewedArticles().rightOrNull()
        verify(exactly = 1) {appDatabase.articlesDao().fetchReviewedArticles()}
        assertEquals(list, articles)
    }
}