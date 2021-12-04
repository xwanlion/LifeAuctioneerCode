package com.github.xwanlion.lifeauctioneer.service;

import androidx.room.Transaction;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.dao.AuctionsDao;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.conf.ActivitySate;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.MoneyCreationWay;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import java.util.List;

public class AuctionsServiceImpl implements IAuctionsService {

    private AuctionsDao auctionsDao = null;
    private Auctions currentAuction = null;

    public AuctionsServiceImpl() {
        IDatabase database = IDatabase.getDatabase();
        auctionsDao = database.getAuctionsDao();
    }

    @Override
    public long saveActivity(int auctioneerId, int amountPerAge) {
        if (auctioneerId <= 0) throw new RuntimeException("ERR_AUCTIONEER_ID_CAN_NOT_BE_NULL");

        Auctioneer auctioneer = BeanManager.auctioneerService.getAuctioneer(auctioneerId);
        if (auctioneer == null) throw new RuntimeException("ERR_INVALID_AUCTIONEER");

        Auctions auctions = new Auctions();
        auctions.setAuctioneerId(auctioneerId);
        auctions.setAuctioneerName(auctioneer.getUsername());
        auctions.setDate(System.currentTimeMillis());
        auctions.setMoneyCreationWay(MoneyCreationWay.RETRIEVE_BY_AGE);
        auctions.setState(ActivitySate.STOPPING);
        auctions.setMoney(0L);
        auctions.setAmountPerAge(amountPerAge);

        return this.saveActivity(auctions);

    }

    @Override
    public long saveActivity(int auctioneerId, long money) {
        if (auctioneerId <= 0) throw new RuntimeException("ERR_AUCTIONEER_ID_CAN_NOT_BE_NULL");
        if (money <= 0) throw new RuntimeException("ERR_MONEY_MUST_GREATER_THA_ZERO");

        Auctions auctions = new Auctions();
        auctions.setAuctioneerId(auctioneerId);
        auctions.setDate(System.currentTimeMillis());
        auctions.setMoneyCreationWay(MoneyCreationWay.FIX_AMOUNT);
        auctions.setState(ActivitySate.STOPPING);
        auctions.setMoney(money);

        return this.saveActivity(auctions);

    }

    @Override
    public long saveActivity(Auctions auctions) {
        return auctionsDao.saveAuctions(auctions);
    }

    @Override
    public boolean updateActivity(int id, int auctioneerId, int moneyCreationWay) {
        Auctions auctions = this.getActivity(id);
        auctions.setAuctioneerId(auctioneerId);
        auctions.setMoneyCreationWay(moneyCreationWay);
        return this.updateActivity(auctions);
    }

    @Override
    public boolean updateActivity(Auctions auctions) {
        auctionsDao.updateAuctions(auctions);
        return true;
    }

    @Override
    public boolean setCurrency(int id, long money) {
        Auctions auctions = this.getActivity(id);
        auctions.setMoneyCreationWay(MoneyCreationWay.FIX_AMOUNT);
        auctions.setMoney(money);
        return this.updateActivity(auctions);
    }

    @Override
    public boolean setCurrency(int id, int amountPerAge) {
        Auctions auctions = this.getActivity(id);
        auctions.setMoneyCreationWay(MoneyCreationWay.RETRIEVE_BY_AGE);
        auctions.setAmountPerAge(amountPerAge);
        return this.updateActivity(auctions);
    }

    @Override
    public boolean delActivity(int id) {
        Auctions auctions = new Auctions();
        auctions.setId(id);
        long r = auctionsDao.delAuctions(auctions);
        return r > 0;
    }

    @Override
    public Auctions getActivity(int id) {
        return auctionsDao.getAuctions(id);
    }

    @Override
    public Auctions getCurrentActivity() {
        return this.currentAuction;
    }

    @Override
    public List<Auctions> listActivity(int pageIndex, int recordsPerPage) {
        if (pageIndex <= 0) pageIndex = 0;
        if (recordsPerPage < 20) recordsPerPage = 20;
        return auctionsDao.listAuctions(pageIndex, recordsPerPage);
    }

    @Override
    public boolean copyActivity(int id) {
        Auctions auctions = this.getActivity(id);
        auctions.setId(0);
        long nid = auctionsDao.saveAuctions(auctions);
        return nid > 0;
    }

//    @Override
//    @Transaction
//    public boolean copyActivity(int id) {
//        Auctions auctions = this.getActivity(id);
//        auctions.setId(-1);
//
//        long newActivityId = this.auctionsDao.saveAuctions(auctions);
//        if (newActivityId <= 0) throw new RuntimeException("ERR_COPY_ACTIVITY_OBJECT_FAIL");
//
//        auctionsDao.copyActivityBidder(id, newActivityId);
//        auctionsDao.copyActivityLot(id, newActivityId);
//        return true;
//    }

    @Override
    public boolean startActivity(int id) {
        if (this.currentAuction != null) throw new RuntimeException("ERR_CAN_NOT_STARTUP_TWO_ACTIVITY_AT_THE_SAME_TIME");

        Auctions auctions = this.getActivity(id);
        if (auctions.getState() < ActivitySate.READY) auctions.setState(ActivitySate.READY);

        boolean success = this.updateActivity(auctions);
        if (!success) return  false;
        // Logger.i("cur activity id:" + auctions.getId() + " needLogin:" + auctions.isNeedLogin());
        this.currentAuction = auctions;
        return success;
    }

    @Override
    public boolean stopActivity(int id) {
        if (id != this.currentAuction.getId()) throw new RuntimeException("ERR_IS_NOT_CURRENT_ACTIVITY_ID");
        Auctions auctions = this.getActivity(id);
        // auctions.setState(ActivitySate.STOPPING);
        return this.updateActivity(auctions);
    }

    @Override
    public void setCurrentActivity(Auctions activity) {
        this.currentAuction = activity;
    }

    @Override
    public void setCurrentActivity(int activityId) {
        this.currentAuction = this.getActivity(activityId);
    }
}
