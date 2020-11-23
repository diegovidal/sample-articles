package com.dvidal.samplearticles.features.start.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment
import com.dvidal.samplearticles.core.di.module.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_start.*
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-24.
 */
class StartFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: StartViewContract.ViewModelEvents by lazy {
        ViewModelProvider(this, viewModelFactory).get(StartViewModel::class.java)
    }

    override fun layoutRes() = R.layout.fragment_start
    override fun injectDagger() = component.inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureButtonsListener()

        viewModel.startViewStates.observe(
            viewLifecycleOwner,
            Observer(::handleViewStates)
        )
        viewModel.startViewEvents.observe(
            viewLifecycleOwner,
            Observer(::handleViewEvents)
        )
    }

    private fun configureButtonsListener() {

        bt_start_articles.setOnClickListener { viewModel.invokeAction(StartViewContract.Action.StartArticles) }
        bt_clear_articles.setOnClickListener { viewModel.invokeAction(StartViewContract.Action.ClearArticles) }
    }

    private fun handleViewEvents(viewState: StartViewContract.Event) {

        when (viewState) {
            is StartViewContract.Event.StartArticlesSuccess -> (activity as? StartActivity)?.goToArticlesActivity(
                viewState.articlesInfoParam
            )
            is StartViewContract.Event.ClearArticlesSuccess -> showToast(getString(R.string.toast_articles_cleared))
            is StartViewContract.Event.DisplayWarning -> showToast(viewState.throwable.message)
        }
    }

    private fun handleViewStates(viewState: StartViewContract.State) {

        if (viewState is StartViewContract.State.StartViewState) {
            tv_start_articles.text =
                if (viewState.isStartArticlesLoading) getString(
                    R.string.loading_articles_label
                ) else getString(R.string.start_articles_label)

            bt_start_articles.isEnabled = !viewState.isStartArticlesLoading
            pb_start_articles.isVisible = viewState.isStartArticlesLoading

            bt_clear_articles.isEnabled = !viewState.isClearArticlesLoading
            pb_clear_articles.isVisible = viewState.isClearArticlesLoading
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}