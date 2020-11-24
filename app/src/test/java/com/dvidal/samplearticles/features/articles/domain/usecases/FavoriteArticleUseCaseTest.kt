package com.dvidal.samplearticles.features.articles.domain.usecases

import com.dvidal.samplearticles.core.common.Either
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
    fun `when run use case should call repository fetch all articles`() = runBlocking {

        val foo = "foo"

        coEvery { repository.favoriteArticle(foo) } returns Either.right(Unit)

        useCase.run(foo)
        coVerify(exactly = 1) { repository.favoriteArticle(foo) }
    }
}
