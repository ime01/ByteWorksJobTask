package com.flowz.byteworksjobtask.ui.employees

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.flowz.byteworksjobtask.MainActivity
import com.flowz.byteworksjobtask.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmployeeFragmentTest{

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun confirmViews() {

        Espresso.onView(withId(R.id.ad_admin_login))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(withId(R.id.lg_password))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}