package com.dvidal.samplearticles.features.articles.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dvidal.samplearticles.MyApplication
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.navigator.Navigator
import com.dvidal.samplearticles.features.articles.presentation.review.ArticlesReviewFragment
import com.dvidal.samplearticles.features.articles.presentation.review.ArticlesReviewFragment.Companion.ARTICLES_REVIEW_FRAGMENT_TAG
import com.dvidal.samplearticles.features.articles.presentation.selection.ArticlesSelectionFragment
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import com.dvidal.samplearticles.features.start.presentation.StartActivity.Companion.EXTRA_ARTICLES_INFO_PARAM
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-24.
 */
class ArticlesActivity: AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    private fun injectDagger() {
        (application as MyApplication).appComponent.activityComponent()
            .build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ativity_articles)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        injectDagger()

        if (savedInstanceState == null)
            inflateArticlesSelectionFragment()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0)
            super.onBackPressed()
        else finish()
    }

    fun updateActionBarTitle(title: Int) {
        supportActionBar?.setTitle(title)
    }

    private fun inflateArticlesSelectionFragment() {
        navigator.inflateArticlesSelectionFragment(this)
    }

    fun inflateArticlesReviewFragment() {
        navigator.inflateArticlesReviewFragment(this)
    }
}