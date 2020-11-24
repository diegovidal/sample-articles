package com.dvidal.samplearticles.features.start.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import com.dvidal.samplearticles.features.start.domain.usecases.ClearArticlesUseCase
import com.dvidal.samplearticles.features.start.domain.usecases.StartArticlesUseCase
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

/**
 * @author diegovidal on 2019-12-29.
 */

@ExperimentalCoroutinesApi
class StartViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val clearArticlesUseCase = mockk<ClearArticlesUseCase>()
    private val startArticlesUseCase = mockk<StartArticlesUseCase>()

    private lateinit var viewModel: StartViewModel

    @Before
    fun setup() = coroutineRule.runBlocking {

        viewModel = StartViewModel(coroutineRule.testDispatcher, clearArticlesUseCase, startArticlesUseCase)
        viewModel.startViewEvents.observeForever { }
    }

    @Test
    fun `when invoke action StartArticles should invoke startArticlesUseCase`() = coroutineRule.runBlocking {

        coEvery { startArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.invokeAction(StartViewContract.Action.StartArticles)
        coVerify(exactly = 1) { startArticlesUseCase.invoke(any()) }
    }

    @Test
    fun `when invoke action ClearArticles should invoke clearArticlesUseCase`() = coroutineRule.runBlocking {

        coEvery { clearArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.invokeAction(StartViewContract.Action.ClearArticles)
        coVerify(exactly = 1) { clearArticlesUseCase.invoke(any()) }
    }

    @Test
    fun `when start articles and is error should return DisplayWarning`() = coroutineRule.runBlocking {

        val throwable = Throwable()
        coEvery { startArticlesUseCase.invoke(any()) } returns EitherResult.left(throwable)

        viewModel.invokeAction(StartViewContract.Action.StartArticles)
        val expected = StartViewContract.Event.DisplayWarning(throwable)
        assertEquals(expected, viewModel.startViewEvents.getOrAwaitValue())
    }

    @Test
    fun `when start articles and is success should return a StartArticlesSuccess with correspondent articlesInfoParam`() = coroutineRule.runBlocking {

        val list = listOf<ArticleView>()
        val articlesInfoParam = ArticlesInfoParam.calculateArticlesInfoParam(list)
        coEvery { startArticlesUseCase.invoke(any()) } returns EitherResult.right(list)

        viewModel.invokeAction(StartViewContract.Action.StartArticles)
        val expected = StartViewContract.Event.StartArticlesSuccess(articlesInfoParam)
        assertEquals(expected, viewModel.startViewEvents.getOrAwaitValue())
    }

    @Test
    fun `when clear articles and is error should return a DisplayWarning`() = coroutineRule.runBlocking {

        val throwable = Throwable()
        coEvery { clearArticlesUseCase.invoke(any()) } returns EitherResult.left(throwable)

        viewModel.invokeAction(StartViewContract.Action.ClearArticles)
        val expected = StartViewContract.Event.DisplayWarning(throwable)
        assertEquals(expected, viewModel.startViewEvents.getOrAwaitValue())
    }

    @Test
    fun `when clear articles and success should return a ClearArticlesSuccess`() = coroutineRule.runBlocking {

        coEvery { clearArticlesUseCase.invoke(any()) } returns EitherResult.right(Unit)

        viewModel.invokeAction(StartViewContract.Action.ClearArticles)
        val expected = StartViewContract.Event.ClearArticlesSuccess
        assertEquals(expected, viewModel.startViewEvents.getOrAwaitValue())
    }

    @Test
    fun `when start articles should post value StartViewState with isStartArticlesLoading`() = coroutineRule.runBlocking {

        viewModel.invokeAction(StartViewContract.Action.StartArticles)
        val expected = StartViewContract.State.StartViewState(isStartArticlesLoading = true, isClearArticlesLoading = false)
        assertEquals(expected, viewModel.startViewStates.getOrAwaitValue())
    }

    @Test
    fun `when clear articles should post value StartViewState with isClearArticlesLoading`() = coroutineRule.runBlocking {

        viewModel.invokeAction(StartViewContract.Action.ClearArticles)
        viewModel.startViewStates.observeForever {
            assertEquals(it, StartViewContract.State.StartViewState(isStartArticlesLoading = false, isClearArticlesLoading = true))
        }
    }
}
