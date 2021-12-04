package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.dao.BidderMoneyDao;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.BidderMoney;
import com.github.xwanlion.lifeauctioneer.model.app.MoneyCreationWay;

import java.util.List;

public class BidderMoneyServiceImpl implements IBidderMoneyService {

    private IAuctionsService activityService = null;
    private BidderMoneyDao bidderMoneyDao = null;

    public BidderMoneyServiceImpl() {
        activityService = new AuctionsServiceImpl();
        IDatabase database = IDatabase.getDatabase();
        bidderMoneyDao = database.getBidderMoneyDao();
    }

    @Override
    public boolean createBidderMoney(int activityId, int bidderId) {
        if (activityId <= 0) return false;
        if (bidderId <= 0) return false;

        Auctions auctions = activityService.getActivity(activityId);
        if (auctions == null) throw new RuntimeException("ERR_INVALID_ACTIVITY_ID");

        int moneyCreationWay = auctions.getMoneyCreationWay();
        if (moneyCreationWay != MoneyCreationWay.FIX_AMOUNT) throw new RuntimeException("ERR_MONEY_CREATION_WAY_IS_NOT_FIX");

        long amount = auctions.getMoney();

        BidderMoney bidderMoney = this.getBidderMoney(activityId, bidderId);
        if (bidderMoney == null) bidderMoney = new BidderMoney();

        bidderMoney.setActivityId(activityId);
        bidderMoney.setBidderId(bidderId);
        bidderMoney.setAge(0);
        bidderMoney.setAmount(amount);

        long id = bidderMoneyDao.saveBidderMoney(bidderMoney);
        return id > 0;
    }

    @Override
    public long createBidderMoney(int activityId, int bidderId, int age) {
        if (activityId <= 0) throw new RuntimeException("ERR_INVALID_ACTIVITY_ID");
        if (bidderId <= 0) throw new RuntimeException("ERR_INVALID_BIDDER_ID");
        if (age < 0) throw new RuntimeException("ERR_INVALID_REST_OF_YEARS");

        Auctions auctions = activityService.getActivity(activityId);
        if (auctions == null) throw new RuntimeException("ERR_INVALID_ACTIVITY_ID");

        int moneyCreationWay = auctions.getMoneyCreationWay();
        if (moneyCreationWay != MoneyCreationWay.RETRIEVE_BY_AGE) throw new RuntimeException("ERR_MONEY_CREATION_WAY_NOT_BY_AGE");

        int amountPerAge = auctions.getAmountPerAge();
        long amount = age * amountPerAge;

        BidderMoney bidderMoney = this.getBidderMoney(activityId, bidderId);
        if (bidderMoney == null) bidderMoney = new BidderMoney();
        bidderMoney.setActivityId(activityId);
        bidderMoney.setBidderId(bidderId);
        bidderMoney.setAge(age);
        bidderMoney.setAmount(amount);

        long id = bidderMoneyDao.saveBidderMoney(bidderMoney);
        if (id <= 0) throw new RuntimeException("ERR_SAVE_BIDDER_MONEY_FAIL");
        return amount;
    }

    @Override
    public boolean reduceBidderMoney(int activityId, int bidderId, long amount) {
        if (amount < 0) throw new RuntimeException("ERR_REDUCE_AMOUNT_CAN_NOT_BE_LESS_THAN_ZERO");

        BidderMoney bidderMoney = this.getBidderMoney(activityId, bidderId);
        if (bidderMoney == null) throw new RuntimeException("ERR_INVALID_BIDDER_MONEY: activityId--> " + activityId + " bidderId-->" + bidderId);
        if (bidderMoney.getAmount() < amount) throw new RuntimeException("ERR_REMAIN_MONEY_MUST_GREATER_THAN_OFFER_AMOUNT:remain-->" + bidderMoney.getAmount() + " amount-->" + amount);

        bidderMoney.setAmount(bidderMoney.getAmount() - amount);
        bidderMoneyDao.updateBidderMoney(bidderMoney);

        return true;
    }

    @Override
    public boolean delBidderMoney(int id) {
        BidderMoney bidderMoney = new BidderMoney();
        bidderMoney.setId(id);
        long r = bidderMoneyDao.delBidderMoney(bidderMoney);
        return r > 0;
    }

    @Override
    public BidderMoney getBidderMoney(int id) {
        return bidderMoneyDao.getBidderMoney(id);
    }

    @Override
    public BidderMoney getBidderMoney(int activityId, int bidderId) {
        return bidderMoneyDao.getBidderMoney(activityId, bidderId);
    }

    @Override
    public List<BidderMoney> listBidderMoney(int activityId) {
        return bidderMoneyDao.listBidderMoney(activityId);
    }

    @Override
    public List<BidderMoney> listBidderMoney(int activityId, int pageIndex, int recordsPerPage) {
        if (pageIndex <= 0) pageIndex = 0;
        if (recordsPerPage < 20) recordsPerPage = 20;
        return bidderMoneyDao.listBidderMoney(activityId, pageIndex, recordsPerPage);
    }

}
