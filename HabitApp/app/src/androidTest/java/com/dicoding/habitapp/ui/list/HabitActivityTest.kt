package com.dicoding.habitapp.ui.list

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
import com.dicoding.habitapp.R
import com.dicoding.habitapp.ui.add.AddHabitActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

//TODO 16 : Write UI test to validate when user tap Add Habit (+), the AddHabitActivity displayed
@RunWith(AndroidJUnit4::class)
class HabitActivityTest {
    @Before
    fun start(){
        ActivityScenario.launch(HabitListActivity::class.java)
    }

    @Test
    fun openAddHabitActivity(){
        Intents.init()

        onView(withId(R.id.fab)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.fab)).perform(ViewActions.click())

        intended(hasComponent(AddHabitActivity::class.java.name))
        Intents.release()
    }
}