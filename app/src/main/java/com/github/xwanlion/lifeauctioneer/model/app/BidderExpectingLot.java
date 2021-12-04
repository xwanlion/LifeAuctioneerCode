package com.github.xwanlion.lifeauctioneer.model.app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.xwanlion.lifeauctioneer.model.JsonPo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = "expectingLot")
public class BidderExpectingLot extends JsonPo {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "activity_id")
    private int activityId;

    @NonNull
    @ColumnInfo(name = "lot_id")
    private int lotId;

    @NonNull
    @ColumnInfo(name = "bidder_id")
    private int bidderId;

    @NonNull
    @ColumnInfo(name = "purchase_price")
    private long purchasePrice;

    @NonNull
    @ColumnInfo(name = "expecting_price")
    private long expectingPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public long getExpectingPrice() {
        return expectingPrice;
    }

    public void setExpectingPrice(long expectingPrice) {
        this.expectingPrice = expectingPrice;
    }

    public static Map<String, Object> toMap(BidderExpectingLot expectingLot) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", expectingLot.getId());
        map.put("activityId", expectingLot.getActivityId());
        map.put("lotId", expectingLot.getLotId());
        map.put("bidderId", expectingLot.getBidderId());
        map.put("purchasePrice", expectingLot.getPurchasePrice());
        map.put("expectingPrice", expectingLot.getExpectingPrice());
        return map;
    }

    public static List<Map<String, Object>> toMap(List<BidderExpectingLot> expectingLots) {
        if (expectingLots == null || expectingLots.size() == 0) return new ArrayList<>();

        List<Map<String, Object>> list = new ArrayList<>();
        for(BidderExpectingLot expectingLot : expectingLots) {
            list.add(BidderExpectingLot.toMap(expectingLot));
        }

        return list;

    }

}
