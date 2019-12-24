package com.dvidal.samplearticles.features.articles.domain.usecases

import com.dvidal.samplearticles.core.common.Either
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 2019-12-24.
 */
class FetchReviewedArticlesUseCaseTest {

    private val repository = mockk<ArticlesRepository>()

    private lateinit var useCase: FetchReviewedArticlesUseCase

    @Before
    fun setup() {

        useCase = FetchReviewedArticlesUseCase(repository)
    }

    @Test
    fun `when run use case should call repository fetch all articles`() {

        val list = listOf(ArticleView("foo"))
        every { repository.fetchReviewedArticles() } returns Either.right(list)

        runBlocking { useCase.run(UseCase.None()) }
        verify(exactly = 1) { repository.fetchReviewedArticles() }
    }
}