package com.dvidal.samplearticles.features.articles.presentation.review

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.features.articles.domain.usecases.FetchReviewedArticlesUseCase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author diegovidal on 2019-12-30.
 */
class ArticlesReviewViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = Dispatchers.Default
    private val fetchReviewedArticlesUseCase = mockk<FetchReviewedArticlesUseCase>()

    private lateinit var viewModel: ArticlesReviewViewModel

    @Before
    fun setup() {

        viewModel = ArticlesReviewViewModel(dispatcher, fetchReviewedArticlesUseCase)
    }

    @Test
    fun `when fetch reviewed articles should invoke fetchReviewedArticlesUseCase`() {

        coEvery { fetchReviewedArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.fetchReviewedArticles()
        coVerify(exactly = 1) { fetchReviewedArticlesUseCase.invoke(any()) }
    }

    @Test
    fun `when fetch reviewed articles and success should return a ShowArticlesReview`() = runBlocking {

        val response = listOf<ArticleView>()
        coEvery { fetchReviewedArticlesUseCase.invoke(any()) } returns EitherResult.right(response)

        viewModel.fetchReviewedArticles()
        assert(viewModel.fetchReviewedArticles.getOrAwaitValue(3) == ArticlesReviewViewModelContract.ViewState.ShowArticlesReview(response))
    }
}