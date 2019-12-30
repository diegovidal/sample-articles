package com.dvidal.samplearticles.features.articles.presentation.selection

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.dvidal.samplearticles.core.common.BaseViewModel
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.core.common.notLet
import com.dvidal.samplearticles.features.articles.data.local.ArticleDto
import com.dvidal.samplearticles.features.articles.domain.usecases.FetchUnreviewedArticlesUseCase
import com.dvidal.samplearticles.features.articles.domain.usecases.ReviewArticleUseCase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-25.
 */
class ArticlesSelectionViewModel @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val fetchUnreviewedArticlesUseCase: FetchUnreviewedArticlesUseCase,
    private val reviewArticleUseCase: ReviewArticleUseCase
) : BaseViewModel() {

    @VisibleForTesting val fetchUnreviewedArticles = MediatorLiveData<List<ArticleView>>()
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
                    it.size > 1 -> postValue(
                        ArticlesSelectionViewModelContract.ViewState.ShowTwoArticlesOnQueue(
                            articlesInfoParam, it[0], it[1]
                        )
                    )
                }
            }
        }

    fun initArticlesSelectionScreen(articlesInfoParam: ArticlesInfoParam) {

        this.articlesInfoParam = articlesInfoParam
        fetchUnreviewedArticles.notLet {

            viewModelScope.launch(coroutineDispatcher) {
                fetchUnreviewedArticlesUseCase.invoke(UseCase.None()).also {
                    it.either(::handleFailure, ::handleFetchUnreviewedArticlesSuccess)
                }
            }
        }
    }

    fun reviewArticleUseCase(userInteraction: ArticlesSelectionViewModelContract.UserInteraction) {

        fetchUnreviewedArticles.value?.firstOrNull()?.sku?.let { firstArticle ->
            userInteraction.sku = firstArticle

            viewModelScope.launch(coroutineDispatcher) {
                reviewArticleUseCase.invoke(userInteraction).also {
                    it.either(::handleFailure) {handleReviewArticleSuccess(userInteraction)}
                }
            }
        }
    }

    private fun handleReviewArticleSuccess(userInteraction: ArticlesSelectionViewModelContract.UserInteraction) {
        if (userInteraction is ArticlesSelectionViewModelContract.UserInteraction.LikeArticle)
            articlesInfoParam?.incrementFavorite()
    }

    private fun handleFetchUnreviewedArticlesSuccess(list: LiveData<List<ArticleDto>>) {

        viewModelScope.launch(Dispatchers.Main) {
            fetchUnreviewedArticles.apply {
                addSource(list) {
                    val listConverted = it.map { articleDto -> articleDto.mapperToArticleView() }
                    postValue(listConverted)
                }
            }
        }
    }
}