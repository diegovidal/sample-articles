package com.dvidal.samplearticles.features.start.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dvidal.samplearticles.core.common.BaseViewModel
import com.dvidal.samplearticles.core.common.SingleLiveEvent
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import com.dvidal.samplearticles.features.start.domain.usecases.ClearArticlesUseCase
import com.dvidal.samplearticles.features.start.domain.usecases.StartArticlesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-24.
 */
class StartViewModel @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val clearArticlesUseCase: ClearArticlesUseCase,
    private val startArticlesUseCase: StartArticlesUseCase
) : BaseViewModel(), StartViewContract.ViewModelEvents {

    private val _action = SingleLiveEvent<StartViewContract.Action>()

    private val _startViewStates = MediatorLiveData<StartViewContract.State>()
    override val startViewStates: LiveData<StartViewContract.State> = _startViewStates

    private val _startViewEvents = SingleLiveEvent<StartViewContract.Event>().apply {

        addSource(_action) {
            handleAction(it)
        }
    }
    override val startViewEvents: LiveData<StartViewContract.Event> = _startViewEvents


    override fun invokeAction(action: StartViewContract.Action) {
        _action.postValue(action)
    }


    private fun handleAction(action: StartViewContract.Action) {

        when(action) {
            StartViewContract.Action.StartArticles -> startArticles()
            StartViewContract.Action.ClearArticles -> clearArticles()
        }
    }

    fun startArticles() {

        _startViewStates.postValue(StartViewContract.State.StartViewState(isStartArticlesLoading = true, isClearArticlesLoading = false))
        viewModelScope.launch(coroutineDispatcher) {
            startArticlesUseCase.invoke(UseCase.None()).also {
                it.either(::handleStartArticlesFailure, ::handleStartArticlesSuccess)
                _startViewStates.postValue(StartViewContract.State.StartViewState(isStartArticlesLoading = false, isClearArticlesLoading = false))
            }
        }
    }

    fun clearArticles() {

        _startViewStates.postValue(StartViewContract.State.StartViewState(isStartArticlesLoading = false, isClearArticlesLoading = true))
        viewModelScope.launch(coroutineDispatcher) {
            clearArticlesUseCase.invoke(UseCase.None()).also {
                it.either(::handleClearArticlesFailure, ::handleClearArticlesSuccess)
                _startViewStates.postValue(StartViewContract.State.StartViewState(isStartArticlesLoading = false, isClearArticlesLoading = false))
            }
        }
    }

    private fun handleStartArticlesSuccess(list: List<ArticleView>) {

        val articlesInfoParam = ArticlesInfoParam.calculateArticlesInfoParam(list)
        _startViewEvents.postValue(StartViewContract.Event.StartArticlesSuccess(articlesInfoParam))
    }

    private fun handleStartArticlesFailure(failure: Throwable) {
        _startViewEvents.postValue(StartViewContract.Event.DisplayWarning(failure))
    }

    private fun handleClearArticlesSuccess(unit: Unit) {
        _startViewEvents.postValue(StartViewContract.Event.ClearArticlesSuccess)
    }

    private fun handleClearArticlesFailure(failure: Throwable) {
        _startViewEvents.postValue(StartViewContract.Event.DisplayWarning(failure))
    }
}