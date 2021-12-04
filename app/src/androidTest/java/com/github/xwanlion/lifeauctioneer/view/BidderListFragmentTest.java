package com.github.xwanlion.lifeauctioneer.view;

import android.content.Context;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.github.xwanlion.lifeauctioneer.model.app.MoneyCreationWay;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.github.xwanlion.lifeauctioneer.BeanManager.bidderService;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class BidderListFragmentTest extends ABaseTester {
    private static Auctions newAuctions = new Auctions();
    private static Auctions oldAuctions = new Auctions();

    {
        oldAuctions.setId(4);
        oldAuctions.setAuctioneerId(1);
        oldAuctions.setAuctioneerName("auct");
        oldAuctions.setDate(1618971140205L);
        oldAuctions.setDate(-1);
        oldAuctions.setMoneyCreationWay(1);
        oldAuctions.setMoney(6000);
        oldAuctions.setAmountPerAge(101);
    }

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void bidderList_oldAuction_listAllBidderIfEdit_true() {
        launchFragment(mActivityRule, R.id.bidderListFragment, oldAuctions);

        List<Bidder> bidderList = BeanManager.bidderService.listBidder(oldAuctions.getId(), 0, 9999);
        int count = 0;
        if (bidderList != null) count = bidderList.size();

        Logger.i("count:" + count);

        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.rcyv_bidder_list);
        assertEquals(count, recyclerView.getItemDecorationCount());

    }

    @Test
    public void bidderList_newAuction_listNoBidder_true() {
        launchFragment(mActivityRule, R.id.bidderListFragment, newAuctions);

        List<Bidder> bidderList = BeanManager.bidderService.listBidder(oldAuctions.getId(), 0, 9999);
        int count = 0;
        if (bidderList != null) count = bidderList.size();

        Logger.i("count:" + count);

        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.rcyv_bidder_list);
        assertEquals(count, recyclerView.getItemDecorationCount());

    }

    @Test
    public void bidderList_oldAuction_addBidder_true() {
        launchFragment(mActivityRule, R.id.bidderListFragment, oldAuctions);

        Espresso.onView(ViewMatchers.withId(R.id.btn_recycle_view_add_item))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.btn_submit_bidder))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        String bidderName = "bidder3";
        Espresso.onView(ViewMatchers.withId(R.id.txt_new_bidder_name))
                .perform(ViewActions.typeText(bidderName), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_bidder_password))
                .perform(ViewActions.typeText("psw"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_submit_bidder))
                .perform(ViewActions.click());

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.btn_bidder_list_item_next_step))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(Matchers.allOf( ViewMatchers.withId(R.id.lbl_bidder_list_item_start_price), ViewMatchers.withText(bidderName)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

    @Test
    public void bidderList_oldAuction_deleteBidder_true() {
        launchFragment(mActivityRule, R.id.bidderListFragment, oldAuctions);

        // equals to 3 mean that has recyclerView has 2 item and a new Button
        assertEquals(true, (getRecyclerViewCount(mActivityRule, R.id.rcyv_bidder_list) == 3));

        Espresso.onView(ViewMatchers.withId(R.id.rcyv_bidder_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, longClickRecyclerChildViewWithId(R.id.lyo_bidder_list_item_panel)));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.btn_bidder_list_item_del), ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(ViewMatchers.withContentDescription(R.string.btn_bidder_list_item_del_tips)));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.btn_bidder_list_item_del), ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.btn_bidder_list_item_del)))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));

        // equals to 2 mean that has recyclerView has 1 item and a new Button
        assertEquals(true, (getRecyclerViewCount(mActivityRule, R.id.rcyv_bidder_list) == 2));

    }

    @Test
    public void bidderList_oldAuction_showDeleteButtonThenHidden_true() {
        launchFragment(mActivityRule, R.id.bidderListFragment, oldAuctions);

        int count = getRecyclerViewCount(mActivityRule, R.id.rcyv_bidder_list);
        assertEquals(true, (count == 2));

        Espresso.onView(ViewMatchers.withId(R.id.rcyv_bidder_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClickRecyclerChildViewWithId(R.id.lyo_bidder_list_item_panel)));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.btn_bidder_list_item_del), ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(ViewMatchers.withContentDescription(R.string.btn_bidder_list_item_del_tips)));

        Espresso.onView(ViewMatchers.withId(R.id.rcyv_bidder_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickRecyclerChildViewWithId(R.id.lyo_bidder_list_item_panel)));

        Espresso.onView(ViewMatchers.withId(R.id.btn_bidder_list_item_del))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));

    }

}