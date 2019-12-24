package com.dvidal.samplearticles.features.start.presentation

/**
 * @author diegovidal on 2019-12-24.
 */
sealed class StartViewModelContract {

    sealed class ViewState: StartViewModelContract() {

        object StartArticlesLoading: ViewState()
        object StartArticlesSuccess: ViewState()
        object StartArticlesError: ViewState()

        object ClearArticlesLoading: ViewState()
        object ClearArticlesSuccess: ViewState()
        object ClearArticlesError: ViewState()
    }
}