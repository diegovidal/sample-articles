package com.dvidal.samplearticles.features.start.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment
import com.dvidal.samplearticles.core.di.module.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_start.*
import timber.log.Timber
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-24.
 */
class StartFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        viewModelFactory.get<StartViewModel>(requireActivity())
    }

    override fun layoutRes() = R.layout.fragment_start
    override fun injectDagger() = component.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureButtonsListener()

        viewModel?.viewStatesSingleLiveEvents?.observe(
            viewLifecycleOwner,
            Observer(::handleViewStatesSingleLiveEvents)
        )
    }

    private fun configureButtonsListener() {

        bt_start_articles.setOnClickListener { viewModel?.startArticles() }
        bt_clear_articles.setOnClickListener { viewModel?.clearArticles() }
    }

    private fun handleViewStatesSingleLiveEvents(viewState: StartViewModelContract.ViewState?) {

        handleButton(viewState)
        when (viewState) {
            is StartViewModelContract.ViewState.StartArticlesSuccess -> (activity as? StartActivity)?.goToArticlesActivity(
                viewState.articlesInfoParam
            )
            is StartViewModelContract.ViewState.ClearArticlesSuccess -> showToast(getString(R.string.toast_articles_cleared))
            is StartViewModelContract.ViewState.Warning -> showToast(viewState.throwable.message)
        }
    }

    private fun handleButton(viewState: StartViewModelContract.ViewState?) {

        tv_start_articles.text =
            if (viewState is StartViewModelContract.ViewState.Loading.StartArticlesLoading) getString(R.string.loading_articles_label)
            else getString(R.string.start_articles_label)

        bt_start_articles.isEnabled = viewState !is StartViewModelContract.ViewState.Loading.StartArticlesLoading
        pb_start_articles.isVisible = viewState is StartViewModelContract.ViewState.Loading.StartArticlesLoading
        bt_clear_articles.isEnabled = viewState !is StartViewModelContract.ViewState.Loading.ClearArticlesLoading
        pb_clear_articles.isVisible = viewState is StartViewModelContract.ViewState.Loading.ClearArticlesLoading
    }

    private fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}