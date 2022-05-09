package com.example.cse110lab5;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GuiFunctionalityTest {

    @Rule
    public ActivityTestRule<TodoListActivity> mActivityTestRule = new ActivityTestRule<>(TodoListActivity.class);

    @Test
    public void guiFunctionalityTest() {
        ViewInteraction appCompatImageView = onView(
                allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageView")), withContentDescription("Search"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withId(R.id.new_todo_text),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction searchAutoComplete = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete.perform(replaceText("Gorilla"), closeSoftKeyboard());

        ViewInteraction searchAutoComplete2 = onView(
                allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), withText("Gorilla"),
                        childAtPosition(
                                allOf(withClassName(is("android.widget.LinearLayout")),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                0),
                        isDisplayed()));
        searchAutoComplete2.perform(pressImeActionButton());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.add_todo_btn), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        DataInteraction textView = onData(anything())
                .inAdapterView(allOf(withId(R.id.listView),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                3)))
                .atPosition(0);
        textView.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.button), withText("Count"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(android.R.id.text1), withText("Gorillas"),
                        withParent(allOf(withId(R.id.listView),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("Gorillas")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.countView), withText("1"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView3.check(matches(withText("1")));

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.go_to_plan_btn), withText("Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.text1), withText("entrance_exit_gate"),
                        withParent(allOf(withId(R.id.selected_exhibits),
                                withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        textView4.check(matches(withText("entrance_exit_gate")));

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.directions_btn), withText("Directions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.next_btn), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.directionsView), withText("The shortest path from entrance_exit_gate to lions is: \n\n1 Walk 10.0 meters along Entrance Way from Entrance and Exit Gate to Entrance Plaza\n\n2 Walk 200.0 meters along Africa Rocks Street from Entrance Plaza to Gorillas\n\n"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.next_btn), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.directionsView), withText("At final exhibit!"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView6.check(matches(withText("At final exhibit!")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
