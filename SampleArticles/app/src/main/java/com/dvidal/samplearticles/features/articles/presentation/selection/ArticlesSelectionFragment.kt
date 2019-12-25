package com.dvidal.samplearticles.features.articles.presentation.selection

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.dvidal.samplearticles.MyApplication
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment
import com.dvidal.samplearticles.core.di.module.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_articles_selection.*
import timber.log.Timber
import javax.inject.Inject

/**
 * @author diegovidal on 2019-12-24.
 */
class ArticlesSelectionFragment: BaseFragment() {

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
        viewModel?.fetchUnreviewedArticles()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.fetchUnreviewedArticles?.observe(viewLifecycleOwner, Observer {
            Timber.i("Opa Entrou aqui!!")
        })

        tv_test.setOnClickListener {
            viewModel?.reviewArticleUseCase("000000001000091266")
        }
    }

    companion object {

        const val TAG = "Articles_Selection_Fragment_Tag"

        fun newInstance(): ArticlesSelectionFragment {
            return ArticlesSelectionFragment()
        }
    }
}