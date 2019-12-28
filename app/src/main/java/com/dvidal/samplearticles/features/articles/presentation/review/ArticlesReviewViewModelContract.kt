package com.dvidal.samplearticles.features.articles.presentation.review

import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.articles.presentation.review.ArticlesReviewAdapter.Companion.ITEM_VIEW_TYPE_GRID
import com.dvidal.samplearticles.features.articles.presentation.review.ArticlesReviewAdapter.Companion.ITEM_VIEW_TYPE_LIST

/**
 * @author diegovidal on 2019-12-25.
 */
sealed class ArticlesReviewViewModelContract {

    sealed class ViewState : ArticlesReviewViewModelContract() {

        data class ShowArticlesReview(val list: List<ArticleView>) : ViewState()

        sealed class SwitchGridLayout(val spanCount: Int, val drawable: Int) : ViewState() {

            object SwitchGridLayoutTypeList :
                SwitchGridLayout(ITEM_VIEW_TYPE_LIST, R.drawable.ic_list)

            object SwitchGridLayoutTypeGrid :
                SwitchGridLayout(ITEM_VIEW_TYPE_GRID, R.drawable.ic_grid)

            fun isTypeList() = this is SwitchGridLayoutTypeList
            fun isTypeGrid() = this is SwitchGridLayoutTypeGrid
        }

    }
}