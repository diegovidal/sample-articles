package com.dvidal.samplearticles.features.articles.presentation.review

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dvidal.samplearticles.BaseEspressoTest
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.presentation.StartViewContract
import com.dvidal.samplearticles.features.start.presentation.StartViewModel
import com.dvidal.samplearticles.utils.FragmentTestRule
import com.dvidal.samplearticles.utils.RecyclerViewMatcher
import io.mockk.every
import io.mockk.mockk
import kotlinx.android.synthetic.main.recycler_item_article_review.view.iv_like_article
import kotlinx.android.synthetic.main.recycler_item_article_review.view.tv_article_title
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticlesReviewFragmentTest: BaseEspressoTest() {

    @get:Rule
    val fragmentRule = FragmentTestRule(ArticlesReviewFragment::class.java)

    private val articlesReviewView = ArticlesReviewView(listOf(ArticleView(title = "test"), ArticleView(title = "test")))
    private val states = MutableLiveData<ArticlesReviewViewContract.State>()
    private val events = MutableLiveData<ArticlesReviewViewContract.Event>()

    private val mockViewModel = mockk<ArticlesReviewViewModel>(relaxUnitFun = true)

    @Before
    fun setup() {

        val viewModelFactory = application.appComponent.viewModelFactor
        every { viewModelFactory.create(ArticlesReviewViewModel::class.java) } returns mockViewModel

        every { mockViewModel.articlesReviewViewStates } returns states
        every { mockViewModel.articlesReviewViewEvents } returns events
    }

    @Test
    fun whenShowArticlesReview_shouldShowRecyclerViewItem() {
        fragmentRule.launchActivity(null)

        runBlocking(Dispatchers.Main) {
            states.postValue(ArticlesReviewViewContract.State.ShowArticlesReview(articlesReviewView))
        }

        onView(RecyclerViewMatcher().atPositionOnView(1, R.id.iv_article_review, R.id.rv_articles_review))
            .check(matches(isDisplayed()))
        onView(RecyclerViewMatcher().atPositionOnView(1, R.id.tv_article_title, R.id.rv_articles_review))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenShowArticlesReview_andHasFavorite_shouldShowRecyclerViewItem() {
        fragmentRule.launchActivity(null)

        val mock = ArticlesReviewView(listOf(ArticleView(title = "test", isFavorite = true), ArticleView(title = "test", isFavorite = true)))
        runBlocking(Dispatchers.Main) {
            states.postValue(ArticlesReviewViewContract.State.ShowArticlesReview(mock))
        }

        onView(RecyclerViewMatcher().atPositionOnView(1, R.id.iv_like_article, R.id.rv_articles_review))
            .check(matches(isDisplayed()))
    }
}