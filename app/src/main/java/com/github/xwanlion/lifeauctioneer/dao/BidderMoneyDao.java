package com.github.xwanlion.lifeauctioneer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.github.xwanlion.lifeauctioneer.model.app.BidderMoney;

import java.util.List;

@Dao
public interface BidderMoneyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long saveBidderMoney(BidderMoney bidderMoney);

    @Update
    public void updateBidderMoney(BidderMoney bidderMoney);

    @Delete
    public int delBidderMoney(BidderMoney bidderMoney);

    @Query("delete from bidderMoney where activity_id = :activityId and bidder_id = :bidderId")
    public int delBidderMoney(int activityId, int bidderId);

    @Query("select id, activity_id, bidder_id, age, amount from bidderMoney where id = :id")
    public BidderMoney getBidderMoney(int id);

    @Query("select id, activity_id, bidder_id, age, amount from bidderMoney where activity_id = :activityId and bidder_id = :bidderId")
    public BidderMoney getBidderMoney(int activityId, int bidderId);

    @Query("select id, activity_id, bidder_id, age, amount from bidderMoney where activity_id = :activityId")
    public List<BidderMoney> listBidderMoney(int activityId);

    @Query("select id, activity_id, bidder_id, age, amount from bidderMoney where activity_id = :activityId order by id desc limit :recordsPerPage offset :pageIndex * :recordsPerPage")
    public List<BidderMoney> listBidderMoney(int activityId, int pageIndex, int recordsPerPage);

    @Query("select id, activity_id, bidder_id, age, amount from bidderMoney where activity_id = :activityId order by id desc")
    public LiveData<List<BidderMoney>> listBidderMoney(long activityId);

}
