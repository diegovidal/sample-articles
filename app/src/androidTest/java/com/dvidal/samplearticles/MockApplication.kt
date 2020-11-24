package com.dvidal.samplearticles

import android.app.Application
import com.dvidal.samplearticles.core.common.BaseAppComponent
import com.dvidal.samplearticles.di.DaggerAppTestComponent

class MockApplication: Application(), BaseApplication {

    override val appComponent: BaseAppComponent by lazy {
        DaggerAppTestComponent
            .builder()
            .build()
    }
}