package com.dvidal.samplearticles.features.articles.domain.usecases

import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.data.local.ArticleDto
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import dagger.Reusable
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-23.
 */
@Reusable
class FetchUnreviewedArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository
): UseCase<Flow<List<ArticleDto>>, UseCase.None>() {

    override suspend fun run(params: None) = repository.fetchUnreviewedArticles()
}