package com.dvidal.samplearticles.features.articles.presentation.selection

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.features.articles.domain.usecases.FetchUnreviewedArticlesUseCase
import com.dvidal.samplearticles.features.articles.domain.usecases.ReviewArticleUseCase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import com.dvidal.samplearticles.features.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author diegovidal on 2019-12-29.
 */
class ArticlesSelectionViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val coroutineDispatcher = Dispatchers.Default
    private val fetchUnreviewedArticlesUseCase = mockk<FetchUnreviewedArticlesUseCase>(relaxUnitFun = true)
    private val reviewArticleUseCase = mockk<ReviewArticleUseCase>(relaxUnitFun = true)

    private lateinit var viewModel: ArticlesSelectionViewModel

    @Before
    fun setup() {

        viewModel = ArticlesSelectionViewModel(coroutineDispatcher, fetchUnreviewedArticlesUseCase, reviewArticleUseCase)
    }

    @Test
    fun `when initArticlesSelectionScreen should invoke fetchUnreviewedArticlesUseCase`() {

        coEvery { fetchUnreviewedArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.initArticlesSelectionScreen(ArticlesInfoParam())
        coVerify(exactly = 1) { fetchUnreviewedArticlesUseCase.invoke(any()) }
    }

    @Test
    fun `when reviewArticleUseCase and there are unreviewedArticles should invoke reviewArticleUseCase`() {

        viewModel.fetchUnreviewedArticles.value = listOf(ArticleView())
        coEvery { reviewArticleUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.reviewArticleUseCase(ArticlesSelectionViewModelContract.UserInteraction.LikeArticle())
        coVerify(exactly = 1) { reviewArticleUseCase.invoke(any()) }
    }

    @Test
    fun `when reviewArticleUseCase and unreviewedArticles is empty should not invoke reviewArticleUseCase`() {

        coEvery { reviewArticleUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.reviewArticleUseCase(ArticlesSelectionViewModelContract.UserInteraction.LikeArticle())
        coVerify(exactly = 0) { reviewArticleUseCase.invoke(any()) }
    }

    @Test
    fun `when fetchUnreviewedArticles is empty should return ArticlesSelectionEmpty`() {

        val response = listOf<ArticleView>()
        viewModel.fetchUnreviewedArticles.value = response

        assert(viewModel.viewStatesLiveEvents.getOrAwaitValue() is ArticlesSelectionViewModelContract.ViewState.ArticlesSelectionEmpty)
    }

    @Test
    fun `when fetchUnreviewedArticles is one should return ShowLastArticleOnQueue`() {

        val response = listOf(ArticleView())
        viewModel.fetchUnreviewedArticles.value = response

        assert(viewModel.viewStatesLiveEvents.getOrAwaitValue() is ArticlesSelectionViewModelContract.ViewState.ShowLastArticleOnQueue)
    }

    @Test
    fun `when fetchUnreviewedArticles is more than one should return ShowTwoArticlesOnQueue`() {

        val response = listOf(ArticleView(), ArticleView())
        viewModel.fetchUnreviewedArticles.value = response

        assert(viewModel.viewStatesLiveEvents.getOrAwaitValue() is ArticlesSelectionViewModelContract.ViewState.ShowTwoArticlesOnQueue)
    }

}