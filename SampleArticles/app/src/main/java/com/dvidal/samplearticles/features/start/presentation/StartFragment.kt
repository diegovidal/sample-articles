package com.dvidal.samplearticles.features.start.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.dvidal.samplearticles.MyApplication
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment
import com.dvidal.samplearticles.core.di.module.viewmodel.ViewModelFactory
import com.dvidal.samplearticles.features.articles.presentation.ArticlesActivity
import kotlinx.android.synthetic.main.fragment_start.*
import timber.log.Timber
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureButtonsListener()

        viewModel?.viewStateSingleLiveEvents?.observe(viewLifecycleOwner, Observer (::handleViewStateSingleLiveEvents))
    }

    private fun configureButtonsListener() {

        bt_start_articles.setOnClickListener { viewModel?.startArticles() }
        bt_clear_articles.setOnClickListener { viewModel?.clearArticles() }
    }

    private fun handleViewStateSingleLiveEvents(viewState: StartViewModelContract.ViewState?) {

        when(viewState) {
            is StartViewModelContract.ViewState.StartArticlesLoading -> Timber.i("StartArticlesLoading")
            is StartViewModelContract.ViewState.StartArticlesSuccess -> goToArticlesActivity()
            is StartViewModelContract.ViewState.Warning.StartArticlesError -> showToast(viewState.throwable.message)

            is StartViewModelContract.ViewState.ClearArticlesLoading -> Timber.i("StartArticlesLoading")
            is StartViewModelContract.ViewState.ClearArticlesSuccess -> showToast(getString(R.string.toast_articles_cleared))
            is StartViewModelContract.ViewState.Warning.ClearArticlesError -> showToast(viewState.throwable.message)
        }
    }

    private fun goToArticlesActivity() {
        startActivity(Intent(context, ArticlesActivity::class.java))
    }

    private fun handleButton(isEnabled: Boolean, viewState: StartViewModelContract.ViewState?){

        when(viewState) {

            is StartViewModelContract.ViewState.StartArticlesLoading -> {

            }
            is StartViewModelContract.ViewState.ClearArticlesLoading -> {

            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}