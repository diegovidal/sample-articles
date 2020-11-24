package com.dvidal.samplearticles.features.articles.presentation.review

import androidx.lifecycle.LiveData

/**
 * @author diegovidal on 2019-12-25.
 */
sealed class ArticlesReviewViewContract {

    interface ViewModelEvents {

        val articlesReviewViewStates: LiveData<State>
        val articlesReviewViewEvents: LiveData<Event>

        fun invokeAction(action: Action)
    }

    sealed class Action {

        object InitPage : Action()
        object RefreshGridLayoutSpanCount : Action()
        object SwitchGridLayoutSpanCount : Action()
    }

    sealed class State {

        data class ShowArticlesReview(val articlesReviewView: ArticlesReviewView) : State()
    }

    sealed class Event
}
