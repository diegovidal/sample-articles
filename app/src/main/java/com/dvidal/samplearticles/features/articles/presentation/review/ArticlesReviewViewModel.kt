package com.dvidal.samplearticles.features.articles.presentation.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvidal.samplearticles.core.common.BaseViewModel
import com.dvidal.samplearticles.core.common.SingleLiveEvent
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.core.common.notLet
import com.dvidal.samplearticles.features.articles.domain.usecases.FetchReviewedArticlesUseCase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

/**
 * @author diegovidal on 2019-12-25.
 */
class ArticlesReviewViewModel @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val fetchReviewedArticlesUseCase: FetchReviewedArticlesUseCase
) : BaseViewModel(), ArticlesReviewViewContract.ViewModelEvents {

    private val _action = SingleLiveEvent<ArticlesReviewViewContract.Action>()

    private var articlesReviewView = ArticlesReviewView()

    private val _articlesReviewViewStates = MutableLiveData<ArticlesReviewViewContract.State>()
    override val articlesReviewViewStates: LiveData<ArticlesReviewViewContract.State> = _articlesReviewViewStates

    private val _articlesReviewViewEvents = SingleLiveEvent<ArticlesReviewViewContract.Event>().apply {
        addSource(_action) {
            handleAction(it)
        }
    }
    override val articlesReviewViewEvents: LiveData<ArticlesReviewViewContract.Event> = _articlesReviewViewEvents

    override fun invokeAction(action: ArticlesReviewViewContract.Action) {
        _action.postValue(action)
    }

    private fun handleAction(action: ArticlesReviewViewContract.Action) {

        when (action) {
            ArticlesReviewViewContract.Action.InitPage -> fetchReviewedArticles()
            ArticlesReviewViewContract.Action.RefreshGridLayoutSpanCount -> refreshGridLayoutSpanCount()
            ArticlesReviewViewContract.Action.SwitchGridLayoutSpanCount -> switchGridLayoutSpanCount()
        }
    }

    private fun fetchReviewedArticles() {

        _articlesReviewViewStates.notLet {

            viewModelScope.launch(coroutineDispatcher) {
                fetchReviewedArticlesUseCase.invoke(UseCase.None()).also {
                    it.either(::handleFailure, ::handleFetchReviewedArticlesSuccess)
                }
            }
        }
    }

    private fun refreshGridLayoutSpanCount() {

        _articlesReviewViewStates.let {
            _articlesReviewViewStates.postValue(ArticlesReviewViewContract.State.ShowArticlesReview(articlesReviewView))
        }
    }

    private fun switchGridLayoutSpanCount() {

        val newSwitchGrid =
            if (articlesReviewView.switchGridLayout.isTypeGrid()) SwitchGridLayout.SwitchGridLayoutTypeList
            else SwitchGridLayout.SwitchGridLayoutTypeGrid

        articlesReviewView.switchGridLayout = newSwitchGrid
        _articlesReviewViewStates.postValue(ArticlesReviewViewContract.State.ShowArticlesReview(articlesReviewView))
    }

    private fun handleFetchReviewedArticlesSuccess(list: List<ArticleView>) {
        articlesReviewView.list = list
        _articlesReviewViewStates.postValue(ArticlesReviewViewContract.State.ShowArticlesReview(articlesReviewView))
    }
}
