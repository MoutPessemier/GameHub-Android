package digitized.gamehub

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CreatePartyFragment  {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun validateInput() {
        performInput()
        checkInput()
    }

    private fun performInput(){
        onView(withId(R.id.txt_party_name)).perform(typeText("Test"), closeSoftKeyboard())
        onView(withId(R.id.txt_party_date)).perform(typeText("2019-12-12"), closeSoftKeyboard())
        onView(withId(R.id.txt_max_size)).perform(typeText("4"), closeSoftKeyboard())
    }

    private fun checkInput() {
        onView(withId(R.id.txt_party_name)).check(matches(withText("Test")))
        onView(withId(R.id.txt_party_date)).check(matches(withText("2019-12-12")))
        onView(withId(R.id.txt_max_size)).check(matches(withText("4")))
    }
}