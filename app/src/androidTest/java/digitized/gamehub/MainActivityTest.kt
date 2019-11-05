package digitized.gamehub

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isOpen
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun onStartUp_showCorrectFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.constraint_game_card))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun onClickInfo_goToGamePartyInfoFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_info)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.constraint_party_info))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun onHamburgerClicked_openDrawer() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open())
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
            .check(ViewAssertions.matches(isOpen()))
    }

    @Test
    fun navigationMenu_createPartyClicked_openCreatePartyFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open())
        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_create_party_view))
        Espresso.onView(ViewMatchers.withId(R.id.constraint_create_party))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun navigationMenu_createGameClicked_openCreateGameFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open())
        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_create_party_view))
        Espresso.onView(ViewMatchers.withId(R.id.constraint_create_party))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun navigationMenu_mapClicked_openMapFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open())
        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_create_party_view))
        Espresso.onView(ViewMatchers.withId(R.id.constraint_create_party))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun navigationMenu_accountClicked_openAccountFragment() {
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open())
        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_create_party_view))
        Espresso.onView(ViewMatchers.withId(R.id.constraint_create_party))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun infoScreen_upButtonClicked_backToTitle() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_info)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withContentDescription(R.string.abc_action_bar_up_description))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.constraint_game_card))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun gameScreen_slideNavDrawerOpen_doesNotOpen() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_info)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout)).perform(DrawerActions.open())
        Espresso.onView(ViewMatchers.withId(R.id.constraint_party_info))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}