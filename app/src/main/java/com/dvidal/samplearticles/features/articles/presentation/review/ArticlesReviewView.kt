package com.dvidal.samplearticles.features.articles.presentation.review

import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.features.articles.presentation.ArticleView

data class ArticlesReviewView(
    var list: List<ArticleView> = emptyList(),
    var switchGridLayout: SwitchGridLayout = SwitchGridLayout.SwitchGridLayoutTypeList
)

sealed class SwitchGridLayout(val spanCount: Int, val drawable: Int) {

    object SwitchGridLayoutTypeList :
        SwitchGridLayout(ArticlesReviewAdapter.ITEM_VIEW_TYPE_LIST, R.drawable.ic_list)

    object SwitchGridLayoutTypeGrid :
        SwitchGridLayout(ArticlesReviewAdapter.ITEM_VIEW_TYPE_GRID, R.drawable.ic_grid)

    fun isTypeList() = this is SwitchGridLayoutTypeList
    fun isTypeGrid() = this is SwitchGridLayoutTypeGrid
}