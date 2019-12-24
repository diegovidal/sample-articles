package com.dvidal.samplearticles.features.start.presentation

import com.dvidal.samplearticles.core.common.BaseViewModel
import com.dvidal.samplearticles.core.common.UseCase
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.domain.usecases.ClearArticlesUseCase
import com.dvidal.samplearticles.features.start.domain.usecases.StartArticlesUseCase
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-24.
 */
class StartViewModel @Inject constructor(
    private val clearArticlesUseCase: ClearArticlesUseCase,
    private val startArticlesUseCase: StartArticlesUseCase
): BaseViewModel() {

    fun startArticles() {

        startArticlesUseCase.invoke(UseCase.None(), Dispatchers.IO, job) {
            it.either(::handleFailure, ::handleSuccess)
        }
    }

    override fun handleFailure(failure: Throwable) {
        Timber.i("E aeee erro")
    }

    private fun handleSuccess(list: List<ArticleView>) {
        Timber.i("E aeee successooo")
    }
}