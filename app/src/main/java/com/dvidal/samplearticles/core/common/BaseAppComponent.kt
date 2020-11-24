package com.dvidal.samplearticles.core.common

import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplearticles.core.di.component.ActivityComponent

interface BaseAppComponent {

    fun activityComponent(): ActivityComponent.Builder
    val viewModelFactor: ViewModelProvider.Factory
}
