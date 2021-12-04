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

@Entity(tableName = "auctions")
public class Auctions extends JsonPo {

    @NonNull
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "auctioneer_id")
    private int auctioneerId;

    @NonNull
    @ColumnInfo(name = "auctioneer_name")
    private String auctioneerName;

    @NonNull
    @ColumnInfo(name = "date")
    private long date;

    @NonNull
    @ColumnInfo(name = "state")
    private int state;

    @NonNull
    @ColumnInfo(name = "money_creation_way")
    private int moneyCreationWay;

    @ColumnInfo(name = "money", defaultValue = "0")
    private long money;

    @ColumnInfo(name = "amount_per_age", defaultValue = "0")
    private int amountPerAge;

    @ColumnInfo(name = "need_login")
    private boolean needLogin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuctioneerName() {
        return auctioneerName;
    }

    public void setAuctioneerName(String auctioneerName) {
        this.auctioneerName = auctioneerName;
    }

    public int getAuctioneerId() {
        return auctioneerId;
    }

    public void setAuctioneerId(int auctioneerId) {
        this.auctioneerId = auctioneerId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getMoneyCreationWay() {
        return moneyCreationWay;
    }

    public void setMoneyCreationWay(int moneyCreationWay) {
        this.moneyCreationWay = moneyCreationWay;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public int getAmountPerAge() {
        return amountPerAge;
    }

    public void setAmountPerAge(int amountPerAge) {
        this.amountPerAge = amountPerAge;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }

    public Auctions clone() {
        Auctions newAuctions = new Auctions();
        newAuctions.setState(this.getState());
        newAuctions.setId(0);
        newAuctions.setMoney(this.getMoney());
        newAuctions.setAmountPerAge(this.getAmountPerAge());
        newAuctions.setMoneyCreationWay(this.getMoneyCreationWay());
        newAuctions.setAuctioneerId(this.getAuctioneerId());
        newAuctions.setAuctioneerName(this.getAuctioneerName());
        newAuctions.setDate(this.getDate());
        newAuctions.setNeedLogin(this.isNeedLogin());
        return newAuctions;
    }

    public static Map<String, Object> toMap(Auctions auctions) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", auctions.getId());
        map.put("auctioneerId", auctions.getAuctioneerId());
        map.put("auctioneerName", auctions.getAuctioneerName());
        map.put("date", auctions.getDate());
        map.put("state", auctions.getState());
        map.put("moneyCreationWay", auctions.getMoneyCreationWay());
        map.put("money", auctions.getMoney());
        map.put("amountPerAge", auctions.getAmountPerAge());
        map.put("needLogin", auctions.isNeedLogin());
        return map;
    }

    public static List<Map<String, Object>> toMap(List<Auctions> activities) {
        if (activities == null || activities.size() == 0) return new ArrayList<>();

        List<Map<String, Object>> list = new ArrayList<>();
        for (Auctions auctions : activities) {
            list.add(Auctions.toMap(auctions));
        }

        return list;

    }


}
