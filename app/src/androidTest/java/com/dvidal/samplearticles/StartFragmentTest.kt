package com.dvidal.samplearticles

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dvidal.samplearticles.features.start.presentation.StartActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartFragmentTest : BaseEspressoTest() {

    @get:Rule
    val activityRule = ActivityTestRule(StartActivity::class.java, true, false)

    @Test
    fun whenShowRatesFragment_shouldCallInitPageAction() {

        activityRule.launchActivity(Intent())
    }

    @Before
    fun setup() {}
}