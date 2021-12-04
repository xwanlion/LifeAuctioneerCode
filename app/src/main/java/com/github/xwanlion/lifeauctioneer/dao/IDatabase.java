package com.github.xwanlion.lifeauctioneer.dao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.github.xwanlion.lifeauctioneer.App;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.BidLog;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.github.xwanlion.lifeauctioneer.model.app.BidderExpectingLot;
import com.github.xwanlion.lifeauctioneer.model.app.BidderMoney;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;
import com.github.xwanlion.lifeauctioneer.model.app.Testimonials;

@Database(entities = {
            Auctioneer.class, Bidder.class, Lot.class, BidderExpectingLot.class, Auctions.class,
            BidLog.class, BidderMoney.class, Testimonials.class
        },
        version = 1,
        exportSchema = false)
public abstract class IDatabase extends RoomDatabase {

    private volatile static IDatabase database;

    public synchronized static IDatabase getDatabase() {
        if (database != null) return database;
        database = Room.databaseBuilder(App.context(), IDatabase.class, "LIFE_AUCTION_DB").fallbackToDestructiveMigration().build();
        return database;
    }

    public abstract AuctioneerDao getAuctioneerDao();

    public abstract BidderDao getBidderDao();

    public abstract LotDao getLotDao();

    public abstract ExpectingLotDao getExpectingLotDao();

    public abstract AuctionsDao getAuctionsDao();

    public abstract BidLogDao getBidLogDao();

    public abstract BidderMoneyDao getBidderMoneyDao();

    public abstract TestimonialsDao getTestimonialsDao();

}
