package com.github.xwanlion.lifeauctioneer.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.MoneyCreationWay;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ReviewFragmentTest extends MainActivityImageViewTester {
    private static Auctions oldAuctions = new Auctions();
    private static int bidderNum = 4;
    private static int lotNum = 15;

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

    public ReviewFragmentTest() {
        super();
    }

    @Test
    public void lotAdding_oldAuction_showingAll_true() {
        this.launchFragment(R.id.reviewFragment, oldAuctions, bidderNum, lotNum);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertWithText(R.id.txt_auction_lot_items, lotNum + "");
        assertWithText(R.id.txt_auction_bidders, bidderNum + "");
        if (oldAuctions.getMoneyCreationWay() == MoneyCreationWay.FIX_AMOUNT) {
            String patton = getString(R.string.txt_activity_list_item_money);
            String text = String.format(patton, oldAuctions.getMoney());
            assertWithText(R.id.txt_auction_money, text);
        } else {
            String patton = getString(R.string.txt_activity_list_item_money_per_age);
            String text = String.format(patton, oldAuctions.getAmountPerAge());
            assertWithText(R.id.txt_auction_money, text);
        }

        assertWithText(R.id.txt_auction_host, oldAuctions.getAuctioneerName() + "");
    }

    @Test
    public void lotAdding_toHome_true() {
        this.launchFragment(R.id.reviewFragment, oldAuctions, bidderNum, lotNum);

        clickView(R.id.btn_back_home);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertIsDisplayed(R.id.rcyv_activity_list);
    }

    protected synchronized void  launchFragment(int destinationId, Auctions auctions, int bidderNum, int lotNum) {
        Bundle bundle = new Bundle();
        bundle.putString(System.KEY_AUCTION, auctions.toJson());
        bundle.putInt(System.KEY_BIDDER_COUNT, bidderNum);
        bundle.putInt(System.KEY_LOT_COUNT, lotNum);

        super.launchFragment(destinationId, bundle);

    }


}