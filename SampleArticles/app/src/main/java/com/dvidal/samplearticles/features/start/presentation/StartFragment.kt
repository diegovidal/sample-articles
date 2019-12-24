package com.dvidal.samplearticles.features.start.presentation

import android.os.Bundle
import com.dvidal.samplearticles.MyApplication
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment
import com.dvidal.samplearticles.core.di.module.viewmodel.ViewModelFactory
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-24.
 */
class StartFragment: BaseFragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        viewModelFactory.get<StartViewModel>(requireActivity())
    }

    override fun layoutRes() = R.layout.fragment_start

    override fun injectDagger() {
        (activity?.application as MyApplication).appComponent.activityComponent()
            .build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel?.startArticles()
    }
}