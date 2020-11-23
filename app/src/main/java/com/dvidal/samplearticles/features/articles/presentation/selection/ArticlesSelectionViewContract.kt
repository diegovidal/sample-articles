package com.dvidal.samplearticles.features.articles.presentation.selection

import androidx.lifecycle.LiveData
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam

/**
 * @author diegovidal on 2019-12-25.
 */
sealed class ArticlesSelectionViewContract {

    interface ViewModelEvents {

        val articlesSelectionViewStates: LiveData<State>
        val articlesSelectionViewEvents: LiveData<Event>

        fun invokeAction(action: Action)
    }

    sealed class Action {

        data class InitPage(var articlesInfoParam: ArticlesInfoParam): Action()
        object NavigateToArticleReviews: Action()

        sealed class ReviewArticle(var sku: String): Action() {

            data class LikeArticle(var param: String = ""): ReviewArticle(param)
            data class DislikeArticle(var param: String = ""): ReviewArticle(param)
        }
    }

    sealed class State(val articlesInfoParam: ArticlesInfoParam?) {

        data class ShowTwoArticlesOnQueue(val aip: ArticlesInfoParam?, val firstArticle: ArticleView, val secondArticle: ArticleView): State(aip)
        data class ShowLastArticleOnQueue(val aip: ArticlesInfoParam?, val lastArticle: ArticleView): State(aip)

        data class ArticlesSelectionEmpty(val aip: ArticlesInfoParam?): State(aip)
    }

    sealed class Event {

        object GoToArticleReviews: Event()
    }
}