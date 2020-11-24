package com.dvidal.samplearticles.core.di.module

import com.dvidal.samplearticles.core.datasource.local.AppDatabase
import com.dvidal.samplearticles.core.datasource.remote.NetworkHandler
import com.dvidal.samplearticles.features.articles.data.local.ArticlesLocalDataSource
import com.dvidal.samplearticles.features.articles.data.local.ArticlesLocalDataSourceImpl
import com.dvidal.samplearticles.features.articles.data.remote.ArticlesRemoteApi
import com.dvidal.samplearticles.features.articles.data.remote.ArticlesRemoteDataSource
import com.dvidal.samplearticles.features.articles.data.remote.ArticlesRemoteDataSourceImpl
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author diegovidal on 2019-12-24.
 */
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideArticlesLocalDataSource(appDatabase: AppDatabase): ArticlesLocalDataSource {
        return ArticlesLocalDataSourceImpl(appDatabase)
    }

    @Singleton
    @Provides
    fun provideArticlesRemoteDataSource(
        remoteApi: ArticlesRemoteApi,
        networkHandler: NetworkHandler
    ): ArticlesRemoteDataSource {

        return ArticlesRemoteDataSourceImpl(remoteApi, networkHandler)
    }

    @Singleton
    @Provides
    fun provideArticlesRepository(
        localDataSource: ArticlesLocalDataSource,
        remoteDataSource: ArticlesRemoteDataSource
    ): ArticlesRepository {

        return ArticlesRepositoryImpl(localDataSource, remoteDataSource)
    }
}
