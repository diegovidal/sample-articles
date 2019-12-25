package com.dvidal.samplearticles.features.start.presentation

import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam

/**
 * @author diegovidal on 2019-12-24.
 */
sealed class StartViewModelContract {

    sealed class ViewState: StartViewModelContract() {

        object StartArticlesLoading: ViewState()
        data class StartArticlesSuccess(val articlesInfoParam: ArticlesInfoParam): ViewState()

        object ClearArticlesLoading: ViewState()
        object ClearArticlesSuccess: ViewState()

        sealed class Warning: ViewState() {

            data class StartArticlesError(val throwable: Throwable): ViewState()
            data class ClearArticlesError(val throwable: Throwable): ViewState()
        }
    }
}