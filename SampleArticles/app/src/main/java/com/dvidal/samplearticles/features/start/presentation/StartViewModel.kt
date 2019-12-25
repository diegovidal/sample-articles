package com.dvidal.samplearticles.features.start.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.dvidal.samplearticles.core.common.*
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import com.dvidal.samplearticles.features.start.domain.usecases.ClearArticlesUseCase
import com.dvidal.samplearticles.features.start.domain.usecases.StartArticlesUseCase
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-24.
 */
class StartViewModel @Inject constructor(
    private val clearArticlesUseCase: ClearArticlesUseCase,
    private val startArticlesUseCase: StartArticlesUseCase
): BaseViewModel() {

    private val _requestStartArticles = MutableLiveData<StartViewModelContract.ViewState>()
    private val requestStartArticles: LiveData<StartViewModelContract.ViewState> = _requestStartArticles

    private val _requestClearArticles = MutableLiveData<StartViewModelContract.ViewState>()
    private val requestClearArticles: LiveData<StartViewModelContract.ViewState> = _requestClearArticles

    val viewStateSingleLiveEvents = SingleLiveEvent<StartViewModelContract.ViewState>().apply {

        addSource(requestStartArticles){
            postValue(it)
        }

        addSource(requestClearArticles){
            postValue(it)
        }
    }

    fun startArticles() {

        _requestStartArticles.postValue(StartViewModelContract.ViewState.StartArticlesLoading)
        startArticlesUseCase.invoke(UseCase.None(), Dispatchers.IO, job) {
            it.either(::handleStartArticlesFailure, ::handleStartArticlesSuccess)
        }
    }

    fun clearArticles() {

        _requestClearArticles.postValue(StartViewModelContract.ViewState.ClearArticlesLoading)
        clearArticlesUseCase.invoke(UseCase.None(), Dispatchers.IO, job) {
            it.either(::handleClearArticlesFailure, ::handleClearArticlesSuccess)
        }
    }

    private fun handleStartArticlesFailure(failure: Throwable) {
        _requestStartArticles.postValue(StartViewModelContract.ViewState.Warning.StartArticlesError(failure))
    }

    private fun handleStartArticlesSuccess(list: List<ArticleView>) {

        val articlesInfoParam = ArticlesInfoParam.calculateArticlesInfoParam(list)
        _requestStartArticles.postValue(StartViewModelContract.ViewState.StartArticlesSuccess(articlesInfoParam))
    }

    private fun handleClearArticlesFailure(failure: Throwable) {
        _requestStartArticles.postValue(StartViewModelContract.ViewState.Warning.ClearArticlesError(failure))
    }

    private fun handleClearArticlesSuccess(unit: Unit) {
        _requestStartArticles.postValue(StartViewModelContract.ViewState.ClearArticlesSuccess)
    }
}