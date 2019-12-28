package com.dvidal.samplearticles.features.start.domain.usecases

import com.dvidal.samplearticles.core.common.Either
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import io.mockk.*
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
    fun `when run use case should call repository clear all articles`() = runBlocking {

        coEvery { repository.clearAllArticles() } returns Either.right(Unit)

        useCase.run(UseCase.None())
        coVerify(exactly = 1) {repository.clearAllArticles()}
    }
}