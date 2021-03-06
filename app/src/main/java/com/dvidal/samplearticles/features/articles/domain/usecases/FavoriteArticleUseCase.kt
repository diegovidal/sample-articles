package com.dvidal.samplearticles.features.articles.domain.usecases

import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-23.
 */
@Reusable
class FavoriteArticleUseCase @Inject constructor(
    private val repository: ArticlesRepository
) : UseCase<Unit, String>() {

    override suspend fun run(params: String) = repository.favoriteArticle(params)
}
