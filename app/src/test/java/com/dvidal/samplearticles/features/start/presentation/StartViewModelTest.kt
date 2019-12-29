package com.dvidal.samplearticles.features.start.presentation

import com.dvidal.samplearticles.features.start.domain.usecases.ClearArticlesUseCase
import com.dvidal.samplearticles.features.start.domain.usecases.StartArticlesUseCase
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.*
import org.junit.Before

/**
 * @author diegovidal on 2019-12-29.
 */
class StartViewModelTest {

    private val dispartcher = Dispatchers.Default
    private val clearArticlesUseCase = mockk<ClearArticlesUseCase>()
    private val startArticlesUseCase = mockk<StartArticlesUseCase>()

    private lateinit var viewModel: StartViewModel

    @Before

}