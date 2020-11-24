package com.dvidal.samplearticles.features.start.presentation

import androidx.lifecycle.LiveData
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam

/**
 * @author diegovidal on 2019-12-24.
 */
sealed class StartViewContract {

    interface ViewModelEvents {

        val startViewStates: LiveData<State>
        val startViewEvents: LiveData<Event>

        fun invokeAction(action: Action)
    }

    sealed class Action {

        object StartArticles : Action()
        object ClearArticles : Action()
    }

    sealed class State {

        data class StartViewState(val isStartArticlesLoading: Boolean, val isClearArticlesLoading: Boolean) : State()
    }

    sealed class Event {

        data class StartArticlesSuccess(val articlesInfoParam: ArticlesInfoParam) : Event()
        object ClearArticlesSuccess : Event()

        data class DisplayWarning(val throwable: Throwable) : Event()
    }
}
