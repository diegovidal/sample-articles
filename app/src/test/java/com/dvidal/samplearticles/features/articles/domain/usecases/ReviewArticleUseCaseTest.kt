package com.dvidal.samplearticles.features.articles.domain.usecases

import com.dvidal.samplearticles.core.common.Either
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.articles.presentation.selection.ArticlesSelectionViewContract
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * @author diegovidal on 2019-12-24.
 */
class ReviewArticleUseCaseTest {

    private val repository = mockk<ArticlesRepository>()
    private val favoriteArticleUseCase = mockk<FavoriteArticleUseCase>()

    private lateinit var useCase: ReviewArticleUseCase

    @Before
    fun setup() {

        useCase = ReviewArticleUseCase(repository, favoriteArticleUseCase)
    }

    @Test
    fun `when run use case should call repository fetch all articles`() = runBlocking {

        val foo = "foo"
        val userInteraction = ArticlesSelectionViewContract.Action.ReviewArticle.LikeArticle(foo)

        coEvery { repository.reviewArticle(foo) } returns Either.right(Unit)
        coEvery { favoriteArticleUseCase.run(foo) } returns Either.right(Unit)

        useCase.run(userInteraction)
        coVerify(exactly = 1) { repository.reviewArticle(foo) }
    }
}