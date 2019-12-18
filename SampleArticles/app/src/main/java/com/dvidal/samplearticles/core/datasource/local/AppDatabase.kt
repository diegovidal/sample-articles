package com.dvidal.samplearticles.core.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvidal.samplearticles.features.articles.data.local.ArticlesDao
import com.dvidal.samplearticles.features.articles.data.local.ArticleDto

/**
 * @author diegovidal on 2019-12-18.
 */
@Database(entities = [
    ArticleDto::class
], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao
}