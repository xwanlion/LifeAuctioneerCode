package com.github.xwanlion.lifeauctioneer.model.app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.github.xwanlion.lifeauctioneer.model.JsonPo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = "bidLog")
public class BidLog extends JsonPo {
    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "activity_id")
    private int activityId;

    @NonNull
    @ColumnInfo(name = "bidder_id")
    private int bidderId;

    @NonNull
    @ColumnInfo(name = "lot_id")
    private int lotId;

    @NonNull
    @ColumnInfo(name = "price")
    private long price;

    @NonNull
    @ColumnInfo(name = "time")
    private long time;

    @Ignore
    private String bidder;

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

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }

    public static Map<String, Object> toMap(BidLog log) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", log.getId());
        map.put("activityId", log.getActivityId());
        map.put("bidderId", log.getBidderId());
        map.put("lotId", log.getLotId());
        map.put("price", log.getPrice());
        map.put("time", log.getTime());
        return map;
    }

    public static List<Map<String, Object>> toMap(List<BidLog> logs) {
        if (logs == null || logs.size() == 0) return new ArrayList<>();

        List<Map<String, Object>> list = new ArrayList<>();
        for (BidLog activity : logs) {
            list.add(BidLog.toMap(activity));
        }

        return list;

    }

}
