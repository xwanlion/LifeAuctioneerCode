package com.github.xwanlion.lifeauctioneer;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TempTest {

//    @Test
//    public void test() throws URISyntaxException {
//        String[] strings = {};
//        for (String str: strings) {
//            System.out.println(str);
//        }
//
//        List<String> list = new ArrayList<>();
//        for (String str: list) {
//            System.out.println(str);
//        }
//
//        Map<String, String> map = new HashMap<>();
//        Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();
//        while (itr.hasNext()) {
//            System.out.println(itr.next().getKey());
//        }
//
//    }
    @Test
    public void test() throws URISyntaxException {
        Auctions auctions = new Auctions();
        System.out.println(auctions.getId());
        Logger.i("auctions.getId:" + auctions.getId());

    }

}
