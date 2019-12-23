package com.dvidal.samplearticles.features.articles.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dvidal.samplearticles.core.datasource.local.AppDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author diegovidal on 2019-12-18.
 */

@RunWith(AndroidJUnit4::class)
class ArticlesDaoTest {

    private lateinit var articlesDao: ArticlesDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        articlesDao = db.articlesDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun whenAddTwoArticles_shouldReturnTwoArticles() {
        val article = ArticleDto("foo")
        val article2 = ArticleDto("foo2", "test")

        val list = listOf(article, article2)
        articlesDao.insertAllArticles(list)
        val articles = articlesDao.fetchAllArticles()
        assertEquals(2, articles.size)
    }

    @Test
    fun whenAddArticleWithSameSku_shouldReplace() {

        val titleArticle = "The Title"

        val article = ArticleDto("foo")
        val article2 = ArticleDto("foo", titleArticle)

        val list = listOf(article, article2)
        articlesDao.insertAllArticles(list)
        val articles = articlesDao.fetchAllArticles()
        assertEquals(titleArticle, articles.first().title)
    }

    @Test
    fun whenAddArticlesAndClear_shouldReturnEmpty() {

        val article = ArticleDto("foo")
        val article2 = ArticleDto("foo2", "test")

        val list = listOf(article, article2)
        articlesDao.insertAllArticles(list)
        articlesDao.clearAllArticles()
        val articles = articlesDao.fetchAllArticles()
        assertEquals(0, articles.size)
    }

    @Test
    fun whenAddArticle_shouldReturnIsReviewAsFalse() {

        val foo = ArticleDto("foo")

        val list = listOf(foo)
        articlesDao.insertAllArticles(list)
        val articles = articlesDao.fetchAllArticles()

        val articleExpected = articles.first()
        assertEquals(articleExpected.isReview, false)
    }

    @Test
    fun whenAddArticleAndReview_shouldReturnIsReviewAsTrue() {

        val foo = ArticleDto("foo")

        val list = listOf(foo)
        articlesDao.insertAllArticles(list)
        articlesDao.reviewArticle(foo.sku)
        val articles = articlesDao.fetchAllArticles()

        val articleExpected = articles.first()
        assertEquals(articleExpected.isReview, true)
    }

    @Test
    fun whenAddArticle_shouldReturnIsFavoriteAsFalse() {

        val foo = ArticleDto("foo")

        val list = listOf(foo)
        articlesDao.insertAllArticles(list)
        val articles = articlesDao.fetchAllArticles()

        val articleExpected = articles.first()
        assertEquals(articleExpected.isFavorite, false)
    }

    @Test
    fun whenAddArticleAndFavorite_shouldReturnIsFavoriteAsTrue() {

        val foo = ArticleDto("foo")

        val list = listOf(foo)
        articlesDao.insertAllArticles(list)
        articlesDao.favoriteArticle(foo.sku)
        val articles = articlesDao.fetchAllArticles()

        val articleExpected = articles.first()
        assertEquals(articleExpected.isFavorite, true)
    }

    @Test
    fun whenAddArticleAndFavoriteAndFetchUnreviewedArticles_shouldReturnEmpty() {

        val foo = ArticleDto("foo")

        val list = listOf(foo)
        articlesDao.insertAllArticles(list)
        articlesDao.reviewArticle(foo.sku)
        val articles = articlesDao.fetchUnreviewedArticles()

        assertEquals(0, articles.size)
    }

    @Test
    fun whenAddArticleAndFavoriteAndFetchFavoriteArticles_shouldReturnFavoriteArticles() {

        val foo = ArticleDto("foo")

        val list = listOf(foo)
        articlesDao.insertAllArticles(list)
        articlesDao.favoriteArticle(foo.sku)
        val articles = articlesDao.fetchFavoriteArticles()

        val articleExpected = articles.first()
        assertEquals(articleExpected.isFavorite, true)
    }
}