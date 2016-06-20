package ua.com.prologistic.openweathermapapp;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    public static final String DEFAULT_LOCATION = "Mykolayiv, UA";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void testCheckDefaultLocation() {
        onView(withText(DEFAULT_LOCATION)).check(matches(isDisplayed()));
    }

    @Test
    public void testCheckSecondTabs() {
        onView(withId(R.id.t_weather_icon)).perform(swipeLeft());
    }

    @Test
    public void testCheckOptionsMenuEnabled() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void testToolbarRefreshItem() {
        onView(withId(R.id.refresh)).perform(click());
    }

    @Test(expected = IllegalStateException.class)
    public void testRecyclerViewIsDisplaying() {
        onView(withId(R.id.recycler_five_day_forecast)).check(matches(isDisplayingAtLeast(0)));
    }

    @Test
    public void testToolbarSettingsItemDoesNotExist() {
        onView(withId(R.id.t_min_max)).check(matches(isDisplayed()));
    }

    @Test
    public void testFragment() {
        onView(withText(DEFAULT_LOCATION)).check(matches(isDisplayed()));
    }

    @Test(expected = NullPointerException.class)
    public void testCheckRefreshedViewExist() {

        onView(withId(R.id.refresh)).perform(click());
        onView(withId(R.id.recycler_five_day_forecast)).check(null);

    }
}

