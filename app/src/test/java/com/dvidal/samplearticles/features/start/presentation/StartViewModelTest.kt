package com.dvidal.samplearticles.features.start.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.features.start.domain.usecases.ClearArticlesUseCase
import com.dvidal.samplearticles.features.start.domain.usecases.StartArticlesUseCase
import com.dvidal.samplearticles.features.utils.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author diegovidal on 2019-12-29.
 */
class StartViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dispatcher = Dispatchers.Default
    private val clearArticlesUseCase = mockk<ClearArticlesUseCase>()
    private val startArticlesUseCase = mockk<StartArticlesUseCase>()

    private lateinit var viewModel: StartViewModel

    @Before
    fun setup() {

        viewModel = StartViewModel(dispatcher, clearArticlesUseCase, startArticlesUseCase)
    }

    @Test
    fun `when start articles should invoke startArticlesUseCase`() {

        coEvery { startArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.startArticles()
        coVerify(exactly = 1) { startArticlesUseCase.invoke(any()) }
    }

    @Test
    fun `when clear articles should invoke clearArticlesUseCase`() {

        coEvery { clearArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable())

        viewModel.clearArticles()
        coVerify(exactly = 1) { clearArticlesUseCase.invoke(any()) }
    }

    @Test
    fun `when start articles and error should return a StartArticlesError`() {

        val throwable = Throwable()
        coEvery { startArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable(throwable))

        viewModel.startArticles()

        assert(viewModel.viewStatesSingleLiveEvents.getOrAwaitValue(3) is StartViewModelContract.ViewState.Warning.StartArticlesError)
    }

    @Test
    fun `when clear articles and error should return a ClearArticlesError`() {

        val throwable = Throwable()
        coEvery { clearArticlesUseCase.invoke(any()) } returns EitherResult.left(Throwable(throwable))

        viewModel.clearArticles()

        assert(viewModel.viewStatesSingleLiveEvents.getOrAwaitValue(3) is StartViewModelContract.ViewState.Warning.ClearArticlesError)
    }

    @Test
    fun `when start articles should post value StartArticlesLoading on liveEvents`() {

        viewModel.startArticles()
        viewModel.viewStatesLiveEvents.observeForever {
            assertEquals(it, StartViewModelContract.ViewState.Loading.StartArticlesLoading)
        }
    }

    @Test
    fun `when start articles should post value StartArticlesLoading on singleLiveEvents`() {

        viewModel.startArticles()
        viewModel.viewStatesSingleLiveEvents.observeForever {
            assertEquals(it, StartViewModelContract.ViewState.Loading.StartArticlesLoading)
        }
    }

    @Test
    fun `when clear articles should post value ClearArticlesLoading on singleLiveEvents`() {

        viewModel.clearArticles()
        viewModel.viewStatesSingleLiveEvents.observeForever {
            assertEquals(it, StartViewModelContract.ViewState.Loading.ClearArticlesLoading)
        }
    }
}