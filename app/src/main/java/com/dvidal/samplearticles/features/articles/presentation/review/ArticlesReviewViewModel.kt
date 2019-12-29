package com.dvidal.samplearticles.features.articles.presentation.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvidal.samplearticles.core.common.BaseViewModel
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.core.common.notLet
import com.dvidal.samplearticles.features.articles.domain.usecases.FetchReviewedArticlesUseCase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-25.
 */
class ArticlesReviewViewModel @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val fetchReviewedArticlesUseCase: FetchReviewedArticlesUseCase
) : BaseViewModel() {

    private val _fetchReviewedArticles = MutableLiveData<List<ArticleView>>()
    val fetchReviewedArticles =
        MediatorLiveData<ArticlesReviewViewModelContract.ViewState>().apply {

            addSource(_fetchReviewedArticles) {
                postValue(ArticlesReviewViewModelContract.ViewState.ShowArticlesReview(it))
            }
        }

    private val _switchGridLayout =
        MutableLiveData<ArticlesReviewViewModelContract.ViewState.SwitchGridLayout>(
            ArticlesReviewViewModelContract.ViewState.SwitchGridLayout.SwitchGridLayoutTypeList
        )
    val switchGridLayout: LiveData<ArticlesReviewViewModelContract.ViewState.SwitchGridLayout> =
        _switchGridLayout

    fun fetchReviewedArticles() {

        _fetchReviewedArticles.notLet {
            fetchReviewedArticlesUseCase.invoke(
                UseCase.None(),
                coroutineDispatcher,
                viewModelScope
            ) {
                it.either(
                    ::handleFetchReviewedArticlesFailure,
                    ::handleFetchReviewedArticlesSuccess
                )
            }
        }
    }

    fun switchGridLayoutSpanCount() {

        val newSwitchGrid =
            if (_switchGridLayout.value?.isTypeGrid() == true) ArticlesReviewViewModelContract.ViewState.SwitchGridLayout.SwitchGridLayoutTypeList
            else ArticlesReviewViewModelContract.ViewState.SwitchGridLayout.SwitchGridLayoutTypeGrid

        _switchGridLayout.postValue(newSwitchGrid)
    }

    fun refreshGridLayoutSpanCount() {

        _switchGridLayout.postValue(switchGridLayout.value)
    }

    private fun handleFetchReviewedArticlesFailure(throwable: Throwable) {}

    private fun handleFetchReviewedArticlesSuccess(list: List<ArticleView>) {
        _fetchReviewedArticles.postValue(list)
    }
}