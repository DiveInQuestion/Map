package com.example.a19360.map;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);
    @Test
    public void testAddSongs() throws Exception {
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.editText1)).perform(typeText("AndroidTest")
                , closeSoftKeyboard());
        onView(withId(R.id.button2)).perform(click());
    }

}