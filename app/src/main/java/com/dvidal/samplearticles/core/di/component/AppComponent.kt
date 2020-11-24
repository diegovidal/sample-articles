package com.dvidal.samplearticles.core.di.component

import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplearticles.core.common.BaseAppComponent
import com.dvidal.samplearticles.core.di.module.ApplicationModule
import com.dvidal.samplearticles.core.di.module.CoroutineDispatcherModule
import com.dvidal.samplearticles.core.di.module.DatabaseModule
import com.dvidal.samplearticles.core.di.module.RemoteModule
import com.dvidal.samplearticles.core.di.module.RepositoryModule
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
interface AppComponent : BaseAppComponent {

    override fun activityComponent(): ActivityComponent.Builder
    override val viewModelFactor: ViewModelProvider.Factory
}
