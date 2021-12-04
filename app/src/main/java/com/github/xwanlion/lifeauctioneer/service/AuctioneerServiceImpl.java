package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.dao.AuctioneerDao;
import com.github.xwanlion.lifeauctioneer.dao.BidderDao;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import java.util.List;

public class AuctioneerServiceImpl implements IAuctioneerService {

    private AuctioneerDao auctioneerDao = null;
    private BidderDao bidderDao = null;

    public AuctioneerServiceImpl() {
        IDatabase database = IDatabase.getDatabase();
        auctioneerDao = database.getAuctioneerDao();
        bidderDao = database.getBidderDao();
    }

    @Override
    public long saveAuctioneer(String username, String password) {
        if (this.getAuctioneer(username) != null) throw new RuntimeException("ERR_AUCTIONEER_NAME_DUPLICATE");
        Auctions auctions = BeanManager.auctionsService.getCurrentActivity();
        if (auctions == null) throw new RuntimeException("ERR_AUCTIONS_HAS_TOPPED");

        Bidder bidder = bidderDao.getBidder(auctions.getId(), username);
        if (bidder != null && bidder.getId() > 0) throw new RuntimeException("ERR_AUCTIONEER_NAME_IS_TOKEN");

        Auctioneer auctioneer = Auctioneer.valueOf(username, password);
        long id = this.saveAuctioneer(auctioneer);
        return id;
    }

    @Override
    public long saveAuctioneer(Auctioneer auctioneer) {
        return auctioneerDao.saveAuctioneer(auctioneer);
    }

    @Override
    public boolean delAuctioneer(int id) {
        if (id <= 0) throw new RuntimeException("ERR_AUCTIONEER_ID_MUST_GREATER_THAN_ZERO");
        Auctioneer auctioneer = new Auctioneer();
        auctioneer.setId(id);
        return  auctioneerDao.delAuctioneer(auctioneer) > 0;
    }

    @Override
    public Auctioneer getAuctioneer(int id) {
        if (id <= 0) return null;
        Logger.i("getAuctioneer id:" + id);
        return auctioneerDao.getAuctioneer(id);
    }

    @Override
    public Auctioneer getAuctioneer(String username) {
        if (username == null || username.length() == 0) return null;
        return auctioneerDao.getAuctioneer(username);
    }

    @Override
    public List<Auctioneer> listAuctioneer(int pageIndex, int recordsPerPage) {
        if (pageIndex < 0) pageIndex = 0;
        if (recordsPerPage < 20) recordsPerPage = 20;
        return auctioneerDao.listAuctioneer(pageIndex, recordsPerPage);
    }

    @Override
    public boolean checkAuctioneer(String username, String password) {
        Auctioneer auctioneer = this.getAuctioneer(username);
        if (auctioneer == null) return  false;

        String yPassword = auctioneer.getPassword();
        if (yPassword == null) yPassword = "";
        if (password == null) password = "";

        return password.equals(yPassword);
    }
}
