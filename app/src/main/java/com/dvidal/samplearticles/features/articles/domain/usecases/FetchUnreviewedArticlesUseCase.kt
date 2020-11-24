package com.dvidal.samplearticles.features.articles.domain.usecases

import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * @author diegovidal on 2019-12-23.
 */
@Reusable
class FetchUnreviewedArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository
) : UseCase<Flow<List<ArticleView>>, UseCase.None>() {

    override suspend fun run(params: None) = repository.fetchUnreviewedArticles()
}
