package com.dvidal.samplearticles.features.articles.presentation.selection

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.articles.presentation.ArticlesActivity
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import com.dvidal.samplearticles.features.start.presentation.StartActivity.Companion.EXTRA_ARTICLES_INFO_PARAM
import javax.inject.Inject
import kotlinx.android.synthetic.main.empty_view_articles_selection.bt_see_reviews
import kotlinx.android.synthetic.main.fragment_articles_selection.bt_dislike_article
import kotlinx.android.synthetic.main.fragment_articles_selection.bt_like_article
import kotlinx.android.synthetic.main.fragment_articles_selection.content_view_articles
import kotlinx.android.synthetic.main.fragment_articles_selection.empty_view
import kotlinx.android.synthetic.main.fragment_articles_selection.iv_first_article
import kotlinx.android.synthetic.main.fragment_articles_selection.pb_content_view
import kotlinx.android.synthetic.main.fragment_articles_selection.tv_articles_info

/**
 * @author diegovidal on 2019-12-24.
 */
class ArticlesSelectionFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ArticlesSelectionViewContract.ViewModelEvents by lazy {
        ViewModelProvider(this, viewModelFactory).get(ArticlesSelectionViewModel::class.java)
    }

    override fun layoutRes() = R.layout.fragment_articles_selection
    override fun injectDagger() = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<ArticlesInfoParam>(EXTRA_ARTICLES_INFO_PARAM)?.let {
            viewModel.invokeAction(ArticlesSelectionViewContract.Action.InitPage(it))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureButtonsListener()

        viewModel.articlesSelectionViewStates.observe(viewLifecycleOwner, Observer(::handleViewStates))
        viewModel.articlesSelectionViewEvents.observe(viewLifecycleOwner, Observer(::handleViewEvents))
    }

    override fun onResume() {
        super.onResume()
        (activity as? ArticlesActivity)?.updateActionBarTitle(R.string.articles_selection_title)
    }

    private fun configureButtonsListener() {

        bt_like_article.setOnClickListener {
            viewModel.invokeAction(ArticlesSelectionViewContract.Action.ReviewArticle.LikeArticle())
        }
        bt_dislike_article.setOnClickListener {
            viewModel.invokeAction(ArticlesSelectionViewContract.Action.ReviewArticle.DislikeArticle())
        }
        bt_see_reviews.setOnClickListener {
            viewModel.invokeAction(ArticlesSelectionViewContract.Action.NavigateToArticleReviews)
        }
    }

    private fun handleViewStates(viewState: ArticlesSelectionViewContract.State) {

        refreshArticlesInfo(viewState.articlesInfoParam)
        when (viewState) {

            is ArticlesSelectionViewContract.State.ShowTwoArticlesOnQueue -> handleShowTwoArticlesOnQueue(
                viewState.firstArticle,
                viewState.secondArticle
            )
            is ArticlesSelectionViewContract.State.ShowLastArticleOnQueue -> handleShowLastArticleOnQueue(
                viewState.lastArticle
            )
            is ArticlesSelectionViewContract.State.ArticlesSelectionEmpty -> handleArticlesSelectionEmpty()
        }
    }

    private fun handleViewEvents(events: ArticlesSelectionViewContract.Event) {

        when (events) {
            ArticlesSelectionViewContract.Event.GoToArticleReviews -> (activity as? ArticlesActivity)?.inflateArticlesReviewFragment()
        }
    }

    private fun refreshArticlesInfo(articlesInfoParam: ArticlesInfoParam?) {

        articlesInfoParam?.let {
            tv_articles_info.text =
                getString(R.string.label_articles_info, it.totalFavoriteArticles, it.totalArticles)
        }
    }

    private fun handleShowTwoArticlesOnQueue(
        firstArticle: ArticleView,
        secondArticle: ArticleView
    ) {

        shouldShowContentView(true)
        Glide
            .with(requireContext())
            .load(firstArticle.imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .centerCrop()
            .into(iv_first_article)
    }

    private fun handleShowLastArticleOnQueue(lastArticle: ArticleView) {

        shouldShowContentView(true)
        Glide
            .with(requireContext())
            .load(lastArticle.imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .centerCrop()
            .into(iv_first_article)
    }

    private fun handleArticlesSelectionEmpty() {

        shouldShowContentView(false)
    }

    private fun shouldShowContentView(isVisible: Boolean) {

        content_view_articles.isVisible = isVisible
        empty_view.isVisible = !isVisible
        pb_content_view.isVisible = false
    }

    companion object {

        fun newInstance(articlesInfoParam: ArticlesInfoParam?): ArticlesSelectionFragment {

            return ArticlesSelectionFragment().apply {
                arguments = Bundle().also {
                    it.putParcelable(EXTRA_ARTICLES_INFO_PARAM, articlesInfoParam)
                }
            }
        }
    }
}
