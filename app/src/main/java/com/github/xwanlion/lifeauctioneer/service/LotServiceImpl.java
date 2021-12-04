package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.dao.AuctioneerDao;
import com.github.xwanlion.lifeauctioneer.dao.ExpectingLotDao;
import com.github.xwanlion.lifeauctioneer.dao.LotDao;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.github.xwanlion.lifeauctioneer.model.app.BidderExpectingLot;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotServiceImpl implements ILotService {

    private LotDao lotDao = null;
    private ExpectingLotDao expectingLotDao = null;

    //private Lot currentAuctionLot = null;

    public LotServiceImpl() {
        IDatabase database = IDatabase.getDatabase();
        lotDao = database.getLotDao();
        expectingLotDao = database.getExpectingLotDao();
    }

    @Override
    public long saveLot(int activityId, String name, long startPrice, long increment, long purchasePrice, String desc, String imageFile) {
        if (activityId <= 0) throw new RuntimeException("ERR_INVALID_ACTIVITY_ID");
        if (name == null || name.length() == 0) throw new RuntimeException("ERR_NAME_CAN_NOT_BE_NULL");

        Lot lot = new Lot();
        lot.setActivityId(activityId);
        lot.setName(name);
        lot.setStartPrice(startPrice);
        lot.setIncrement(increment);
        lot.setPurchasePrice(purchasePrice);
        lot.setDesc(desc);
        lot.setImageFile(imageFile);

        long id = this.saveLot(lot);
        return id;

    }

    @Override
    public long saveLot(Lot lot) {
        return lotDao.saveLot(lot);
    }

    @Override
    public boolean delLot(int id) {
        Lot lot = new Lot();
        lot.setId(id);
        long r = lotDao.delLot(lot);
        return r > 0;
    }

    @Override
    public Lot getLot(int id) {
        return lotDao.getLot(id);
    }

    @Override
    public List<Lot> listLot(int activityId, int pageIndex, int recordsPerPage) {
        if (pageIndex <= 0) pageIndex = 0;
        if (recordsPerPage < 20) recordsPerPage = 20;
        return lotDao.listLot(activityId, pageIndex, recordsPerPage);
    }

//    @Override
//    public Lot pointAuctionLot(int lotId) {
//        currentAuctionLot = this.getLot(lotId);
//        return currentAuctionLot;
//    }

    @Override
    public Lot pointAuctionLot(int lotId) {
        return this.getLot(lotId);
    }

    @Override
    public boolean saveExpectingLot(int activityId, int bidderId, int lotId, long expectingPrice) {
        BidderExpectingLot lot = this.getExpectingLot(activityId, bidderId, lotId);
        if (lot == null) lot = new BidderExpectingLot();
        lot.setActivityId(activityId);
        lot.setBidderId(bidderId);
        lot.setLotId(lotId);
        lot.setExpectingPrice(expectingPrice);

        long id = expectingLotDao.saveLot(lot);
        return id > 0;
    }

    @Override
    public boolean saveExpectingLot(int activityId, int bidderId, List<BidderExpectingLot> lots) {
        if (activityId <= 0) return false;
        if (bidderId <= 0) return false;
        if (lots == null || lots.size() == 0) return true;

        for (BidderExpectingLot lot : lots) {
            boolean success = this.saveExpectingLot(activityId, bidderId, lot.getLotId(), lot.getExpectingPrice());
            if (!success) throw new RuntimeException("ERR_BATCH_PERSIS_EXPECTING_LOT_FAIL");
        }

        return true;
    }

    @Override
    public BidderExpectingLot getExpectingLot(int activityId, int bidderId, int lotId) {
        return expectingLotDao.getLot(activityId, bidderId, lotId);
    }

    @Override
    public boolean achieveExpectingLot(int activityId, int bidderId, int lotId, long price) {
        BidderExpectingLot lot = this.getExpectingLot(activityId, bidderId, lotId);
        if (lot != null) {
            lot.setPurchasePrice(price);
            long id = expectingLotDao.updateLot(lot);
            return id > 0;
        } else {
            BidderExpectingLot expectingLot = new BidderExpectingLot();
            expectingLot.setExpectingPrice(0L);
            expectingLot.setLotId(lotId);
            expectingLot.setPurchasePrice(price);
            expectingLot.setBidderId(bidderId);
            expectingLot.setActivityId(activityId);
            long id = expectingLotDao.saveLot(expectingLot);
            return id > 0;
        }
    }

    @Override
    public List<Map<String, Object>> listExpectingLot(int activityId, int bidderId) {
        List<BidderExpectingLot> expectingLots = expectingLotDao.listLot(activityId, bidderId);
        if (expectingLots == null && expectingLots.size() == 0) return null;
        return toMaps(expectingLots);
    }

    @Override
    public List<Map<String, Object>> listExpectingLot(int activityId) {
        List<BidderExpectingLot> expectingLots = expectingLotDao.listLot(activityId);
        if (expectingLots == null && expectingLots.size() == 0) return null;
        return toMaps(expectingLots);
    }

    private List<Map<String, Object>> toMaps(List<BidderExpectingLot> expectingLots) {
        List<Map<String, Object>> lots = new ArrayList<>();
        for (BidderExpectingLot expectingLot : expectingLots) {
            Lot lot = this.getLot(expectingLot.getId());
            if (lot == null) continue;

            Map<String, Object> map = Lot.toMap(lot);
            map.put("expectingPrice", expectingLot.getExpectingPrice());
            map.put("purchasePrice", expectingLot.getPurchasePrice());
            lots.add(map);

        }
        return lots;
    }
}
