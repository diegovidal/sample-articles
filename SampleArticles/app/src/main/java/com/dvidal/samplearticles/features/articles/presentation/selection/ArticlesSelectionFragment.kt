package com.dvidal.samplearticles.features.articles.presentation.selection

import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.common.BaseFragment

/**
 * @author diegovidal on 2019-12-24.
 */
class ArticlesSelectionFragment: BaseFragment() {

    override fun layoutRes() = R.layout.fragment_articles_selection

    override fun injectDagger() {
        
    }

    companion object {

        const val TAG = "Articles_Selection_Fragment_Tag"

        fun newInstance(): ArticlesSelectionFragment {
            return ArticlesSelectionFragment()
        }
    }
}