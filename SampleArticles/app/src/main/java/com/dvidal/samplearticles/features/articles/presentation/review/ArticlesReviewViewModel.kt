package com.dvidal.samplearticles.features.articles.presentation.review

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvidal.samplearticles.core.common.BaseViewModel
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.core.common.notLet
import com.dvidal.samplearticles.features.articles.domain.usecases.FetchReviewedArticlesUseCase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-25.
 */
class ArticlesReviewViewModel @Inject constructor(
    private val fetchReviewedArticlesUseCase: FetchReviewedArticlesUseCase
): BaseViewModel() {

    private val _fetchReviewedArticles = MutableLiveData<List<ArticleView>>()
    val viewStatesLiveEvents = MediatorLiveData<ArticlesReviewViewModelContract.ViewState>().apply {

        addSource(_fetchReviewedArticles){
            postValue(ArticlesReviewViewModelContract.ViewState.ShowArticlesReview(it))
        }
    }

    fun fetchReviewedArticles() {

        _fetchReviewedArticles.notLet {
            fetchReviewedArticlesUseCase.invoke(UseCase.None(), Dispatchers.IO, viewModelScope) {
                it.either(
                    ::handleFetchReviewedArticlesFailure,
                    ::handleFetchReviewedArticlesSuccess
                )
            }
        }
    }

    private fun handleFetchReviewedArticlesFailure(throwable: Throwable) {}

    private fun handleFetchReviewedArticlesSuccess(list: List<ArticleView>) {
        _fetchReviewedArticles.postValue(list)
    }
}