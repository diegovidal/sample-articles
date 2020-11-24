package com.dvidal.samplearticles.features.start.presentation

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.dvidal.samplearticles.BaseEspressoTest
import com.dvidal.samplearticles.R
import com.dvidal.samplearticles.utils.FragmentTestRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartFragmentTest: BaseEspressoTest() {

    @get:Rule
    val fragmentRule = FragmentTestRule(StartFragment::class.java)

    private val states = MutableLiveData<StartViewContract.State>()
    private val events = MutableLiveData<StartViewContract.Event>()

    private val mockViewModel = mockk<StartViewModel>(relaxUnitFun = true)

    @Before
    fun setup() {

        val viewModelFactory = application.appComponent.viewModelFactor
        every { viewModelFactory.create(StartViewModel::class.java) } returns mockViewModel

        every { mockViewModel.startViewStates } returns states
        every { mockViewModel.startViewEvents } returns events
    }

    @Test
    fun myTest() {
        fragmentRule.launchActivity(null)

        runBlocking(Dispatchers.Main) {
            states.postValue(StartViewContract.State.StartViewState(isStartArticlesLoading = false, isClearArticlesLoading = false))
        }

        onView(withId(R.id.bt_start_articles)).check(matches(isDisplayed()))
        onView(withId(R.id.bt_clear_articles)).check(matches(isDisplayed()))
    }
}