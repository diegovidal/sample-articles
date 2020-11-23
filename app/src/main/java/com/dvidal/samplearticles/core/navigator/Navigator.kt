package com.dvidal.samplearticles.core.navigator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.features.articles.presentation.ArticlesActivity
import com.dvidal.samplearticles.features.articles.presentation.review.ArticlesReviewFragment
import com.dvidal.samplearticles.features.articles.presentation.selection.ArticlesSelectionFragment
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import com.dvidal.samplearticles.features.start.presentation.StartActivity
import com.dvidal.samplearticles.features.start.presentation.StartFragment
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author diegovidal on 2019-12-25.
 */

@Singleton
class Navigator @Inject constructor(){

    fun inflateStartFragment(activity: AppCompatActivity) {

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_content, StartFragment.newInstance())
            .commit()
    }

    fun inflateArticlesSelectionFragment(activity: AppCompatActivity) {

        val articlesInfoParam = activity.intent.extras?.getParcelable(StartActivity.EXTRA_ARTICLES_INFO_PARAM) as? ArticlesInfoParam

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_content, ArticlesSelectionFragment.newInstance(articlesInfoParam))
            .commit()
    }

    fun inflateArticlesReviewFragment(activity: AppCompatActivity) {

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_content, ArticlesReviewFragment.newInstance())
            .addToBackStack(ArticlesReviewFragment.ARTICLES_REVIEW_FRAGMENT_TAG)
            .commit()
    }

    fun goToArticlesActivity(activity: AppCompatActivity, articlesInfoParam: ArticlesInfoParam) {
        Intent(activity, ArticlesActivity::class.java).apply {
            putExtra(StartActivity.EXTRA_ARTICLES_INFO_PARAM, articlesInfoParam)
        }.also { activity.startActivity(it) }
    }
}