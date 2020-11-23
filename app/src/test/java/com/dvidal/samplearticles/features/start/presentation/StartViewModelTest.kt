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
    fun setup() {

        viewModel = StartViewModel(coroutineRule.testDispatcher, clearArticlesUseCase, startArticlesUseCase)
    }

    @Test
    fun `when start articles should invoke startArticlesUseCase`() = coroutineRule.runBlocking {

        coEvery { startArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.invokeAction(StartViewContract.Action.StartArticles)
        coVerify(exactly = 1) { startArticlesUseCase.invoke(any()) }
    }

    @Test
    fun `when clear articles should invoke clearArticlesUseCase`() = coroutineRule.runBlocking {

        coEvery { clearArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.invokeAction(StartViewContract.Action.ClearArticles)
        coVerify(exactly = 1) { clearArticlesUseCase.invoke(any()) }
    }

    @Test
    fun `when start articles and error should return a StartArticlesError`() = coroutineRule.runBlocking {

        val throwable = Throwable()
        coEvery { startArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable(throwable))

        viewModel.invokeAction(StartViewContract.Action.StartArticles)

        assert(viewModel.startViewEvents.getOrAwaitValue(3) is StartViewContract.Event.DisplayWarning)
    }

    @Test
    fun `when start articles and success should return a StartArticlesSuccess with correspondent articlesInfoParam`() = coroutineRule.runBlocking {

        val list = listOf<ArticleView>()
        val articlesInfoParam = ArticlesInfoParam.calculateArticlesInfoParam(list)
        coEvery { startArticlesUseCase.invoke(any()) } returns EitherResult.right(list)

        viewModel.invokeAction(StartViewContract.Action.StartArticles)
        assert(viewModel.startViewEvents.getOrAwaitValue(3) == StartViewContract.Event.StartArticlesSuccess(articlesInfoParam))
    }

    @Test
    fun `when clear articles and error should return a ClearArticlesError`() = coroutineRule.runBlocking {

        val throwable = Throwable()
        coEvery { clearArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable(throwable))

        viewModel.invokeAction(StartViewContract.Action.ClearArticles)
        assert(viewModel.startViewEvents.getOrAwaitValue(3) == StartViewContract.Event.DisplayWarning(throwable))
    }

    @Test
    fun `when clear articles and success should return a ClearArticlesSuccess`() = coroutineRule.runBlocking {

        coEvery { clearArticlesUseCase.invoke(any()) } returns EitherResult.right(Unit)

        viewModel.invokeAction(StartViewContract.Action.ClearArticles)
        assert(viewModel.startViewEvents.getOrAwaitValue(3) == StartViewContract.Event.ClearArticlesSuccess)
    }

    @Test
    fun `when start articles should post value StartArticlesLoading on liveEvents`() = coroutineRule.runBlocking {

        viewModel.invokeAction(StartViewContract.Action.StartArticles)
        viewModel.startViewStates.observeForever {
            assertEquals(it, StartViewContract.State.StartViewState(isStartArticlesLoading = true, isClearArticlesLoading = false))
        }
    }

    @Test
    fun `when start articles should post value StartArticlesLoading on singleLiveEvents`() = coroutineRule.runBlocking {

        viewModel.invokeAction(StartViewContract.Action.StartArticles)
        viewModel.startViewStates.observeForever {
            assertEquals(it, StartViewContract.State.StartViewState(isStartArticlesLoading = true, isClearArticlesLoading = false))
        }
    }

    @Test
    fun `when clear articles should post value ClearArticlesLoading on singleLiveEvents`() = coroutineRule.runBlocking {

        viewModel.invokeAction(StartViewContract.Action.ClearArticles)
        viewModel.startViewStates.observeForever {
            assertEquals(it, StartViewContract.State.StartViewState(isStartArticlesLoading = false, isClearArticlesLoading = true))
        }
    }
}