package com.dvidal.samplearticles.features.articles.presentation.selection

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment
import com.dvidal.samplearticles.core.di.module.viewmodel.ViewModelFactory
import com.dvidal.samplearticles.features.articles.presentation.ArticleView
import com.dvidal.samplearticles.features.articles.presentation.ArticlesActivity
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import com.dvidal.samplearticles.features.start.presentation.StartActivity.Companion.EXTRA_ARTICLES_INFO_PARAM
import kotlinx.android.synthetic.main.empty_view_articles_selection.*
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
        viewModelFactory.get<ArticlesSelectionViewModel>(this)
    }

    override fun layoutRes() = R.layout.fragment_articles_selection
    override fun injectDagger() = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<ArticlesInfoParam>(EXTRA_ARTICLES_INFO_PARAM)?.let {
            viewModel?.initArticlesSelectionScreen(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureButtons()
        viewModel?.viewStatesLiveEvents?.observe(
            viewLifecycleOwner,
            Observer(::handleViewStatesLiveEvents)
        )
    }

    private fun configureButtons() {

        bt_like_article.setOnClickListener {
            viewModel?.reviewArticleUseCase(
                ArticlesSelectionViewModelContract.UserInteraction.LikeArticle()
            )
        }
        bt_dislike_article.setOnClickListener {
            viewModel?.reviewArticleUseCase(
                ArticlesSelectionViewModelContract.UserInteraction.DislikeArticle()
            )
        }
        bt_see_reviews.setOnClickListener(::onClickToSeeReviews)
    }

    private fun handleViewStatesLiveEvents(viewState: ArticlesSelectionViewModelContract.ViewState?) {

        refreshArticlesInfo(viewState?.articlesInfoParam)
        when (viewState) {

            is ArticlesSelectionViewModelContract.ViewState.ShowTwoArticlesOnQueue -> handleShowTwoArticlesOnQueue(
                viewState.firstArticle,
                viewState.secondArticle
            )
            is ArticlesSelectionViewModelContract.ViewState.ShowLastArticleOnQueue -> handleShowLastArticleOnQueue(
                viewState.lastArticle
            )
            is ArticlesSelectionViewModelContract.ViewState.ArticlesSelectionEmpty -> handleArticlesSelectionEmpty()
            else -> Timber.i("handleViewStatesLiveEvents else sentence.")
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
            .centerCrop()
            .into(iv_first_article)
    }

    private fun handleShowLastArticleOnQueue(lastArticle: ArticleView) {

        shouldShowContentView(true)
        Glide
            .with(requireContext())
            .load(lastArticle.imageUrl)
            .centerCrop()
            .into(iv_first_article)
    }

    private fun handleArticlesSelectionEmpty() {

        shouldShowContentView(false)
    }

    private fun shouldShowContentView(isVisible: Boolean) {

        content_view_articles.isVisible = isVisible
        empty_view.isVisible = !isVisible
    }

    private fun onClickToSeeReviews(v: View) {

        (activity as? ArticlesActivity)?.inflateArticlesReviewFragment()
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