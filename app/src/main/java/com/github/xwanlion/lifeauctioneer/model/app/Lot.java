package com.github.xwanlion.lifeauctioneer.model.app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.github.xwanlion.lifeauctioneer.model.JsonPo;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = "lot")
public class Lot extends JsonPo {
    public static Lot activeLot = null;
    private static Lot restLot = null;

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "activity_id")
    private int activityId;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "start_price", defaultValue = "0")
    private long startPrice;

    @ColumnInfo(name = "increment", defaultValue = "0")
    private long increment; // scale amount

    @ColumnInfo(name = "purchase_price", defaultValue = "0")
    private long purchasePrice;

    @ColumnInfo(name = "desc")
    private String desc;

    @ColumnInfo(name = "image_file")
    private String imageFile;

    @ColumnInfo(name = "buyer_id")
    private int buyerId;

    @ColumnInfo(name = "buyer")
    private String buyer;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(long startPrice) {
        this.startPrice = startPrice;
    }

    public long getIncrement() {
        return increment;
    }

    public void setIncrement(long increment) {
        this.increment = increment;
    }

    public long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public static Map<String, Object> toMap(Lot lot) {
        String filePath = "/storage/emulated/0/LIFE_AUCTION/";
        Map<String, Object> map = new HashMap<>();
        map.put("id", lot.getId());
        map.put("activityId", lot.getActivityId());
        map.put("name", lot.getName());
        map.put("startPrice", lot.getStartPrice());
        map.put("increment", lot.getIncrement());
        map.put("purchasePrice", lot.getPurchasePrice());
        map.put("desc", lot.getDesc());
        map.put("buyerId", lot.getBuyerId());
        map.put("buyer", lot.getBuyer());
        map.put("imageFile", lot.getImageFile().replace(filePath, ""));
        return map;
    }

    public static List<Map<String, Object>> toMap(List<Lot> lots) {
        if (lots == null || lots.size() == 0) return new ArrayList<>();

        List<Map<String, Object>> list = new ArrayList<>();
        for(Lot lot : lots) {
            list.add(Lot.toMap(lot));
        }

        return list;
    }

    /**
     * rest lot.
     * to show that current auction lot has sold, can not to bid now.
     * @return
     */
    public static Lot getRestLot() {
        if (restLot == null) {
            restLot = new Lot();
            restLot.setId(-1);
        }
        return restLot;
    }

    public Lot clone() {
        Lot lot = new Lot();
        lot.setId(this.getId());
        lot.setActivityId(this.getActivityId());
        lot.setName(this.getName());
        lot.setStartPrice(this.getStartPrice());
        lot.setIncrement(this.getIncrement());
        lot.setPurchasePrice(this.getPurchasePrice());
        lot.setDesc(this.getDesc());
        lot.setBuyerId(this.getBuyerId());
        lot.setBuyer(this.getBuyer());
        lot.setImageFile(this.getImageFile());
        return lot;
    }

}
