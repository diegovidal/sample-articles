package com.dvidal.samplearticles.core.di.component

import com.dvidal.samplearticles.features.start.presentation.StartFragment
import dagger.Subcomponent

/**
 * @author diegovidal on 2019-12-18.
 */

@Subcomponent
interface ActivityComponent {

    fun inject(productsFragment: StartFragment)

    @Subcomponent.Builder
    interface Builder {

        fun build(): ActivityComponent
    }
}