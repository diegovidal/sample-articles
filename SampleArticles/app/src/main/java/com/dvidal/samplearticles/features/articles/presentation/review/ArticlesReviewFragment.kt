package com.dvidal.samplearticles.features.articles.presentation.review

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment
import com.dvidal.samplearticles.core.di.module.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_articles_review.*
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-25.
 */
class ArticlesReviewFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var adapter: ArticlesReviewAdapter

    private val viewModel by lazy {
        viewModelFactory.get<ArticlesReviewViewModel>(this)
    }

    override fun layoutRes() = R.layout.fragment_articles_review
    override fun injectDagger() = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel?.fetchReviewedArticles()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()

        viewModel?.viewStatesLiveEvents?.observe(
            viewLifecycleOwner,
            Observer(::handleViewStatesLiveEvents)
        )

        bt_grid_span_count?.setOnClickListener {

            rv_articles_review.layoutManager =
                GridLayoutManager(context, 3)
        }
    }

    private fun configureRecyclerView() {

        rv_articles_review.layoutManager =
            GridLayoutManager(context, 1)
        rv_articles_review.adapter = adapter
    }

    private fun handleViewStatesLiveEvents(viewState: ArticlesReviewViewModelContract.ViewState?) {

        when (viewState) {
            is ArticlesReviewViewModelContract.ViewState.ShowArticlesReview -> {
                adapter.updateDataSet(viewState.list)
            }
        }
    }

    companion object {

        const val ARTICLES_REVIEW_FRAGMENT_TAG = "ARTICLES_REVIEW_FRAGMENT_TAG"

        fun newInstance() = ArticlesReviewFragment()
    }
}