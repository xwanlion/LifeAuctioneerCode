package com.github.xwanlion.lifeauctioneer.view;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LotAddingFragmentTest extends MainActivityImageViewTester {
    private static Auctions newAuctions = new Auctions();
    private static Auctions oldAuctions = new Auctions();

    {
        oldAuctions.setId(4);
        oldAuctions.setAuctioneerId(1);
        oldAuctions.setAuctioneerName("auct");
        oldAuctions.setDate(1618971140205L);
        oldAuctions.setState(-1);
        oldAuctions.setMoneyCreationWay(1);
        oldAuctions.setMoney(6000);
        oldAuctions.setAmountPerAge(101);
    }

    public LotAddingFragmentTest() {
        super();
    }

    @Test
    public void lotAdding_oldAuction_defaultToNull_true() {
        this.launchFragment(R.id.auctionLotAddingFragment, oldAuctions);

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_auction_item_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_auction_item_start_price))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_auction_item_increment))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_auction_item_desc))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));

        super.assertImageLoaded(R.id.img_new_auction_item);

    }

    @Test
    public void lotAdding_oldAuction_lotNameCanNotBeNull_true() {
        super.launchFragment(R.id.auctionLotAddingFragment, oldAuctions);

        super.loadImage(R.id.img_new_auction_item);
        super.assertImageLoaded(R.id.img_new_auction_item);

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_auction_item_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_auction_item_start_price))
                .perform(ViewActions.typeText("1500"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_auction_item_increment))
                .perform(ViewActions.typeText("100"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_submit_lot_item))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_auction_item_name))
                .check(ViewAssertions.matches(ViewMatchers.withHint(R.string.hint_new_auction_item_name)));

    }

    @Test
    public void lotAdding_oldAuction_startPriceCanNotBeNull_true() {
        super.launchFragment(R.id.auctionLotAddingFragment, oldAuctions);

        super.loadImage(R.id.img_new_auction_item);
        super.assertImageLoaded(R.id.img_new_auction_item);

        inputText(R.id.txt_new_auction_item_name, "lot");

        inputText(R.id.txt_new_auction_item_start_price, "");

        inputText(R.id.txt_new_auction_item_increment, "100");

        clickView(R.id.btn_submit_lot_item);

        assertWithHit(R.id.txt_new_auction_item_name, R.string.hint_new_auction_item_name);

    }

    @Test
    public void lotAdding_oldAuction_incrementCanNotBeNull_true() {
        super.launchFragment(R.id.auctionLotAddingFragment, oldAuctions);

        super.loadImage(R.id.img_new_auction_item);
        super.assertImageLoaded(R.id.img_new_auction_item);

        inputText(R.id.txt_new_auction_item_name, "lot");
        inputText(R.id.txt_new_auction_item_start_price, "1500");
        inputText(R.id.txt_new_auction_item_increment, "");
        clickView(R.id.btn_submit_lot_item);

        assertWithHit(R.id.txt_new_auction_item_name, R.string.hint_new_auction_item_name);

    }

    @Test
    public void lotAdding_newAuction_saveFailed_true() {
        super.launchFragment(R.id.auctionLotAddingFragment, newAuctions);

        super.loadImage(R.id.img_new_auction_item);
        super.assertImageLoaded(R.id.img_new_auction_item);

        inputText(R.id.txt_new_auction_item_name, "lot");
        inputText(R.id.txt_new_auction_item_start_price, "1500");
        inputText(R.id.txt_new_auction_item_increment, "100");
        clickView(R.id.btn_submit_lot_item);

        assertWithHit(R.id.txt_new_auction_item_name, R.string.hint_new_auction_item_name);

    }

    @Test
    public void lotAdding_oldAuction_saveSuccess_true() {
        super.launchFragment(R.id.auctionLotAddingFragment, oldAuctions);

        super.loadImage(R.id.img_new_auction_item);
        super.assertImageLoaded(R.id.img_new_auction_item);

        inputText(R.id.txt_new_auction_item_name, "lot");
        inputText(R.id.txt_new_auction_item_start_price, "1500");
        inputText(R.id.txt_new_auction_item_increment, "100");
        clickView(R.id.btn_submit_lot_item);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertWithContentDescription(R.id.btn_lot_list_next_step, R.string.tips_activity_next_step);

    }

}