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
    fun addAnArticleAndReadInList() {
        val article = ArticleDto("foo")
        val article2 = ArticleDto("foo", "uhuuu")

        articlesDao.addArticle(article)
        articlesDao.addArticle(article2)
        val articles = articlesDao.fetchArticles()
        assertEquals("uhuuu", articles.first().title)
    }
}