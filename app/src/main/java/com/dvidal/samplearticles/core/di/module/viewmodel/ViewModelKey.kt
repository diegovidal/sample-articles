package com.dvidal.samplearticles.core.di.module.viewmodel

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * @author diegovidal on 17/12/18.
 */

@MapKey
annotation class ViewModelKey(val classKey: KClass<out ViewModel>)