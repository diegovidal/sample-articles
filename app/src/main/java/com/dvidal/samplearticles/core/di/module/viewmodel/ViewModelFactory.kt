package com.dvidal.samplearticles.core.di.module.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author diegovidal on 17/12/18.
 */

class ViewModelFactory @Inject constructor(
    private val classToViewModel: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return classToViewModel[modelClass]?.get() as? T
            ?: throw NullPointerException("No view model mapping for class: ${modelClass.name}")
    }
}
