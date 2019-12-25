package com.dvidal.samplearticles.features.articles.presentation.review

import com.dvidal.samplearticles.features.articles.presentation.ArticleView

/**
 * @author diegovidal on 2019-12-25.
 */
sealed class ArticlesReviewViewModelContract {

    sealed class ViewState: ArticlesReviewViewModelContract() {

        data class ShowArticlesReview(val list: List<ArticleView>): ViewState()
    }
}