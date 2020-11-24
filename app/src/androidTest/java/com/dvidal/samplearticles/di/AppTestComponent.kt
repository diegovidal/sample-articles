package com.dvidal.samplearticles.di

import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplearticles.core.common.BaseAppComponent
import com.dvidal.samplearticles.core.di.component.ActivityComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ViewModelTestModule::class
])
interface AppTestComponent : BaseAppComponent {

    override fun activityComponent(): ActivityComponent.Builder
    override val viewModelFactor: ViewModelProvider.Factory
}
