package com.github.xwanlion.lifeauctioneer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.github.xwanlion.lifeauctioneer.model.app.Bidder;

import java.util.List;

@Dao
public interface BidderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long saveBidder(Bidder bidder);

    @Delete
    public int delBidder(Bidder bidder);

    @Query("INSERT INTO bidder  SELECT NULL id, :newActivityId activity_id, username, password FROM bidder where activity_id = :activityId")
    public abstract void copyBidder(long activityId, long newActivityId);

    @Query("select id, activity_id, username, password from bidder where id = :id")
    public Bidder getBidder(int id);

    @Query("select id, activity_id, username, password from bidder where activity_id = :auctionsId and username = :username limit 1")
    public Bidder getBidder(int auctionsId, String username);

    @Query("select id, activity_id, username, password from bidder where activity_id = :auctionsId order by id desc limit :recordsPerPage offset :pageIndex * :recordsPerPage")
    public List<Bidder> listBidder(int auctionsId, int pageIndex, int recordsPerPage);

    @Query("select id, activity_id, username, password from bidder where activity_id = :auctionsId order by id desc")
    public List<Bidder> listBidder(int auctionsId);

    @Query("select id, activity_id, username, password from bidder order by id desc limit :recordsPerPage offset :pageIndex * :recordsPerPage")
    public List<Bidder> listBidder(long pageIndex, long recordsPerPage);

}
