package com.github.xwanlion.lifeauctioneer.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavDeepLinkBuilder;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;

public class ABaseTester {
    public final int getRecyclerViewCount(ActivityTestRule rule, int id){
        RecyclerView recyclerView = (RecyclerView) rule.getActivity().findViewById(id);
        return recyclerView.getAdapter().getItemCount();
    }

    public final ViewAction clickRecyclerChildViewWithId(int childId) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specific id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View childView = view.findViewById(childId);
                childView.performClick();
            }
        };
    }

    public final ViewAction longClickRecyclerChildViewWithId(int childId) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specific id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View childView = view.findViewById(childId);
                childView.performLongClick();
            }
        };
    }


    protected synchronized void  launchFragment(IntentsTestRule rule, int destinationId, Auctions auctions) {
        //Intents.init();
        Bundle bundle = new Bundle();
        bundle.putString(System.KEY_AUCTION, auctions.toJson());

//        Intent launchFragmentIntent = buildLaunchFragmentIntent(destinationId, bundle);
//        rule.launchActivity(launchFragmentIntent);
        this.launchFragment(rule, destinationId, bundle);

    }

    protected void launchFragment(ActivityTestRule rule, int destinationId, Auctions auctions) {
        Bundle bundle = new Bundle();
        bundle.putString(System.KEY_AUCTION, auctions.toJson());

//        Intent launchFragmentIntent = buildLaunchFragmentIntent(destinationId, bundle);
//        rule.launchActivity(launchFragmentIntent);
        this.launchFragment(rule, destinationId, bundle);

    }

    protected void launchFragment(ActivityTestRule rule, int destinationId, Auctions auctions, Auctioneer auctioneer) {
        Bundle bundle = new Bundle();
        bundle.putString(System.KEY_AUCTION, auctions.toJson());
        bundle.putString(System.KEY_AUCTIONEER, auctioneer.toJson());

//        Intent launchFragmentIntent = buildLaunchFragmentIntent(destinationId, bundle);
//        rule.launchActivity(launchFragmentIntent);
        this.launchFragment(rule, destinationId, bundle);
    }

    protected void launchFragment(ActivityTestRule rule, int destinationId, Bundle bundle) {
        Intents.release();
        Intent launchFragmentIntent = buildLaunchFragmentIntent(destinationId, bundle);
        rule.launchActivity(launchFragmentIntent);
    }

    protected void inputText(int viewId, String text) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard());

        if (text == null || text.length() == 0) return;

        Espresso.onView(ViewMatchers.withId(viewId))
                .perform(ViewActions.typeText(text), ViewActions.closeSoftKeyboard());
    }

    protected void clickView(int viewId) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .perform(ViewActions.click());
    }

    protected void assertWithHit(int viewId, String text) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(ViewMatchers.withHint(text)));
    }
    protected void assertWithHit(int viewId, int resourceId) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(ViewMatchers.withHint(resourceId)));
    }

    protected void assertWithContentDescription(int viewId, int resourceId) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(ViewMatchers.withContentDescription(resourceId)));
    }

    protected void assertWithText(int viewId, int resourceId) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(ViewMatchers.withText(resourceId)));
    }

    protected void assertWithText(int viewId, String text) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(ViewMatchers.withText(text)));
    }

    protected void assertIsDisplayed(int viewId) {
        Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    protected String getString(Activity activity, int resourceId) {
        return activity.getString(resourceId);
    }

    private Intent buildLaunchFragmentIntent(int destinationId, Bundle argBundle) {
        return new NavDeepLinkBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext())
                .setGraph(R.navigation.navhost)
                .setComponentName(ComponentName.unflattenFromString(MainActivity.class.getName()))
                .setDestination(destinationId)
                .setArguments(argBundle)
                .createTaskStackBuilder().getIntent(0);
    }


}
