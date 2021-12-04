package com.github.xwanlion.lifeauctioneer.view;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.github.xwanlion.lifeauctioneer.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AuctionsListFragmentTest extends ABaseTester {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(MainActivity.class);;

    @Test
    public void auctionsList_newButton_toAuctioneerSetting_true() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_recycle_view_add_item))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.txt_auction_host_man))
                .check(ViewAssertions.matches(ViewMatchers.withHint(R.string.hits_auction_host_man)));
    }

    @Test
    public void auctionsList_startButton_longPress_showDeleteAndCopy_true() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_to_startup_activity_item))
                .perform(ViewActions.longClick());
        Espresso.onView(ViewMatchers.withId(R.id.btn_del_activity_item))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void auctionsList_showDeleteButtonAndCopyButton_thenHideIt_true() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_del_activity_item))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));

        this.auctionsList_startButton_longPress_showDeleteAndCopy_true();

        Espresso.onView(ViewMatchers.withId(R.id.txt_activity_item_auctioneer))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.btn_del_activity_item))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));
    }

    @Test
    public void auctionsList_copyItem_true() {
        // equals to 2 mean that has recyclerView has one item and a new Button
        assertEquals(true, (getRecyclerViewCount(mActivityRule, R.id.rcyv_activity_list) == 2));

        this.auctionsList_startButton_longPress_showDeleteAndCopy_true();

        Espresso.onView(ViewMatchers.withId(R.id.btn_copy_activity_item))
                .perform(ViewActions.click());

        // equals to 3 mean that has recyclerView has 2 item and a new Button
        assertEquals(true, (getRecyclerViewCount(mActivityRule, R.id.rcyv_activity_list) == 3));
    }

    @Test
    public void auctionsList_deleteItem_true() {
        // equals to 3 mean that has recyclerView has 2 item and a new Button
        assertEquals(true, (getRecyclerViewCount(mActivityRule, R.id.rcyv_activity_list) == 3));

        Espresso.onView(ViewMatchers.withId(R.id.rcyv_activity_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, longClickRecyclerChildViewWithId(R.id.btn_to_startup_activity_item)));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.btn_del_activity_item), ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(ViewMatchers.withContentDescription(R.string.btn_del_activity_list_item)));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.btn_del_activity_item), ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.btn_del_activity_item)))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));

        // equals to 2 mean that has recyclerView has 1 item and a new Button
        assertEquals(true, (getRecyclerViewCount(mActivityRule, R.id.rcyv_activity_list) == 2));

    }


}
