package com.example.nasagridapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nasagridapp.app.ui.details.adapter.ImageDetailsAdapter
import com.example.nasagridapp.app.ui.grid.ImageGridFragment
import com.example.nasagridapp.app.ui.grid.adapter.ImageGridAdapter
import org.hamcrest.MatcherAssert

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.nasagridapp", appContext.packageName)
    }

    @Test
    fun initialClickTest() {
        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(withId(R.id.imageGridRV))
            .check(RecyclerViewItemCountAssertion(1))

        Espresso.onView(withId(R.id.imageGridRV))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ImageGridAdapter.ImageGridViewHolder>(1, ViewActions.click()))
    }

    @Test
    fun fullFunctionalityTest() {
        ActivityScenario.launch(MainActivity::class.java)

        Espresso.onView(withId(R.id.imageGridRV))
            .check(RecyclerViewItemCountAssertion(20))


        for (index in 0 until 10) {
            Espresso.onView(withId(R.id.imageGridRV))
                .perform(RecyclerViewActions.actionOnItemAtPosition<ImageGridAdapter.ImageGridViewHolder>(index, ViewActions.click()))
            Espresso.onView(withId(R.id.totalImageCountText)).check(
                ViewAssertions.matches(
                    ViewMatchers.withText(
                        "${index + 1} / 26"
                    )
                )
            )
            Espresso.onView(withId(R.id.backIcon)).perform(ViewActions.click())
        }

        Espresso.onView(withId(R.id.imageGridRV))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ImageGridAdapter.ImageGridViewHolder>(10, ViewActions.click()))


        for (index in 10 until 20) {
            Espresso.onView(withId(R.id.totalImageCountText)).check(
                ViewAssertions.matches(
                    ViewMatchers.withText(
                        "${index + 1} / 26"
                    )
                )
            )
            Espresso.onView(withId(R.id.imageDetailRv))
                .perform(RecyclerViewActions.scrollToPosition<ImageDetailsAdapter.ImageDetailViewHolder>(index+1))
        }

    }

}

class RecyclerViewItemCountAssertion(private val minimumCount: Int) : ViewAssertion {
    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as? RecyclerView
        val adapter = recyclerView?.adapter
        MatcherAssert.assertThat("Grid has at least 1 item present", adapter?.itemCount?:0 >= minimumCount)
    }
}