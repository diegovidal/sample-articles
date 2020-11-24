package com.dvidal.samplearticles

import android.app.Application
import com.dvidal.samplearticles.core.common.BaseAppComponent
import com.dvidal.samplearticles.core.di.component.DaggerAppComponent
import com.dvidal.samplearticles.core.di.module.ApplicationModule
import timber.log.Timber

/**
 * @author diegovidal on 2019-12-18.
 */
class MyApplication : Application(), BaseApplication {

    override val appComponent: BaseAppComponent by lazy {
        DaggerAppComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
