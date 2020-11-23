package com.dvidal.samplearticles.features.articles.presentation.selection

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dvidal.samplearticles.core.common.BaseViewModel
import com.dvidal.samplearticles.core.common.SingleLiveEvent
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.core.common.notLet
import com.dvidal.samplearticles.features.articles.data.local.ArticleDto
import com.dvidal.samplearticles.features.articles.domain.usecases.FetchUnreviewedArticlesUseCase
import com.dvidal.samplearticles.features.articles.domain.usecases.ReviewArticleUseCase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-25.
 */
class ArticlesSelectionViewModel @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val fetchUnreviewedArticlesUseCase: FetchUnreviewedArticlesUseCase,
    private val reviewArticleUseCase: ReviewArticleUseCase
) : BaseViewModel(), ArticlesSelectionViewContract.ViewModelEvents {

    private val _action = SingleLiveEvent<ArticlesSelectionViewContract.Action>()

    @VisibleForTesting
    val fetchUnreviewedArticles = MediatorLiveData<List<ArticleView>>()
    private var articlesInfoParam: ArticlesInfoParam? = null

    private val _articlesSelectionViewStates = MediatorLiveData<ArticlesSelectionViewContract.State>().apply {

        addSource(fetchUnreviewedArticles) {
            when {
                it.isEmpty() -> postValue(
                    ArticlesSelectionViewContract.State.ArticlesSelectionEmpty(
                        articlesInfoParam
                    )
                )
                it.size == 1 -> postValue(
                    ArticlesSelectionViewContract.State.ShowLastArticleOnQueue(
                        articlesInfoParam, it.first()
                    )
                )
                it.size > 1 -> postValue(
                    ArticlesSelectionViewContract.State.ShowTwoArticlesOnQueue(
                        articlesInfoParam, it[0], it[1]
                    )
                )
            }
        }
    }
    override val articlesSelectionViewStates: LiveData<ArticlesSelectionViewContract.State> = _articlesSelectionViewStates

    private val _articlesSelectionViewEvents = SingleLiveEvent<ArticlesSelectionViewContract.Event>().apply {
        addSource(_action) {
            handleAction(it)
        }
    }
    override val articlesSelectionViewEvents: LiveData<ArticlesSelectionViewContract.Event> = _articlesSelectionViewEvents

    override fun invokeAction(action: ArticlesSelectionViewContract.Action) {
        _action.postValue(action)
    }

    private fun handleAction(action: ArticlesSelectionViewContract.Action) {

        when (action) {
            is ArticlesSelectionViewContract.Action.InitPage -> initArticlesSelectionScreen(action.articlesInfoParam)
            is ArticlesSelectionViewContract.Action.ReviewArticle.LikeArticle -> reviewArticleUseCase(action)
            is ArticlesSelectionViewContract.Action.ReviewArticle.DislikeArticle -> reviewArticleUseCase(action)
            is ArticlesSelectionViewContract.Action.NavigateToArticleReviews -> _articlesSelectionViewEvents.postValue(ArticlesSelectionViewContract.Event.GoToArticleReviews)
        }
    }

    private fun initArticlesSelectionScreen(articlesInfoParam: ArticlesInfoParam) {

        this.articlesInfoParam = articlesInfoParam
        fetchUnreviewedArticles.notLet {

            viewModelScope.launch(coroutineDispatcher) {
                fetchUnreviewedArticlesUseCase.invoke(UseCase.None()).also {
                    it.either(::handleFailure, ::handleFetchUnreviewedArticlesSuccess)
                }
            }
        }
    }

    private fun reviewArticleUseCase(userInteraction: ArticlesSelectionViewContract.Action.ReviewArticle) {

        fetchUnreviewedArticles.value?.firstOrNull()?.sku?.let { firstArticle ->
            userInteraction.sku = firstArticle

            viewModelScope.launch(coroutineDispatcher) {
                reviewArticleUseCase.invoke(userInteraction).also {
                    it.either(::handleFailure) { handleReviewArticleSuccess(userInteraction) }
                }
            }
        }
    }

    private fun handleReviewArticleSuccess(userInteraction: ArticlesSelectionViewContract.Action) {
        if (userInteraction is ArticlesSelectionViewContract.Action.ReviewArticle.LikeArticle)
            articlesInfoParam?.incrementFavorite()
    }

    private fun handleFetchUnreviewedArticlesSuccess(list: Flow<List<ArticleView>>) {

        viewModelScope.launch(coroutineDispatcher) {
            fetchUnreviewedArticles.apply {

                withContext(Dispatchers.Main) {
                    addSource(list.asLiveData()) { listConverted ->
                        postValue(listConverted)
                    }
                }
            }
        }
    }
}