package com.github.xwanlion.lifeauctioneer.controller;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.yanzhenjie.andserver.http.HttpRequest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UserControllerTester {
    private BidderController bidderController = new BidderController();
    private AuctioneerController auctioneerController = new AuctioneerController();
    private UserController userController = new UserController();


    @Test
    public void testUserLogin() {
        String bidder = "bidder1";
        String auctioneer = "auctioneer1";
        String password = "password";

        bidderController.newBidder(bidder, password);
        auctioneerController.newAuctioneer(auctioneer, password);

        Map<String, Object> bidderMap = userController.userLogin(bidder, password, null, null);
        assertEquals(bidderMap.get("code"), 0);

        Map<String, Object> auctioneerMap = userController.userLogin(auctioneer, password, null, null);
        assertEquals(auctioneerMap.get("code"), 0);

        Map<String, Object> otherMap = userController.userLogin("aaa", "bbb", null, null);
        assertEquals(false, ((Integer) otherMap.get("code") == 0));

    }
}
