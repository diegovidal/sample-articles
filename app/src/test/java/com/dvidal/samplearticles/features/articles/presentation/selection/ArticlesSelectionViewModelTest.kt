package com.dvidal.samplearticles.features.articles.presentation.selection

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.features.articles.domain.usecases.FetchUnreviewedArticlesUseCase
import com.dvidal.samplearticles.features.articles.domain.usecases.ReviewArticleUseCase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import com.dvidal.samplearticles.features.utils.MainCoroutineRule
import com.dvidal.samplearticles.features.utils.getOrAwaitValue
import com.dvidal.samplearticles.features.utils.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any

/**
 * @author diegovidal on 2019-12-29.
 */
@ExperimentalCoroutinesApi
class ArticlesSelectionViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val fetchUnreviewedArticlesUseCase = mockk<FetchUnreviewedArticlesUseCase>(relaxUnitFun = true)
    private val reviewArticleUseCase = mockk<ReviewArticleUseCase>(relaxUnitFun = true)

    private lateinit var viewModel: ArticlesSelectionViewModel

    @Before
    fun setup() = coroutineRule.runBlocking {

        viewModel = ArticlesSelectionViewModel(coroutineRule.testDispatcher, fetchUnreviewedArticlesUseCase, reviewArticleUseCase)
        viewModel.articlesSelectionViewEvents.observeForever {  }
    }

    @Test
    fun `when initArticlesSelectionScreen should invoke fetchUnreviewedArticlesUseCase`() = coroutineRule.runBlocking {

        coEvery { fetchUnreviewedArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.invokeAction(ArticlesSelectionViewContract.Action.InitPage(ArticlesInfoParam()))
        coVerify(exactly = 1) { fetchUnreviewedArticlesUseCase.invoke(any()) }
    }

    @Test
    fun `when reviewArticleUseCase and there are unreviewedArticles should invoke reviewArticleUseCase`() = coroutineRule.runBlocking {

        viewModel.fetchUnreviewedArticles.value = listOf(ArticleView())
        coEvery { reviewArticleUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.invokeAction(ArticlesSelectionViewContract.Action.ReviewArticle.LikeArticle())
        coVerify(exactly = 1) { reviewArticleUseCase.invoke(any()) }
    }

    @Test
    fun `when reviewArticleUseCase and unreviewedArticles is empty should not invoke reviewArticleUseCase`() = coroutineRule.runBlocking {

        coEvery { reviewArticleUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.invokeAction(ArticlesSelectionViewContract.Action.ReviewArticle.LikeArticle())
        coVerify(exactly = 0) { reviewArticleUseCase.invoke(any()) }
    }

    @Test
    fun `when fetchUnreviewedArticles is empty should return ArticlesSelectionEmpty`() = coroutineRule.runBlocking {

        val response = listOf<ArticleView>()
        viewModel.fetchUnreviewedArticles.value = response

        val expected = ArticlesSelectionViewContract.State.ArticlesSelectionEmpty(null)
        assertEquals(expected, viewModel.articlesSelectionViewStates.getOrAwaitValue())
    }

    @Test
    fun `when fetchUnreviewedArticles is one article should return ShowLastArticleOnQueue`() = coroutineRule.runBlocking {

        val response = listOf(ArticleView())
        viewModel.fetchUnreviewedArticles.value = response

        val expected = ArticlesSelectionViewContract.State.ShowLastArticleOnQueue(null, response.first())
        assertEquals(expected, viewModel.articlesSelectionViewStates.getOrAwaitValue())
    }

    @Test
    fun `when fetchUnreviewedArticles is more than one article should return ShowTwoArticlesOnQueue`() = coroutineRule.runBlocking {

        val response = listOf(ArticleView(), ArticleView())
        viewModel.fetchUnreviewedArticles.value = response

        val expected = ArticlesSelectionViewContract.State.ShowTwoArticlesOnQueue(null, response.first(), response[1])
        assertEquals(expected, viewModel.articlesSelectionViewStates.getOrAwaitValue())
    }

}