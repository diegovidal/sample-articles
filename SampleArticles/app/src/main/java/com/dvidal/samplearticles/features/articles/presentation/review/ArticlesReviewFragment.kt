package com.dvidal.samplearticles.features.articles.presentation.review

import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment

/**
 * @author diegovidal on 2019-12-25.
 */
class ArticlesReviewFragment : BaseFragment() {

    override fun layoutRes() = R.layout.fragment_articles_review
    override fun injectDagger() = component.inject(this)

    companion object {

        const val ARTICLES_REVIEW_FRAGMENT_TAG = "ARTICLES_REVIEW_FRAGMENT_TAG"

        fun newInstance() = ArticlesReviewFragment()
    }
}