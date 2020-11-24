package com.dvidal.samplearticles.features.articles.presentation.selection

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dvidal.samplearticles.BaseEspressoTest
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import com.dvidal.samplearticles.features.start.presentation.StartActivity
import com.dvidal.samplearticles.utils.FragmentTestRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticlesSelectionFragmentTest: BaseEspressoTest() {

    @get:Rule
    val fragmentRule = FragmentTestRule(ArticlesSelectionFragment::class.java)

    private val articlesInfoParam = ArticlesInfoParam()
    private val states = MutableLiveData<ArticlesSelectionViewContract.State>()
    private val events = MutableLiveData<ArticlesSelectionViewContract.Event>()

    private val mockViewModel = mockk<ArticlesSelectionViewModel>(relaxUnitFun = true)

    @Before
    fun setup() {

        val viewModelFactory = application.appComponent.viewModelFactor
        every { viewModelFactory.create(ArticlesSelectionViewModel::class.java) } returns mockViewModel

        every { mockViewModel.articlesSelectionViewStates } returns states
        every { mockViewModel.articlesSelectionViewEvents } returns events
    }

    private fun launchActivity() {
        fragmentRule.launchActivity(Intent().apply {
            putExtra(StartActivity.EXTRA_ARTICLES_INFO_PARAM, articlesInfoParam)
        })
    }

    @Test
    fun whenLaunchFragment_shouldShowProgressBar() {
        launchActivity()

        onView(withId(R.id.pb_content_view)).check(matches(isDisplayed()))
    }

    @Test
    fun whenShowLastArticleOnQueue_shouldShowImageAndButtons() {
        launchActivity()

        runBlocking(Dispatchers.Main) {
            states.postValue(ArticlesSelectionViewContract.State.ShowLastArticleOnQueue(articlesInfoParam, ArticleView()))
        }

        onView(withId(R.id.iv_first_article)).check(matches(isDisplayed()))
        onView(withId(R.id.bt_like_article)).check(matches(isDisplayed()))
        onView(withId(R.id.bt_dislike_article)).check(matches(isDisplayed()))
    }

    @Test
    fun whenShowTwoArticlesOnQueue_shouldShowImageAndButtons() {
        launchActivity()

        runBlocking(Dispatchers.Main) {
            states.postValue(ArticlesSelectionViewContract.State.ShowTwoArticlesOnQueue(articlesInfoParam, ArticleView(), ArticleView()))
        }

        onView(withId(R.id.iv_first_article)).check(matches(isDisplayed()))
        onView(withId(R.id.bt_like_article)).check(matches(isDisplayed()))
        onView(withId(R.id.bt_dislike_article)).check(matches(isDisplayed()))
    }

    @Test
    fun whenArticlesSelectionEmpty_shouldShowEmptyView() {
        launchActivity()

        runBlocking(Dispatchers.Main) {
            states.postValue(ArticlesSelectionViewContract.State.ArticlesSelectionEmpty(articlesInfoParam))
        }

        onView(withId(R.id.empty_view)).check(matches(isDisplayed()))
    }

    @Test
    fun whenShowContent_andClickLikeButton_shouldInvokeAction() {
        launchActivity()

        runBlocking(Dispatchers.Main) {
            states.postValue(ArticlesSelectionViewContract.State.ShowLastArticleOnQueue(articlesInfoParam, ArticleView()))
        }

        onView(withId(R.id.bt_like_article)).perform(click())
        verify(exactly = 1) { mockViewModel.invokeAction(ArticlesSelectionViewContract.Action.ReviewArticle.LikeArticle()) }
    }

    @Test
    fun whenShowContent_andClickDislikeButton_shouldInvokeAction() {
        launchActivity()

        runBlocking(Dispatchers.Main) {
            states.postValue(ArticlesSelectionViewContract.State.ShowLastArticleOnQueue(articlesInfoParam, ArticleView()))
        }

        onView(withId(R.id.bt_dislike_article)).perform(click())
        verify(exactly = 1) { mockViewModel.invokeAction(ArticlesSelectionViewContract.Action.ReviewArticle.DislikeArticle()) }
    }

    @Test
    fun whenArticlesSelectionEmpty_andClickButton_shouldInvokeAction() {
        launchActivity()

        runBlocking(Dispatchers.Main) {
            states.postValue(ArticlesSelectionViewContract.State.ArticlesSelectionEmpty(articlesInfoParam))
        }

        onView(withId(R.id.bt_see_reviews)).perform(click())
        verify(exactly = 1) { mockViewModel.invokeAction(ArticlesSelectionViewContract.Action.NavigateToArticleReviews) }
    }
}