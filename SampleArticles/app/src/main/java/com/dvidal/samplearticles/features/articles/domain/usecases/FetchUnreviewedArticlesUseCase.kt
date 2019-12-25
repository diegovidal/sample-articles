package com.dvidal.samplearticles.features.articles.domain.usecases

import androidx.lifecycle.LiveData
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.data.local.ArticleDto
import com.dvidal.samplearticles.features.articles.domain.ArticlesRepository
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import dagger.Reusable
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-23.
 */
@Reusable
class FetchUnreviewedArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository
): UseCase<LiveData<List<ArticleDto>>, UseCase.None>() {

    override suspend fun run(params: None) = repository.fetchUnreviewedArticles()
}