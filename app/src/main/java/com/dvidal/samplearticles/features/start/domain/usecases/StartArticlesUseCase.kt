package com.dvidal.samplearticles.features.start.domain.usecases

import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-23.
 */
@Reusable
class StartArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository
) : UseCase<List<ArticleView>, UseCase.None>() {

    override suspend fun run(params: None) = repository.fetchAllArticles()
}
