package com.dvidal.samplearticles.features.start.presentation

/**
 * @author diegovidal on 2019-12-24.
 */
sealed class StartViewModelContract {

    sealed class ViewState: StartViewModelContract() {

        object StartArticlesLoading: ViewState()
        object StartArticlesSuccess: ViewState()

        object ClearArticlesLoading: ViewState()
        object ClearArticlesSuccess: ViewState()

        sealed class Warning: ViewState() {

            data class StartArticlesError(val throwable: Throwable): ViewState()
            data class ClearArticlesError(val throwable: Throwable): ViewState()
        }
    }
}