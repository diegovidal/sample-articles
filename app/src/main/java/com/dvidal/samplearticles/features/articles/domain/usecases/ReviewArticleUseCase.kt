package com.dvidal.samplearticles.features.articles.domain.usecases

import com.dvidal.samplearticles.core.common.EitherResult
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import com.dvidal.samplearticles.features.articles.presentation.selection.ArticlesSelectionViewContract
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-23.
 */
@Reusable
class ReviewArticleUseCase @Inject constructor(
    private val repository: ArticlesRepository,
    private val favoriteArticleUseCase: FavoriteArticleUseCase
) : UseCase<Unit, ArticlesSelectionViewContract.Action.ReviewArticle>() {

    override suspend fun run(params: ArticlesSelectionViewContract.Action.ReviewArticle): EitherResult<Unit> {

        if (params is ArticlesSelectionViewContract.Action.ReviewArticle.LikeArticle)
            favoriteArticleUseCase.run(params.sku)

        return repository.reviewArticle(params.sku)
    }
}
