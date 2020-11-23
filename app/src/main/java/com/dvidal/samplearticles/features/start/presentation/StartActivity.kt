package com.dvidal.samplearticles.features.start.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dvidal.samplearticles.MyApplication
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.core.navigator.Navigator
import com.dvidal.samplearticles.features.start.domain.ArticlesInfoParam
import javax.inject.Inject

class StartActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    private fun injectDagger() {
        (application as MyApplication).appComponent.activityComponent()
            .build().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        injectDagger()

        if (savedInstanceState == null)
            navigator.inflateStartFragment(this)
    }

    fun goToArticlesActivity(articlesInfoParam: ArticlesInfoParam) {
        navigator.goToArticlesActivity(this, articlesInfoParam)
    }

    companion object {

        const val EXTRA_ARTICLES_INFO_PARAM = "EXTRA_ARTICLES_INFO_PARAM"
    }
}
