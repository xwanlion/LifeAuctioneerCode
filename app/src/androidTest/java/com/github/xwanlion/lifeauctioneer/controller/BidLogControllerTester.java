package com.github.xwanlion.lifeauctioneer.controller;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class BidLogControllerTester {
    private BidLogController lotController = new BidLogController();

    public void testNewBidLog(int activityId, int bidderId, int lotId) {
        long price = 3200;
        Map<String, Object> rMap = lotController.newBidLog(activityId, bidderId, lotId, price);
        boolean success = (rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

    }

    public Map<String, Object> testGetBidLog(int activityId, int bidderId, int lotId) {
        Map<String, Object> bidLogMap = lotController.getBidLog(activityId, bidderId, lotId);
        assertEquals(true, (bidLogMap != null && ((Integer) bidLogMap.get("code") == 0)));

        Map<String, Object> bidLogDataMap = (Map<String, Object>) bidLogMap.get("data");
        int id = (Integer) bidLogDataMap.get("id");
        assertEquals(true, id > 0);

        Map<String, Object> rgMap = lotController.getBidLog(id);
        boolean dSuccess = ( rgMap != null && ((Integer) rgMap.get("code") == 0));
        assertEquals(true, dSuccess);

        return (Map<String, Object>) rgMap.get("data");

    }

    public List<Map<String, Object>> testListBidLog(int activityId, int bidderId) {
        Map<String, Object> rMap = lotController.listBidLog(activityId, bidderId);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");

        Map<String, Object> rrMap = lotController.listBidLog(activityId, 0, 20);
        boolean xSuccess = (rrMap != null && ((Integer) rrMap.get("code") == 0));
        assertEquals(true, xSuccess);

        return list;

    }

    public void testDelBidLog(int id) {
        Map<String, Object> rMap = lotController.delBidLog(id);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

    }


    @Test
    public void testBidLogAll() {
        int activityId = 1;
        int bidderId = 2;
        int lotId = 2;

        testNewBidLog(activityId, bidderId, lotId);
        testListBidLog(activityId, bidderId);
        Map<String, Object> map = testGetBidLog(activityId, bidderId, lotId);
        assertEquals(true, (map != null));

        int id = (Integer) map.get("id");
        testDelBidLog(id);

    }

}
