package com.dvidal.samplearticles.core.di.component

import com.dvidal.samplearticles.core.di.module.ApplicationModule
import com.dvidal.samplearticles.core.di.module.DatabaseModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author diegovidal on 2019-12-18.
 */

@Singleton
@Component(modules = [
    ApplicationModule::class,
    DatabaseModule::class
])
interface AppComponent {

    fun activityComponent(): ActivityComponent.Builder
}