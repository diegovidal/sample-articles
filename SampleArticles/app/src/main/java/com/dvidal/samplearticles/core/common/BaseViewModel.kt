package com.dvidal.samplearticles.core.common

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * @author diegovidal on 14/12/18.
 */

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    protected val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    val failure by lazy { SingleLiveEvent<Throwable>() }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    open fun handleFailure(failure: Throwable) {
        this.failure.postValue(failure)
    }
}