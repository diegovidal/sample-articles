package com.dvidal.samplearticles.core.di.component

import com.dvidal.samplearticles.features.articles.presentation.ArticlesActivity
import com.dvidal.samplearticles.features.articles.presentation.review.ArticlesReviewFragment
import com.dvidal.samplearticles.features.articles.presentation.selection.ArticlesSelectionFragment
import com.dvidal.samplearticles.features.start.presentation.StartActivity
import com.dvidal.samplearticles.features.start.presentation.StartFragment
import dagger.Subcomponent

/**
 * @author diegovidal on 2019-12-18.
 */

@Subcomponent
interface ActivityComponent {

    fun inject(startFragment: StartFragment)
    fun inject(articlesSelectionFragment: ArticlesSelectionFragment)
    fun inject(articlesReviewFragment: ArticlesReviewFragment)

    fun inject(articlesActivity: ArticlesActivity)
    fun inject(startActivity: StartActivity)

    @Subcomponent.Builder
    interface Builder {

        fun build(): ActivityComponent
    }
}