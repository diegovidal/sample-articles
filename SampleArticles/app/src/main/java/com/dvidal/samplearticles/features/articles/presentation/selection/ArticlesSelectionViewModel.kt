package com.dvidal.samplearticles.features.articles.presentation.selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dvidal.samplearticles.core.common.BaseViewModel
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.core.common.notLet
import com.dvidal.samplearticles.features.articles.data.local.ArticleDto
import com.dvidal.samplearticles.features.articles.domain.usecases.FetchUnreviewedArticlesUseCase
import com.dvidal.samplearticles.features.articles.domain.usecases.ReviewArticleUseCase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-25.
 */
class ArticlesSelectionViewModel @Inject constructor(
    private val fetchUnreviewedArticlesUseCase: FetchUnreviewedArticlesUseCase,
    private val reviewArticleUseCase: ReviewArticleUseCase
) : BaseViewModel() {

    val fetchUnreviewedArticles = MediatorLiveData<List<ArticleView>>()

    fun fetchUnreviewedArticles() {

        fetchUnreviewedArticles.notLet {
            fetchUnreviewedArticlesUseCase.invoke(UseCase.None(), Dispatchers.IO, job) {
                it.either(
                    ::handleFetchUnreviewedArticlesFailure,
                    ::handleFetchUnreviewedArticlesSuccess
                )
            }
        }
    }

    fun reviewArticleUseCase(sku: String) {

        reviewArticleUseCase.invoke(sku, Dispatchers.IO, job) {
            it.either(
                ::handleFailure,
                {}
            )
        }
    }

    private fun handleFetchUnreviewedArticlesFailure(throwable: Throwable) {

    }

    private fun handleFetchUnreviewedArticlesSuccess(list: LiveData<List<ArticleDto>>) {

        fetchUnreviewedArticles.apply {
            addSource(list) {
                val listConverted = it.map { articleDto ->  articleDto.mapperToArticleView() }
                postValue(listConverted)
            }
        }
    }
}