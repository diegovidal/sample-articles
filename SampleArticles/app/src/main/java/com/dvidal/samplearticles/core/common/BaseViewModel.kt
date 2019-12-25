package com.dvidal.samplearticles.core.common

import androidx.lifecycle.ViewModel

/**
 * @author diegovidal on 14/12/18.
 */

abstract class BaseViewModel : ViewModel() {

    private val failure by lazy { SingleLiveEvent<Throwable>() }

    open fun handleFailure(failure: Throwable) {
        this.failure.postValue(failure)
    }
}