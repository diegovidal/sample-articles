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
class FavoriteArticleUseCaseTest {

    private val repository = mockk<ArticlesRepository>()

    private lateinit var useCase: FavoriteArticleUseCase

    @Before
    fun setup() {

        useCase = FavoriteArticleUseCase(repository)
    }

    @Test
    fun `when run use case should call repository fetch all articles`() {

        val foo = "foo"

        every { repository.favoriteArticle(foo) } returns Either.right(Unit)

        runBlocking { useCase.run(foo) }
        verify(exactly = 1) { repository.favoriteArticle(foo) }
    }
}