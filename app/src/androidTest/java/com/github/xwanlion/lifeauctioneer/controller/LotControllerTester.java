package com.github.xwanlion.lifeauctioneer.controller;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.xwanlion.lifeauctioneer.model.app.BidLog;
import com.github.xwanlion.lifeauctioneer.model.app.BidderExpectingLot;
import com.github.xwanlion.lifeauctioneer.model.app.BidderMoney;
import com.github.xwanlion.lifeauctioneer.service.AuctionsServiceImpl;
import com.github.xwanlion.lifeauctioneer.service.BidLogServiceImpl;
import com.github.xwanlion.lifeauctioneer.service.BidderMoneyServiceImpl;
import com.github.xwanlion.lifeauctioneer.service.IAuctionsService;
import com.github.xwanlion.lifeauctioneer.service.IBidLogService;
import com.github.xwanlion.lifeauctioneer.service.IBidderMoneyService;
import com.github.xwanlion.lifeauctioneer.service.ILotService;
import com.github.xwanlion.lifeauctioneer.service.LotServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class LotControllerTester {
    private LotController lotController = new LotController();
    private IBidLogService bidLogService = new BidLogServiceImpl();
    private IBidderMoneyService bidderMoneyService = new BidderMoneyServiceImpl();
    private ILotService lotService = new LotServiceImpl();
    private IAuctionsService activityService = new AuctionsServiceImpl();
    private int currentLotId = 0;

    @Test
    public void testNewLot() {
        int activityId = 1;
        String name = "goods";
        long startingPrice = 1500;
        long increment = 100;
        String desc = "description";

        Map<String, Object> rMap = lotController.newLot(activityId, name, startingPrice, increment, desc, null);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

    }

    @Test
    public void testGetLot() {
        int activityId = 1;
        Map<String, Object> rMap = lotController.listLot(activityId);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        assertEquals(true, (list != null && list.size() > 0));

        Map<String, Object> map = list.get(0);
        int id = (Integer) map.get("id");
        assertEquals(true, (id > 0));

        Map<String, Object> rDelMap = lotController.getLot(id);
        boolean dSuccess = ( rDelMap != null && ((Integer) rDelMap.get("code") == 0));
        assertEquals(true, dSuccess);

    }

    @Test
    public void testListLot() {
        int activityId = 1;
        Map<String, Object> rMap = lotController.listLot(activityId);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

    }

    @Test
    public void testDelLot() {
        int activityId = 1;
        Map<String, Object> rMap = lotController.listLot(activityId);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        assertEquals(true, (list != null && list.size() > 0));

        Map<String, Object> map = list.get(0);
        int id = (Integer) map.get("id");
        assertEquals(true, (id > 0));

        Map<String, Object> rDelMap = lotController.delLot(id);
        boolean dSuccess = ( rDelMap != null && ((Integer) rDelMap.get("code") == 0));
        assertEquals(true, dSuccess);

    }

    @Test
    public void testPointAuctionLot() {
        int activityId = 1;
        Map<String, Object> rMap = lotController.listLot(activityId);
        boolean success = (rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        assertEquals(true, (list != null && list.size() > 0));

        Map<String, Object> map = list.get(0);
        int id = (Integer) map.get("id");
        assertEquals(true, (id > 0));

        String sessionId = UUID.randomUUID().toString();
        Map<String, Object> rDelMap = lotController.pointAuctionLot(id, null);
        boolean dSuccess = ( rDelMap != null && ((Integer) rDelMap.get("code") == 0));
        assertEquals(true, dSuccess);

        currentLotId = id;

    }

    @Test
    public void testSaveExpectingLot() {
        int activityId = 1;
        int bidderId = 2;

        Map<String, Object> rMap = lotController.listLot(activityId);
        boolean success = (rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        assertEquals(true, (list != null && list.size() > 0));

        Map<String, Object> map = list.get(0);
        int id = (Integer) map.get("id");
        assertEquals(true, (id > 0));

        Map<String, Object> rDelMap = lotController.saveExpectingLot(activityId, bidderId, id, 3000);
        boolean dSuccess = ( rDelMap != null && ((Integer) rDelMap.get("code") == 0));
        assertEquals(true, dSuccess);

    }

    @Test
    public void testListExpectingLot() {
        int activityId = 1;
        int bidderId = 2;

        Map<String, Object> rMap = lotController.listExpectingLot(activityId);
        boolean success = (rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

    }

    @Test
    public void testListExpectingLot2() {
        int activityId = 1;
        int bidderId = 2;

        Map<String, Object> rMap = lotController.listExpectingLot(activityId, bidderId);
        boolean success = (rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

    }

    @Test
    public void testBiding() {
        long price = 1L;
        int bidderId = 2;

        String sessionId = UUID.randomUUID().toString();
        Map<String, Object> rMap = lotController.bid(bidderId, price, null);
        boolean success = (rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<BidLog> list = bidLogService.listLog(1, bidderId);
        assertEquals(true, (list != null && list.size() > 0));

        BidLog bidLog = list.get(0);
        assertEquals(true, (bidLog != null && bidLog.getPrice() == price));

    }

    // remember to uninstalled the app before testing.
    @Test
    public void testSold() {
        int activityId = 1;
        long price = 1L;
        int bidderId = 2;

        activityService.saveActivity(1, 5000L);
        bidderMoneyService.createBidderMoney(activityId, bidderId);
        BidderMoney bidderMoney =  bidderMoneyService.getBidderMoney(activityId, bidderId);
        assertEquals(true, bidderMoney != null);

        testNewLot();
        testSaveExpectingLot();

        testPointAuctionLot();
        testBiding();

        Map<String, Object> rMap = lotController.sold(bidderId, price);
        boolean success = (rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        BidderMoney bidderMoney2 =  bidderMoneyService.getBidderMoney(activityId, bidderId);
        assertEquals(true, bidderMoney2 != null);
        assertEquals(true, (bidderMoney.getAmount() > bidderMoney2.getAmount()));

        BidderExpectingLot expectingLot = lotService.getExpectingLot(activityId, bidderId, currentLotId);
        assertEquals(true, (expectingLot != null));
        assertEquals(true, price == expectingLot.getPurchasePrice());

    }

    // remember to uninstall app before testing
    @Test
    public void testAll() {
        testNewLot();
        testGetLot();

        // listLot has test on function testPointAuctionLot
        // testListLot();

        testPointAuctionLot();
        testSaveExpectingLot();
        testListExpectingLot();
        testListExpectingLot2();

        //
        testBiding();
        testSold();

        // must put on last
        testDelLot();

    }

}
