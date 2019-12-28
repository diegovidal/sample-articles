package com.dvidal.samplearticles.features.articles.domain.usecases

import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.articles.presentation.selection.ArticlesSelectionViewModelContract
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-23.
 */
@Reusable
class ReviewArticleUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    private val favoriteArticleUseCase: FavoriteArticleUseCase
): UseCase<Unit, ArticlesSelectionViewModelContract.UserInteraction>() {

    override suspend fun run(params: ArticlesSelectionViewModelContract.UserInteraction): EitherResult<Unit> {

        if (params is ArticlesSelectionViewModelContract.UserInteraction.LikeArticle)
            favoriteArticleUseCase.run(params.sku)

        return repository.reviewArticle(params.sku)
    }
}