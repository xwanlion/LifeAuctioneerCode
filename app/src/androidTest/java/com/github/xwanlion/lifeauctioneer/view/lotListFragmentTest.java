package com.github.xwanlion.lifeauctioneer.view;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class lotListFragmentTest extends ABaseTester {
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
    public void lotList_oldAuction_listAllLotIfEdit_true() {
        launchFragment(mActivityRule, R.id.auctionLotListFragment, oldAuctions);

        List<Lot> lotList = BeanManager.lotService.listLot(oldAuctions.getId(), 0, 9999);
        int count = 0;
        if (lotList != null) count = lotList.size();

        Logger.i("count:" + count);

        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.rcyv_auction_lot_list);
        assertEquals(count, recyclerView.getItemDecorationCount());

    }

    @Test
    public void lotList_newAuction_listNoLot_true() {
        launchFragment(mActivityRule, R.id.auctionLotListFragment, newAuctions);

        int count = 0;
        RecyclerView recyclerView = mActivityRule.getActivity().findViewById(R.id.rcyv_auction_lot_list);
        assertEquals(count, recyclerView.getItemDecorationCount());

    }

    //FIXME:test fail
    @Test
    public void lotList_oldAuction_addLot_true() {
        // see LotListFragmentTest2.java
    }

    @Test
    public void lotList_oldAuction_deleteLot_true() {
        launchFragment(mActivityRule, R.id.auctionLotListFragment, oldAuctions);

        // equals to 3 mean that has recyclerView has 2 item and a new Button
        assertEquals(true, (getRecyclerViewCount(mActivityRule, R.id.rcyv_auction_lot_list) == 3));

        Espresso.onView(ViewMatchers.withId(R.id.rcyv_auction_lot_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClickRecyclerChildViewWithId(R.id.lyo_auction_lot_list_item_panel)));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.btn_auction_lot_list_item_del), ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(ViewMatchers.withContentDescription(R.string.btn_auction_lot_list_item_del_tips)));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.btn_auction_lot_list_item_del), ViewMatchers.isDisplayed()))
                .perform(ViewActions.click());

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.btn_auction_lot_list_item_del)))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));

        // equals to 2 mean that has recyclerView has 1 item and a new Button
        assertEquals(true, (getRecyclerViewCount(mActivityRule, R.id.rcyv_auction_lot_list) == 2));

    }

    @Test
    public void lotList_oldAuction_showDeleteButtonThenHidden_true() {
        launchFragment(mActivityRule, R.id.auctionLotListFragment, oldAuctions);

        int count = getRecyclerViewCount(mActivityRule, R.id.rcyv_auction_lot_list);
        assertEquals(true, (count == 2));

        Espresso.onView(ViewMatchers.withId(R.id.rcyv_auction_lot_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClickRecyclerChildViewWithId(R.id.lyo_auction_lot_list_item_panel)));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.btn_auction_lot_list_item_del), ViewMatchers.isDisplayed()))
                .check(ViewAssertions.matches(ViewMatchers.withContentDescription(R.string.btn_auction_lot_list_item_del_tips)));

        Espresso.onView(ViewMatchers.withId(R.id.rcyv_auction_lot_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickRecyclerChildViewWithId(R.id.lyo_auction_lot_list_item_panel)));

        Espresso.onView(ViewMatchers.withId(R.id.btn_auction_lot_list_item_del))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())));

    }

}