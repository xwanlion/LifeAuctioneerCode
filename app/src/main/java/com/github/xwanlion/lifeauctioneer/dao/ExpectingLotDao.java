package com.github.xwanlion.lifeauctioneer.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.github.xwanlion.lifeauctioneer.model.app.BidderExpectingLot;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;

import java.util.List;

@Dao
public interface ExpectingLotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long saveLot(BidderExpectingLot lot);

    @Delete
    public int delLot(BidderExpectingLot lot);

    @Update
    public int updateLot(BidderExpectingLot lot);

    @Query("select id, activity_id, lot_id, bidder_id, purchase_price, expecting_price from expectingLot where id = :id")
    public BidderExpectingLot getLot(int id);

    @Query("select id, activity_id, lot_id, bidder_id, purchase_price, expecting_price from expectingLot where activity_id = :activityId and bidder_id = :bidderId and lot_id = :lotId")
    public BidderExpectingLot getLot(int activityId, int bidderId, int lotId);

    @Query("select id, activity_id, lot_id, bidder_id, purchase_price, expecting_price from expectingLot where activity_id = :activityId and bidder_id = :bidderId")
    public List<BidderExpectingLot> listLot(int activityId, int bidderId);

    @Query("select id, activity_id, lot_id, bidder_id, purchase_price, expecting_price from expectingLot where activity_id = :activityId")
    public List<BidderExpectingLot> listLot(int activityId);

    @Query("select id, activity_id, lot_id, bidder_id, purchase_price, expecting_price from expectingLot where activity_id = :activityId order by id desc limit :recordsPerPage offset :pageIndex * :recordsPerPage")
    public List<BidderExpectingLot> listLot(int activityId, int pageIndex, int recordsPerPage);

}
