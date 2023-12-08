package com.dicoding.courseschedule.ui.list

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class ListActivityTest {
    @get:Rule
    var activityRule = ActivityScenarioRule(ListActivity::class.java)

    @Before
    fun initValid() {
        Intents.init()
    }

    @After
    fun initFinish() {
        Intents.release()
    }

    @Test
    fun addButtonTest() {
        activityRule.scenario
        Espresso.onView(withId(R.id.fab))
            .perform(ViewActions.click())
        Intents.intended(
            allOf(
                hasComponent(AddCourseActivity::class.java.name)
            )
        )
    }
}