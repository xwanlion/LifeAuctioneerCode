package com.github.xwanlion.lifeauctioneer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.github.xwanlion.lifeauctioneer.model.app.BidLog;

import java.util.List;

@Dao
public interface BidLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long saveBidLog(BidLog bidLog);

    @Delete
    public int delBidLog(BidLog bidLog);

    @Query("delete from bidLog where activity_id = :activityId")
    public void delBidLog(int activityId);

    @Query("delete from bidLog where activity_id = :activityId and bidder_id = :bidderId")
    public void delBidLog(int activityId, int bidderId);

    @Query("select id, activity_id, bidder_id, lot_id, price, time from bidLog where id = :id")
    public BidLog getBidLog(int id);

    @Query("select id, activity_id, bidder_id, lot_id, price, time from bidLog where activity_id = :activityId and bidder_id = :bidderId and lot_id = :lotId")
    public BidLog getBidLog(int activityId, int bidderId, int lotId);

    // select the highest offer, if offer price is same, select the earlier one
    @Query("select id, activity_id, bidder_id, lot_id, price, time from bidLog where  activity_id = :activityId and lot_id = :lotId order by price desc, time asc limit 1")
    public BidLog getHighestOffer(int activityId, int lotId);

    @Query("select id, activity_id, bidder_id, lot_id, price, time from bidLog where activity_id = :activityId and bidder_id = :bidderId")
    public List<BidLog> listBidLog(int activityId, int bidderId);

    @Query("select id, activity_id, bidder_id, lot_id, price, time from bidLog where activity_id = :activityId order by id desc limit :recordsPerPage offset :pageIndex * :recordsPerPage")
    public List<BidLog> listBidLog(int activityId, int pageIndex, int recordsPerPage);

}
