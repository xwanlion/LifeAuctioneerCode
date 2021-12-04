package com.github.xwanlion.lifeauctioneer.util;

import com.github.xwanlion.lifeauctioneer.model.app.BidderExpectingLot;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.NumberUtil;

public class StringUtils {
    public static List<BidderExpectingLot> toBidderExpectingLots (String str) {
        if (str == null || str.length() == 0) return new ArrayList<>();
        return toBidderExpectingLots(str.split(";"));
    }

    public static List<BidderExpectingLot> toBidderExpectingLots (String[] arr) {
        if (arr == null || arr.length == 0) return new ArrayList<>();

        List<BidderExpectingLot> list = new ArrayList<>();
        for (String s : arr) {
            String[] arrEx = s.split(",");
            if (arrEx == null || arrEx.length != 2) break;
            list.add(toBidderExpectingLot(arrEx));
        }

        return list;
    }

    public static BidderExpectingLot toBidderExpectingLot(String[] arr) {
        BidderExpectingLot lot = new BidderExpectingLot();
        lot.setLotId(NumberUtil.parseInt(arr[0]));
        lot.setExpectingPrice(NumberUtil.parseLong(arr[1]));
        return lot;
    }

    public static void main(String[] args) {
        String str = "1,1500;2,1520;";
        List<BidderExpectingLot> lotList = toBidderExpectingLots(str);
        System.out.println(lotList.size());
        //Logger.i(lotList.size());
    }
}
