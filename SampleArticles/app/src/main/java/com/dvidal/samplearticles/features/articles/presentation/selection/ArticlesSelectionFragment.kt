package com.dvidal.samplearticles.features.articles.presentation.selection

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.dvidal.samplearticles.MyApplication
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment
import com.dvidal.samplearticles.core.di.module.viewmodel.ViewModelFactory
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import com.dvidal.samplearticles.features.start.presentation.StartActivity.Companion.EXTRA_ARTICLES_INFO_PARAM
import kotlinx.android.synthetic.main.fragment_articles_selection.*
import timber.log.Timber
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-24.
 */
class ArticlesSelectionFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        viewModelFactory.get<ArticlesSelectionViewModel>(requireActivity())
    }

    override fun layoutRes() = R.layout.fragment_articles_selection

    override fun injectDagger() {
        (activity?.application as MyApplication).appComponent.activityComponent()
            .build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<ArticlesInfoParam>(EXTRA_ARTICLES_INFO_PARAM)?.let {
            viewModel?.initArticlesSelectionScreen(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.viewStatesLiveEvents?.observe(viewLifecycleOwner, Observer (::handleViewStatesLiveEvents))

        tv_test.setOnClickListener {
            viewModel?.reviewArticleUseCase(ArticlesSelectionViewModelContract.UserInteraction.LikeArticle())
        }
    }

    private fun handleViewStatesLiveEvents(viewState: ArticlesSelectionViewModelContract.ViewState?) {

        refreshArticlesInfo(viewState?.articlesInfoParam)
        when(viewState) {

            is ArticlesSelectionViewModelContract.ViewState.ShowTwoArticlesOnQueue -> handleShowTwoArticlesOnQueue(viewState.aip, viewState.firstArticle, viewState.secondArticle)
            is ArticlesSelectionViewModelContract.ViewState.ShowLastArticleOnQueue -> handleShowLastArticleOnQueue(viewState.aip, viewState.lastArticle)
            is ArticlesSelectionViewModelContract.ViewState.ArticlesSelectionEmpty -> handleArticlesSelectionEmpty(viewState.aip)
            else -> Timber.i("handleViewStatesLiveEvents else sentence.")
        }
    }

    private fun refreshArticlesInfo(articlesInfoParam: ArticlesInfoParam?) {

        articlesInfoParam?.let {

        }
    }

    private fun handleShowTwoArticlesOnQueue(aip: ArticlesInfoParam?, firstArticle: ArticleView, secondArticle: ArticleView) {
       val sas = firstArticle.sku
    }

    private fun handleShowLastArticleOnQueue(aip: ArticlesInfoParam?, lastArticle: ArticleView) {

    }

    private fun handleArticlesSelectionEmpty(aip: ArticlesInfoParam?) {

    }

    companion object {

        const val TAG = "Articles_Selection_Fragment_Tag"

        fun newInstance(articlesInfoParam: ArticlesInfoParam?): ArticlesSelectionFragment {

            val bundle = Bundle().apply {
                putParcelable(EXTRA_ARTICLES_INFO_PARAM, articlesInfoParam)
            }

            return ArticlesSelectionFragment().apply {
                arguments = bundle
            }
        }
    }
}