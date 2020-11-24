package com.dvidal.samplearticles.features.articles.presentation.review

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment
import com.dvidal.samplearticles.core.di.module.viewmodel.ViewModelFactory
import com.dvidal.samplearticles.features.articles.presentation.ArticlesActivity
import kotlinx.android.synthetic.main.fragment_articles_review.*
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-25.
 */
class ArticlesReviewFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: ArticlesReviewAdapter

    private var menuItem: MenuItem? = null

    private val viewModel: ArticlesReviewViewContract.ViewModelEvents by lazy {
        ViewModelProvider(this, viewModelFactory).get(ArticlesReviewViewModel::class.java)
    }

    override fun layoutRes() = R.layout.fragment_articles_review
    override fun injectDagger() = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.invokeAction(ArticlesReviewViewContract.Action.InitPage)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()

        viewModel.articlesReviewViewStates.observe(viewLifecycleOwner, Observer(::handleViewStates))
        viewModel.articlesReviewViewEvents.observe(viewLifecycleOwner, Observer {  })
    }

    override fun onResume() {
        super.onResume()
        (activity as? ArticlesActivity)?.updateActionBarTitle(R.string.articles_review_title)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.grid_type, menu)
        menuItem = menu.findItem(R.id.grid_type)
        viewModel.invokeAction(ArticlesReviewViewContract.Action.RefreshGridLayoutSpanCount)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.grid_type -> viewModel.invokeAction(ArticlesReviewViewContract.Action.SwitchGridLayoutSpanCount)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureRecyclerView() {

        rv_articles_review.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
        rv_articles_review.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rv_articles_review.adapter = adapter
    }

    private fun handleViewStates(viewState: ArticlesReviewViewContract.State?) {

        when (viewState) {
            is ArticlesReviewViewContract.State.ShowArticlesReview -> {

                val manager = rv_articles_review.layoutManager as? GridLayoutManager
                manager?.spanCount = viewState.articlesReviewView.switchGridLayout.spanCount
                manager?.requestLayout()
                adapter.updateDataSet(viewState.articlesReviewView.list, viewState.articlesReviewView.switchGridLayout)
                updateDrawableMenuItem(viewState.articlesReviewView.switchGridLayout.drawable)
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