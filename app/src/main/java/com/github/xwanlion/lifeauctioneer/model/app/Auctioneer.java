package com.github.xwanlion.lifeauctioneer.model.app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.github.xwanlion.lifeauctioneer.model.JsonPo;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = "auctioneer", indices = {@Index(value = "username", unique = true)})
public class Auctioneer extends JsonPo {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;

    @NonNull
    @ColumnInfo(name="username")
    private String username;

    @ColumnInfo(name="password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static Auctioneer valueOf(String username, String password) {
        Auctioneer auctioneer = new Auctioneer();
        auctioneer.setPassword(password);
        auctioneer.setUsername(username);
        return auctioneer;
    }

    public static Map<String, Object> toMap(Auctioneer auctioneer) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", auctioneer.getId());
        map.put("username", auctioneer.getUsername());
        map.put("password", auctioneer.getPassword());
        return map;
    }

    public static List<Map<String, Object>> toMap(List<Auctioneer> auctioneers) {
        if (auctioneers == null || auctioneers.size() == 0) return new ArrayList<>();

        List<Map<String, Object>> list = new ArrayList<>();
        for(Auctioneer auctioneer : auctioneers) {
            list.add(Auctioneer.toMap(auctioneer));
        }

        return list;

    }

}
