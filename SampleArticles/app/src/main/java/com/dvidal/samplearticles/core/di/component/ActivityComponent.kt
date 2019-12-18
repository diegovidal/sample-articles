package com.dvidal.samplearticles.core.di.component

import dagger.Subcomponent

/**
 * @author diegovidal on 2019-12-18.
 */

@Subcomponent()
interface ActivityComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): ActivityComponent
    }
}