package com.dvidal.samplearticles.core.di.component

import com.dvidal.samplearticles.core.di.module.*
import com.dvidal.samplearticles.core.di.module.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author diegovidal on 2019-12-18.
 */

@Singleton
@Component(modules = [
    ApplicationModule::class,
    DatabaseModule::class,
    RemoteModule::class,
    RepositoryModule::class,
    CoroutineDispatcherModule::class,
    ViewModelModule::class
])
interface AppComponent {

    fun activityComponent(): ActivityComponent.Builder
}