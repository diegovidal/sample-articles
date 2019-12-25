package com.dvidal.samplearticles.features.articles.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.features.articles.presentation.selection.ArticlesSelectionFragment

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

        supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_content, ArticlesSelectionFragment.newInstance())
            ?.addToBackStack(ArticlesSelectionFragment.TAG)
            ?.commit()
    }

    private fun inflateArticlesReviewsFragment() {

    }
}