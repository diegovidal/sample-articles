package com.dvidal.samplearticles.features.start.domain.usecases

import com.dvidal.samplearticles.core.common.Either
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 2019-12-24.
 */
class ClearArticlesUseCaseTest {

    private val repository = mockk<ArticlesRepository>()

    private lateinit var useCase: ClearArticlesUseCase

    @Before
    fun setup() {

        useCase = ClearArticlesUseCase(repository)
    }

    @Test
    fun `when run use case should call repository clear all articles`() {

        every { repository.clearAllArticles() } returns Either.right(Unit)

        runBlocking { useCase.run(UseCase.None()) }
        verify(exactly = 1) {repository.clearAllArticles()}
    }
}