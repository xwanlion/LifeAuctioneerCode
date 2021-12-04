package com.github.xwanlion.lifeauctioneer.model.app;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ActivityReview {

    private LiveData<List<Lot>> lots;

    private LiveData<List<Bidder>> bidders;

    private String money;

    private String auctioneer;

    public LiveData<List<Lot>> getLots() {
        return lots;
    }

    public void setLots(LiveData<List<Lot>> lots) {
        this.lots = lots;
    }

    public LiveData<List<Bidder>> getBidders() {
        return bidders;
    }

    public void setBidders(LiveData<List<Bidder>> bidders) {
        this.bidders = bidders;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAuctioneer() {
        return auctioneer;
    }

    public void setAuctioneer(String auctioneer) {
        this.auctioneer = auctioneer;
    }

}
