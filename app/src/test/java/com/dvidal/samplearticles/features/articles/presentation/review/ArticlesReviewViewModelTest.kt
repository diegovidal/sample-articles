package com.dvidal.samplearticles.features.articles.presentation.review

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.features.articles.domain.usecases.FetchReviewedArticlesUseCase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.utils.MainCoroutineRule
import com.dvidal.samplearticles.features.utils.getOrAwaitValue
import com.dvidal.samplearticles.features.utils.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author diegovidal on 2019-12-30.
 */

@ExperimentalCoroutinesApi
class ArticlesReviewViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val dispatcher = Dispatchers.Default
    private val fetchReviewedArticlesUseCase = mockk<FetchReviewedArticlesUseCase>()

    private lateinit var viewModel: ArticlesReviewViewModel

    @Before
    fun setup() {

        viewModel = ArticlesReviewViewModel(dispatcher, fetchReviewedArticlesUseCase)
        viewModel.articlesReviewViewEvents.observeForever {  }
    }

    @Test
    fun `when fetch reviewed articles should invoke fetchReviewedArticlesUseCase`() = coroutineRule.runBlocking {

        coEvery { fetchReviewedArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.invokeAction(ArticlesReviewViewContract.Action.InitPage)
        coVerify(exactly = 1) { fetchReviewedArticlesUseCase.invoke(any()) }
    }

    @Test
    fun `when fetch reviewed articles and success should return a ShowArticlesReview`() = coroutineRule.runBlocking {

        val response = listOf(ArticleView(), ArticleView())
        val articlesReviewView = ArticlesReviewView(list = response)
        coEvery { fetchReviewedArticlesUseCase.invoke(any()) } returns EitherResult.right(response)

        viewModel.invokeAction(ArticlesReviewViewContract.Action.InitPage)
        val expected = ArticlesReviewViewContract.State.ShowArticlesReview(articlesReviewView)
        assertEquals(expected, viewModel.articlesReviewViewStates.getOrAwaitValue())
    }
}