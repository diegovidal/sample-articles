package com.dvidal.samplearticles.core.di.module.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplearticles.features.articles.presentation.review.ArticlesReviewViewModel
import com.dvidal.samplearticles.features.articles.presentation.selection.ArticlesSelectionViewModel
import com.dvidal.samplearticles.features.start.presentation.StartViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author diegovidal on 2019-12-24.
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    abstract fun bindStartViewModel(viewModel: StartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticlesSelectionViewModel::class)
    abstract fun bindArticlesSelectionViewModel(viewModel: ArticlesSelectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticlesReviewViewModel::class)
    abstract fun bindArticlesReviewViewModel(viewModel: ArticlesReviewViewModel): ViewModel
}
