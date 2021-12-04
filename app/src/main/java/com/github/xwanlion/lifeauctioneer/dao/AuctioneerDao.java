package com.github.xwanlion.lifeauctioneer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;

import java.util.List;

@Dao
public interface AuctioneerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long saveAuctioneer(Auctioneer auctioneer);

    @Delete
    public int delAuctioneer(Auctioneer auctioneer);

    @Query("select id, username, password from auctioneer where id = :id")
    public Auctioneer getAuctioneer(int id);

    @Query("select id, username, password from auctioneer where username = :username limit 1")
    public Auctioneer getAuctioneer(String username);

    @Query("select id, username, password from auctioneer order by id desc limit :recordsPerPage offset :pageIndex * :recordsPerPage")
    public List<Auctioneer> listAuctioneer(int pageIndex, int recordsPerPage);

    @Query("select id, username, password from auctioneer order by id desc limit 200 offset 0")
    public LiveData<List<Auctioneer>> listAuctioneer();

}
