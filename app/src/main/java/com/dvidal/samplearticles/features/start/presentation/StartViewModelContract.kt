package com.dvidal.samplearticles.features.start.presentation

import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam

/**
 * @author diegovidal on 2019-12-24.
 */
sealed class StartViewModelContract {

    sealed class ViewState: StartViewModelContract() {

        data class StartArticlesSuccess(val articlesInfoParam: ArticlesInfoParam): ViewState()
        object ClearArticlesSuccess: ViewState()

        sealed class Loading: ViewState() {

            object ClearArticlesLoading: Loading()
            object StartArticlesLoading: Loading()
        }

        sealed class Warning(val throwable: Throwable): ViewState() {

            data class StartArticlesError(val t: Throwable): Warning(t)
            data class ClearArticlesError(val t: Throwable): Warning(t)
        }
    }
}