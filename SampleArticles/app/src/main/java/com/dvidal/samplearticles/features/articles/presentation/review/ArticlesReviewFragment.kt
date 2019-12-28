package com.dvidal.samplearticles.features.articles.presentation.review

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment
import com.dvidal.samplearticles.core.di.module.viewmodel.ViewModelFactory
import com.dvidal.samplearticles.features.articles.presentation.review.ArticlesReviewAdapter.Companion.ITEM_VIEW_TYPE_LIST
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

    private var menuItem: MenuItem? = null

    private val viewModel by lazy {
        viewModelFactory.get<ArticlesReviewViewModel>(this)
    }

    override fun layoutRes() = R.layout.fragment_articles_review
    override fun injectDagger() = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel?.fetchReviewedArticles()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.grid_type, menu)
        menuItem = menu?.findItem(R.id.grid_type)
        viewModel?.refreshGridLayoutSpanCount()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.grid_type -> viewModel?.switchGridLayoutSpanCount()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()

        viewModel?.fetchReviewedArticles?.observe(
            viewLifecycleOwner,
            Observer(::handleViewStatesLiveEvents)
        )

        viewModel?.switchGridLayout?.observe(
            viewLifecycleOwner,
            Observer(::handleViewStatesLiveEvents)
        )
    }

    private fun configureRecyclerView() {

        rv_articles_review.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        rv_articles_review.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv_articles_review.adapter = adapter
    }

    private fun handleViewStatesLiveEvents(viewState: ArticlesReviewViewModelContract.ViewState?) {

        when (viewState) {
            is ArticlesReviewViewModelContract.ViewState.ShowArticlesReview -> adapter.updateDataSet(
                viewState.list
            )
            is ArticlesReviewViewModelContract.ViewState.SwitchGridLayout -> {

                val manager = rv_articles_review.layoutManager as GridLayoutManager
                manager.spanCount = viewState.spanCount
                manager.requestLayout()
                (rv_articles_review.adapter as? ArticlesReviewAdapter)?.switchGridLayoutSpanCount(
                    viewState
                )
                updateDrawableMenuItem(viewState.drawable)
            }
        }
    }

    private fun updateDrawableMenuItem(drawable: Int) {

        menuItem?.setIcon(drawable)
    }

    companion object {

        const val ARTICLES_REVIEW_FRAGMENT_TAG = "ARTICLES_REVIEW_FRAGMENT_TAG"

        fun newInstance() = ArticlesReviewFragment()
    }
}