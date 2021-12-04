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

@Entity(tableName = "bidderMoney")
public class BidderMoney extends JsonPo {
    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "activity_id", index = true)
    private int activityId;

    @NonNull
    @ColumnInfo(name = "bidder_id", index = true)
    private int bidderId;

    @NonNull
    @ColumnInfo(name = "age")
    private int age; // age for create auction money

    @NonNull
    @ColumnInfo(name = "amount")
    private long amount; // the number of bidder's money

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public static Map<String, Object> toMap(BidderMoney bidderMoney) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", bidderMoney.getId());
        map.put("activityId", bidderMoney.getActivityId());
        map.put("bidderId", bidderMoney.getBidderId());
        map.put("age", bidderMoney.getAge());
        map.put("amount", bidderMoney.getAmount());
        return map;
    }

    public static List<Map<String, Object>> toMap(List<BidderMoney> bidderMonies) {
        if (bidderMonies == null || bidderMonies.size() == 0) return new ArrayList<>();

        List<Map<String, Object>> list = new ArrayList<>();
        for (BidderMoney activity : bidderMonies) {
            list.add(BidderMoney.toMap(activity));
        }

        return list;

    }


}
