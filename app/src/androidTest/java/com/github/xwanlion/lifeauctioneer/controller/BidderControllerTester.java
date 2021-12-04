package com.github.xwanlion.lifeauctioneer.controller;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class BidderControllerTester {
    private BidderController controller = new BidderController();

    @Test
    public void testNewBidder() {
        controller.newBidder("username1", "password");
        Map<String, Object> map = controller.listBidder();
        assertEquals(map.get("code"), 0);
    }

    @Test
    public void testDelBidder() {
        testNewBidder();

        Map<String, Object> map = controller.listBidder();
        assertEquals(map.get("code"), 0);

        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("data");
        assertEquals(true, list != null);

        int id = (Integer) list.get(0).get("id");
        assertEquals(true, id > 0);

        Map<String, Object> delMap = controller.delBidder(id);
        assertEquals(true, delMap != null && ((Integer) delMap.get("code") == 0));

    }
}
