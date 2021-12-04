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

@Entity(tableName = "testimonials")
public class Testimonials extends JsonPo {

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

    @ColumnInfo(name = "content", defaultValue = "")
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Map<String, Object> toMap(Testimonials testimonials) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", testimonials.getId());
        map.put("activityId", testimonials.getActivityId());
        map.put("bidderId", testimonials.getBidderId());
        map.put("content", testimonials.getContent());
        return map;
    }

    public static List<Map<String, Object>> toMap(List<Testimonials> testimonials) {
        if (testimonials == null || testimonials.size() == 0) return new ArrayList<>();

        List<Map<String, Object>> list = new ArrayList<>();
        for (Testimonials activity : testimonials) {
            list.add(Testimonials.toMap(activity));
        }

        return list;

    }

}
