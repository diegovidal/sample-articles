package com.dvidal.samplearticles.features.articles.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.features.articles.presentation.selection.ArticlesSelectionFragment
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import com.dvidal.samplearticles.features.start.presentation.StartActivity.Companion.EXTRA_ARTICLES_INFO_PARAM

/**
 * @author diegovidal on 2019-12-24.
 */
class ArticlesActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ativity_articles)
        inflateArticlesSelectionFragment()
    }

    private fun inflateArticlesSelectionFragment() {

        val articlesInfoParam = intent.extras?.getParcelable(EXTRA_ARTICLES_INFO_PARAM) as? ArticlesInfoParam

        supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_content, ArticlesSelectionFragment.newInstance(articlesInfoParam))
            ?.commit()
    }

    private fun inflateArticlesReviewsFragment() {

    }
}