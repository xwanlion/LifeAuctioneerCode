package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.dao.AuctioneerDao;
import com.github.xwanlion.lifeauctioneer.dao.BidderDao;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;

import java.util.ArrayList;
import java.util.List;

public class BidderServiceImpl implements IBidderService {

    private BidderDao bidderDao = null;
    private AuctioneerDao auctioneerDao = null;

    public BidderServiceImpl() {
        IDatabase database = IDatabase.getDatabase();
        bidderDao = database.getBidderDao();
        auctioneerDao = database.getAuctioneerDao();
    }

    @Override
    public long saveBidder(int auctionsId, String username, String password) {
        if (auctionsId <= 0) throw new RuntimeException("ERR_INVALID_AUCTIONS_ID");
        if (this.getBidder(auctionsId, username) != null)
            throw new RuntimeException("ERR_BIDDER_NAME_DUPLICATE");

        Auctioneer auctioneer = auctioneerDao.getAuctioneer(username);
        if (auctioneer != null && auctioneer.getId() > 0)
            throw new RuntimeException("ERR_BIDDER_NAME_IS_TOKEN");

        Bidder bidder = Bidder.valueOf(auctionsId, username, password);
        long id = this.saveBidder(bidder);
        return id;
    }

    @Override
    public long saveBidder(Bidder bidder) {
        return bidderDao.saveBidder(bidder);
    }

    @Override
    public boolean delBidder(int id) {
        if (id <= 0) throw new RuntimeException("ERR_AUCTIONEER_ID_MUST_GREATER_THAN_ZERO");
        Bidder bidder = new Bidder();
        bidder.setId(id);
        return bidderDao.delBidder(bidder) > 0;
    }

    @Override
    public Bidder getBidder(int id) {
        if (id <= 0) return null;
        return bidderDao.getBidder(id);
    }

    @Override
    public Bidder getBidder(int auctionsId, String username) {
        if (auctionsId <= 0) return null;
        if (username == null || username.length() == 0) return null;
        return bidderDao.getBidder(auctionsId, username);
    }

    @Override
    public List<Bidder> listBidder(int pageIndex, int recordsPerPage) {
        if (pageIndex < 0) pageIndex = 0;
        if (recordsPerPage < 20) recordsPerPage = 20;
        return bidderDao.listBidder(pageIndex, recordsPerPage);
    }

    @Override
    public List<Bidder> listBidder(int auctionsId, int pageIndex, int recordsPerPage) {
        if (auctionsId <= 0) return new ArrayList<>();
        if (pageIndex < 0) pageIndex = 0;
        if (recordsPerPage < 20) recordsPerPage = 20;
        return bidderDao.listBidder(auctionsId, pageIndex, recordsPerPage);
    }

    @Override
    public boolean checkBidder(int auctionsId, String username, String password) {
        Bidder bidder = this.getBidder(auctionsId, username);
        if (bidder == null) return false;

        String yPassword = bidder.getPassword();
        if (yPassword == null) yPassword = "";
        if (password == null) password = "";

        return password.equals(yPassword);
    }
}
