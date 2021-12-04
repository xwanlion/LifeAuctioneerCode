package com.github.xwanlion.lifeauctioneer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.github.xwanlion.lifeauctioneer.model.app.Auctions;

import java.util.List;

@Dao
public abstract class AuctionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long saveAuctions(Auctions auctions);

    @Update
    public abstract void updateAuctions(Auctions auctions);

    @Delete
    public abstract int delAuctions(Auctions auctions);

//    @Transaction
//    public void copyActivity(int id) {
//        int newActivityId = copyActivityObject(id);
//        if (newActivityId <= 0) throw new RuntimeException("ERR_COPY_ACTIVITY_OBJECT_FAIL");
//
//        copyActivityBidder(id, newActivityId);
//        copyActivityLot(id, newActivityId);
//    }

//    @Insert
//    @Query("INSERT INTO auctions  SELECT -1 id, auctioneer_id, auctioneer_name, date, -1 state, money_creation_way, money, amount_per_age, need_login FROM auctions where id = :id")
//    public abstract int copyActivityObject(long id);

//    @Query("INSERT INTO bidder  SELECT :newActivityId activity_id, username, password FROM bidder where activity_id = :activityId")
//    public abstract void copyActivityBidder(long activityId, long newActivityId);

//    @Query("INSERT INTO lot  select :newActivityId activity_id, name, start_price, increment, purchase_price, `desc`, image_file, buyer_id, buyer from lot where activity_id = :activityId")
//    public abstract void copyActivityLot(long activityId, long newActivityId);

    @Query("select id, auctioneer_id, auctioneer_name, date, state, money_creation_way, money, amount_per_age, need_login from auctions where id = :id")
    public abstract Auctions getAuctions(int id);

    @Query("select id, auctioneer_id, auctioneer_name, date, state, money_creation_way, money, amount_per_age, need_login from auctions order by id desc limit :recordsPerPage offset :pageIndex * :recordsPerPage")
    public abstract List<Auctions> listAuctions(int pageIndex, int recordsPerPage);

    @Query("select id, auctioneer_id, auctioneer_name, date, state, money_creation_way, money, amount_per_age, need_login from auctions order by id desc limit 200 offset 0")
    public abstract LiveData<List<Auctions>> listAuctions();

}
