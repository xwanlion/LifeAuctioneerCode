package com.github.xwanlion.lifeauctioneer.controller;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AuctioneerControllerTester {
    private AuctioneerController controller = new AuctioneerController();

    @Test
    public void testNewAuctioneer() {
        controller.newAuctioneer("username1", "password");
        Map<String, Object> map = controller.listAuctioneer();
        assertEquals(map.get("code"), 0);

    }

    @Test
    public void testDelAuctioneer() {
        Map<String, Object> map = controller.listAuctioneer();
        assertEquals(map.get("code"), 0);

        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("data");
        assertEquals(true, list != null);

        int id = (Integer) list.get(0).get("id");
        assertEquals(true, id > 0);
        Map<String, Object> delMap = controller.delAuctioneer(id);
        assertEquals(true, delMap != null && ((Integer) delMap.get("code") == 0));


    }
}
