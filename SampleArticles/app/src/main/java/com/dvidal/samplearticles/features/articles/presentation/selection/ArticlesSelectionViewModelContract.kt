package com.dvidal.samplearticles.features.articles.presentation.selection

import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam

/**
 * @author diegovidal on 2019-12-25.
 */
sealed class ArticlesSelectionViewModelContract {

    sealed class UserInteraction(var sku: String) {

        data class LikeArticle(var param: String = ""): UserInteraction(param)
        data class DislikeArticle(var param: String = ""): UserInteraction(param)
    }

    sealed class ViewState(val articlesInfoParam: ArticlesInfoParam?): ArticlesSelectionViewModelContract() {

        data class ShowTwoArticlesOnQueue(val aip: ArticlesInfoParam?, val firstArticle: ArticleView, val secondArticle: ArticleView): ViewState(aip)
        data class ShowLastArticleOnQueue(val aip: ArticlesInfoParam?, val lastArticle: ArticleView): ViewState(aip)

        data class ArticlesSelectionEmpty(val aip: ArticlesInfoParam?): ViewState(aip)
    }
}