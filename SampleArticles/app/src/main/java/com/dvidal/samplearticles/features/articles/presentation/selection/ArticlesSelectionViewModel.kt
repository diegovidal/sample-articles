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
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-25.
 */
class ArticlesSelectionViewModel @Inject constructor(
    private val fetchUnreviewedArticlesUseCase: FetchUnreviewedArticlesUseCase,
    private val reviewArticleUseCase: ReviewArticleUseCase
) : BaseViewModel() {

    private val fetchUnreviewedArticles = MediatorLiveData<List<ArticleView>>()
    private var articlesInfoParam: ArticlesInfoParam? = null

    val viewStatesLiveEvents =
        MediatorLiveData<ArticlesSelectionViewModelContract.ViewState>().apply {

            addSource(fetchUnreviewedArticles) {
                when {
                    it.isEmpty() -> postValue(
                        ArticlesSelectionViewModelContract.ViewState.ArticlesSelectionEmpty(
                            articlesInfoParam
                        )
                    )
                    it.size == 1 -> postValue(
                        ArticlesSelectionViewModelContract.ViewState.ShowLastArticleOnQueue(
                            articlesInfoParam, it.first()
                        )
                    )
                    else -> postValue(
                        ArticlesSelectionViewModelContract.ViewState.ShowTwoArticlesOnQueue(
                            articlesInfoParam, it[2], it[1]
                        )
                    )
                }
            }
        }

    fun initArticlesSelectionScreen(articlesInfoParam: ArticlesInfoParam) {

        this.articlesInfoParam = articlesInfoParam
        fetchUnreviewedArticles.notLet {
            fetchUnreviewedArticlesUseCase.invoke(UseCase.None(), Dispatchers.IO, job) {
                it.either(
                    ::handleFetchUnreviewedArticlesFailure,
                    ::handleFetchUnreviewedArticlesSuccess
                )
            }
        }
    }

    fun reviewArticleUseCase(userInteraction: ArticlesSelectionViewModelContract.UserInteraction) {

        val firstArticle = fetchUnreviewedArticles.value?.first()?.sku ?: ""
        userInteraction.sku = firstArticle

        reviewArticleUseCase.invoke(userInteraction, Dispatchers.IO, job) {
            it.either(
                ::handleFailure
            ) { if (userInteraction is ArticlesSelectionViewModelContract.UserInteraction.LikeArticle) articlesInfoParam?.incrementFavorite() }
        }
    }

    private fun handleFetchUnreviewedArticlesFailure(throwable: Throwable) {

    }

    private fun handleFetchUnreviewedArticlesSuccess(list: LiveData<List<ArticleDto>>) {

        fetchUnreviewedArticles.apply {
            addSource(list) {
                val listConverted = it.map { articleDto -> articleDto.mapperToArticleView() }
                postValue(listConverted)
            }
        }
    }
}