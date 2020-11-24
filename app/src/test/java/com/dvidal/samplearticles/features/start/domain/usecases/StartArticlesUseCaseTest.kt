package com.dvidal.samplearticles.features.start.domain.usecases

import com.dvidal.samplearticles.core.common.Either
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 2019-12-24.
 */
class StartArticlesUseCaseTest {

    private val repository = mockk<ArticlesRepository>()

    private lateinit var useCase: StartArticlesUseCase

    @Before
    fun setup() {

        useCase = StartArticlesUseCase(repository)
    }

    @Test
    fun `when run use case should call repository fetch all articles`() = runBlocking {

        val list = listOf(ArticleView("foo"))
        coEvery { repository.fetchAllArticles() } returns Either.right(list)

        useCase.run(UseCase.None())
        coVerify(exactly = 1) { repository.fetchAllArticles() }
    }
}
