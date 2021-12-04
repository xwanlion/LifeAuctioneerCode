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

@Entity(tableName = "bidder")
public class Bidder extends JsonPo {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "activity_id", index = true)
    private int auctionsId;

    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuctionsId() {
        return auctionsId;
    }

    public void setAuctionsId(int auctionsId) {
        this.auctionsId = auctionsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Bidder valueOf(int id, int auctionsId, String username, String password) {
        Bidder bidder = new Bidder();
        bidder.setId(id);
        bidder.setAuctionsId(auctionsId);
        bidder.setPassword(password);
        bidder.setUsername(username);
        return bidder;
    }

    public static Bidder valueOf(int auctionsId, String username, String password) {
//        Bidder bidder = new Bidder();
//        bidder.setAuctionsId(auctionsId);
//        bidder.setPassword(password);
//        bidder.setUsername(username);
//        return bidder;
        return valueOf(0, auctionsId, username, password);
    }

    public static Map<String, Object> toMap(Bidder bidder) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", bidder.getId());
        map.put("auctionsId", bidder.getAuctionsId());
        map.put("username", bidder.getUsername());
        map.put("password", bidder.getPassword());
        return map;
    }

    public static List<Map<String, Object>> toMap(List<Bidder> bidders) {
        if (bidders == null || bidders.size() == 0) return new ArrayList<>();

        List<Map<String, Object>> list = new ArrayList<>();
        for(Bidder bidder : bidders) {
            list.add(Bidder.toMap(bidder));
        }

        return list;

    }

}
