package com.github.xwanlion.lifeauctioneer.controller;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.xwanlion.lifeauctioneer.conf.ActivitySate;
import com.github.xwanlion.lifeauctioneer.model.app.MoneyCreationWay;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AuctionsControllerTester {
    private AuctionsController lotController = new AuctionsController();


    @Test
    public void testNewActivity() {
        int auctioneerId = 1;
        int moneyCreationWay = 0;

        Map<String, Object> rMap = lotController.newActivity(auctioneerId, moneyCreationWay);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

    }

    @Test
    public void testUpdateActivity() {
        int id = 1;
        int auctioneerId = 2;
        int moneyCreationWay = 1;

        Map<String, Object> rMap = lotController.updateActivity(id, auctioneerId, moneyCreationWay);
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

    }

    @Test
    public void testGetActivity() {
        Map<String, Object> rMap = lotController.listActivity();
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        assertEquals(true, (list != null && list.size() > 0));

        Logger.d(JsonUtils.toJsonString(list));

        Map<String, Object> map = list.get(0);
        int id = (Integer) map.get("id");
        assertEquals(true, (id > 0));

        Map<String, Object> rgMap = lotController.getActivity(id);
        boolean dSuccess = ( rgMap != null && ((Integer) rgMap.get("code") == 0));
        assertEquals(true, dSuccess);

        Logger.d(JsonUtils.toJsonString(rgMap));
        Map<String, Object> dataMap = (Map<String, Object>) rgMap.get("data");
        assertEquals(true, (dataMap != null));

        int auctioneerId = (Integer) dataMap.get("auctioneerId");
        int moneyCreationWay = (Integer) dataMap.get("moneyCreationWay");
        assertEquals(true, (auctioneerId == 2));
        assertEquals(true, (moneyCreationWay == 1));

    }

    public List<Map<String, Object>> testListActivity() {
        Map<String, Object> rMap = lotController.listActivity();
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        Logger.d(JsonUtils.toJsonString(rMap));
        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        return list;

    }

    @Test
    public void testDelActivity() {
        Map<String, Object> rMap = lotController.listActivity();
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        assertEquals(true, (list != null && list.size() > 0));

        Map<String, Object> map = list.get(0);
        int id = (Integer) map.get("id");
        assertEquals(true, (id > 0));

        Map<String, Object> rDelMap = lotController.delActivity(id);
        boolean dSuccess = ( rDelMap != null && ((Integer) rDelMap.get("code") == 0));
        assertEquals(true, dSuccess);

        Logger.d(JsonUtils.toJsonString(rDelMap));

    }

    @Test
    public void testCopyActivity() {
        List<Map<String, Object>> list = this.testListActivity();
        assertEquals(true, (list != null && list.size() > 0));

        int orgSize = list.size();

        Logger.d(JsonUtils.toJsonString(list));

        Map<String, Object> map = list.get(0);
        int id = (Integer) map.get("id");
        assertEquals(true, (id > 0));

        Map<String, Object> rrMap = lotController.copyActivity(id);
        boolean xSuccess = ( rrMap != null && ((Integer) rrMap.get("code") == 0));
        assertEquals(true, xSuccess);

        List<Map<String, Object>> xList = this.testListActivity();
        assertEquals(true, (xList.size() > orgSize));

        Logger.d(JsonUtils.toJsonString(xList));

    }

    @Test
    public void testStartActivity() {
        Map<String, Object> rMap = lotController.listActivity();
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        assertEquals(true, (list != null && list.size() > 0));

        Logger.d("begin to start up");
        Logger.d(JsonUtils.toJsonString(list));

        Map<String, Object> map = list.get(0);
        int id = (Integer) map.get("id");
        assertEquals(true, (id > 0));

        Map<String, Object> rrMap = lotController.startActivity(id);
        boolean xSuccess = ( rrMap != null && ((Integer) rrMap.get("code") == 0));
        assertEquals(true, xSuccess);

        Map<String, Object> getMap = lotController.getActivity(id);
        assertEquals(true, ( rMap != null && ((Integer) rMap.get("code") == 0)));

        Map<String, Object> dataMap = (Map<String, Object>) getMap.get("data");
        int state = (Integer) dataMap.get("state");
        assertEquals(true, (state == ActivitySate.READY));
        Logger.d("start up success");

    }

    @Test
    public void testSopActivity() {
        Map<String, Object> rMap = lotController.listActivity();
        boolean success = ( rMap != null && ((Integer) rMap.get("code") == 0));
        assertEquals(true, success);

        List<Map<String, Object>> list = (List<Map<String, Object>>) rMap.get("data");
        assertEquals(true, (list != null && list.size() > 0));

        Logger.d("begin to stop");
        Logger.d(JsonUtils.toJsonString(list));

        Map<String, Object> map = list.get(0);
        int id = (Integer) map.get("id");
        assertEquals(true, (id > 0));

        Map<String, Object> rrMap = lotController.stopActivity(id);
        boolean xSuccess = (rrMap != null && ((Integer) rrMap.get("code") == 0));
        assertEquals(true, xSuccess);

        Logger.d("stop success");

        Map<String, Object> getMap = lotController.getActivity(id);
        assertEquals(true, ( rMap != null && ((Integer) rMap.get("code") == 0)));

        Map<String, Object> dataMap = (Map<String, Object>) getMap.get("data");
        int state = (Integer) dataMap.get("state");
        assertEquals(true, (state == ActivitySate.STOPPING));

    }

    @Test
    public void testChangeState() {
        List<Map<String, Object>> list = this.testListActivity();
        assertEquals(true, (list != null && list.size() > 0));

        Map<String, Object> map = list.get(0);
        int id = (Integer) map.get("id");
        assertEquals(true, (id > 0));

        Map<String, Object> rrMap = lotController.changeState(id, ActivitySate.AUCTION);
        boolean xSuccess = (rrMap != null && ((Integer) rrMap.get("code") == 0));
        assertEquals(true, xSuccess);

        Map<String, Object> getMap = lotController.getActivity(id);
        assertEquals(true, ( getMap != null && ((Integer) getMap.get("code") == 0)));

        Map<String, Object> dataMap = (Map<String, Object>) getMap.get("data");
        int state = (Integer) dataMap.get("state");
        assertEquals(true, (state == ActivitySate.AUCTION));

    }

    @Test
    public void testSetCurrency() {
        List<Map<String, Object>> list = this.testListActivity();
        assertEquals(true, (list != null && list.size() > 0));

        Map<String, Object> map = list.get(0);
        int id = (Integer) map.get("id");
        assertEquals(true, (id > 0));

        int amountPerAgeX = 100;
        Map<String, Object> rrMap = lotController.setCurrency(id, amountPerAgeX);
        boolean xSuccess = (rrMap != null && ((Integer) rrMap.get("code") == 0));
        assertEquals(true, xSuccess);

        Map<String, Object> getMap = lotController.getActivity(id);
        assertEquals(true, ( getMap != null && ((Integer) getMap.get("code") == 0)));

        Map<String, Object> dataMap = (Map<String, Object>) getMap.get("data");
        int moneyCreationWay = (Integer) dataMap.get("moneyCreationWay");
        int amountPerAge = (Integer) dataMap.get("amountPerAge");
        assertEquals(true, (moneyCreationWay == MoneyCreationWay.RETRIEVE_BY_AGE));
        assertEquals(true, (amountPerAge == amountPerAgeX));

        //----++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        long money = 3000L;
        Map<String, Object> moneyMap = lotController.setCurrency(id, money);
        boolean mSuccess = (moneyMap != null && ((Integer) moneyMap.get("code") == 0));
        assertEquals(true, mSuccess);

        Map<String, Object> mGetMap = lotController.getActivity(id);
        assertEquals(true, ( mGetMap != null && ((Integer) mGetMap.get("code") == 0)));

        Map<String, Object> mDataMap = (Map<String, Object>) mGetMap.get("data");
        int mCreationWay = (Integer) mDataMap.get("moneyCreationWay");
        long amount = (Long) mDataMap.get("money");
        assertEquals(true, (mCreationWay == MoneyCreationWay.FIX_AMOUNT));
        assertEquals(true, (amount == money));

    }


    @Test
    public void testAll() {
        testNewActivity();
        testUpdateActivity();
        testGetActivity();

        // in testDelActivity() function, has test listActivity.
        // testListActivity();

        testCopyActivity();
        testStartActivity();
        testSopActivity();

        testDelActivity();

    }

}
