package com.dvidal.samplearticles.features.articles.presentation.review

import androidx.lifecycle.LiveData
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.articles.presentation.review.ArticlesReviewAdapter.Companion.ITEM_VIEW_TYPE_GRID
import com.dvidal.samplearticles.features.articles.presentation.review.ArticlesReviewAdapter.Companion.ITEM_VIEW_TYPE_LIST
import com.dvidal.samplearticles.features.articles.presentation.selection.ArticlesSelectionViewContract

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

        object InitPage: Action()
        object RefreshGridLayoutSpanCount: Action()
        object SwitchGridLayoutSpanCount: Action()
    }

    sealed class State {

        data class ShowArticlesReview(val articlesReviewView: ArticlesReviewView) : State()
    }

    sealed class Event
}