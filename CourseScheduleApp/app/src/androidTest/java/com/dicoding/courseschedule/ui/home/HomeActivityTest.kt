package com.dicoding.courseschedule.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Before
    fun start(){
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun openAddCourseActivity(){
        Intents.init()

        onView(withId(R.id.action_add)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.action_add)).perform(ViewActions.click())

        intended(hasComponent(AddCourseActivity::class.java.name))
        Intents.release()
    }
}