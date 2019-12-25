package com.dvidal.samplearticles.features.start.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dvidal.samplearticles.R

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    companion object {

        const val EXTRA_ARTICLES_INFO_PARAM = "EXTRA_ARTICLES_INFO_PARAM"
    }
}
