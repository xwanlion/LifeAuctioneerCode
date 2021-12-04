package com.github.xwanlion.lifeauctioneer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.github.xwanlion.lifeauctioneer.model.app.Lot;

import java.util.List;

@Dao
public interface LotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long saveLot(Lot lot);

    @Delete
    public int delLot(Lot lot);

    @Query("INSERT INTO lot  select NULL id, :newActivityId activity_id, name, start_price, increment, purchase_price, `desc`, image_file, buyer_id, buyer from lot where activity_id = :activityId")
    public abstract void copyLot(long activityId, long newActivityId);

    @Query("select id, activity_id, name, start_price, increment, purchase_price, `desc`, image_file, buyer_id, buyer from lot where id = :id")
    public Lot getLot(int id);

    @Query("select id, activity_id, name, start_price, increment, purchase_price, `desc`, image_file, buyer_id, buyer from lot where activity_id = :activityId and id = :id")
    public Lot getActivityLot(int activityId, int id);

    @Query("select id, activity_id, name, start_price, increment, purchase_price, `desc`, image_file, buyer_id, buyer from lot where activity_id = :activityId")
    public Lot listLot(int activityId);

    @Query("select id, activity_id, name, start_price, increment, purchase_price, `desc`, image_file, buyer_id, buyer from lot where activity_id = :activityId order by id desc limit :recordsPerPage offset :pageIndex * :recordsPerPage")
    public List<Lot> listLot(int activityId, int pageIndex, int recordsPerPage);

    @Query("select id, activity_id, name, start_price, increment, purchase_price, `desc`, image_file, buyer_id, buyer from lot where activity_id = :activityId order by id desc limit 0 offset 200")
    public LiveData<List<Lot>> listLot(String activityId);

}
