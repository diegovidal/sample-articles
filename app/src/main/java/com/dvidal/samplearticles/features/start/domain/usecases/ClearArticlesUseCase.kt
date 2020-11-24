package com.dvidal.samplearticles.features.start.domain.usecases

import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-23.
 */
@Reusable
class ClearArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository
) : UseCase<Unit, UseCase.None>() {

    override suspend fun run(params: None) = repository.clearAllArticles()
}
