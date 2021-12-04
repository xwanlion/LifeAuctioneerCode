package com.github.xwanlion.lifeauctioneer.controller;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class BidderMoneyControllerTester {
    private BidderMoneyController bidderMoneyController = new BidderMoneyController();
    private AuctionsController auctionsController = new AuctionsController();

    public void testCreateBidderMoney(int activityId, int bidderId) {
        Map<String, Object> rMap = bidderMoneyController.createBidderMoney(activityId, bidderId);
        boolean success = (rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

    }

    public void testCreateBidderMoney(int activityId, int bidderId, int age) {
        Map<String, Object> rMap = bidderMoneyController.createBidderMoney(bidderId, age);
        boolean success = (rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

    }

    public Map<String, Object> testGetBidderMoney(int id) {
        Map<String, Object> bidderMoneyMap = bidderMoneyController.getBidderMoney(id);
        assertEquals(true, (bidderMoneyMap != null && ((Integer) bidderMoneyMap.get("code") == 0)));

        Object obj = bidderMoneyMap.get("data");
        if (obj instanceof String) return null;

        Map<String, Object> bidderMoneyDataMap = (Map<String, Object>) obj;
        return bidderMoneyDataMap;

    }

    public Map<String, Object> testGetBidderMoney(int activityId, int bidderId) {
        Map<String, Object> bidderMoneyMap = bidderMoneyController.getBidderMoney(activityId, bidderId);
        assertEquals(true, (bidderMoneyMap != null && ((Integer) bidderMoneyMap.get("code") == 0)));

        Map<String, Object> bidderMoneyDataMap = (Map<String, Object>) bidderMoneyMap.get("data");
        return bidderMoneyDataMap;

    }

    public List<Map<String, Object>> testListBidderMoney(int activityId) {
        Map<String, Object> rMap = bidderMoneyController.listBidderMoney(activityId);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        return list;

    }

    public List<Map<String, Object>> testListBidderMoney(int activityId, int pageIndex, int recordsPerPage) {
        Map<String, Object> rMap = bidderMoneyController.listBidderMoney(activityId, pageIndex, recordsPerPage);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        return list;

    }

    public void testDelBidderMoney(int id) {
        Map<String, Object> rMap = bidderMoneyController.delBidderMoney(id);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

    }


    @Test
    public void testBidderMoneyAll() {
        int activityId = 1;
        int bidderId = 2;
        int bidderId2 = 22;
        int age = 20;

        auctionsController.newActivity(1, 6000L);
        auctionsController.newActivity(1, 100);

        testCreateBidderMoney(activityId, bidderId);
        testCreateBidderMoney(activityId + 1, bidderId2, age);

        Map<String, Object> map1 = testGetBidderMoney(activityId, bidderId);
        int bidderMoneyId = (Integer) map1.get("id");
        assertEquals(true, (bidderMoneyId > 0));

        Map<String, Object> map = testGetBidderMoney(bidderMoneyId);
        assertEquals(true, (map != null));

        List<Map<String, Object>> list = testListBidderMoney(activityId);
        assertEquals(true, (list != null && list.size() > 0));

        testDelBidderMoney(bidderMoneyId);
        Map<String, Object> delObjectMap = testGetBidderMoney(bidderMoneyId);
        assertEquals(true, delObjectMap == null);

    }

}
