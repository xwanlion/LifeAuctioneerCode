package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.dao.BidLogDao;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.model.app.BidLog;

import java.util.List;

public class BidLogServiceImpl implements IBidLogService {

    private BidLogDao bidLogDao = null;

    public BidLogServiceImpl() {
        IDatabase database = IDatabase.getDatabase();
        bidLogDao = database.getBidLogDao();
    }

    @Override
    public boolean saveLog(int activityId, int bidderId, int lotId, long price) {
        if (activityId <= 0) return false;
        if (bidderId <= 0) return false;
        if (lotId <= 0) return false;
        if (price <= 0) return false;

        BidLog log = new BidLog();
        log.setActivityId(activityId);
        log.setBidderId(bidderId);
        log.setLotId(lotId);
        log.setPrice(price);
        log.setTime(System.currentTimeMillis());

        return this.saveLog(log);

    }

    @Override
    public boolean saveLog(BidLog log) {
        long id = bidLogDao.saveBidLog(log);
        return id > 0;
    }


    @Override
    public boolean delLog(int id) {
        BidLog bidLog = new BidLog();
        bidLog.setId(id);
        long r = bidLogDao.delBidLog(bidLog);
        return r > 0;
    }

    @Override
    public boolean clearLog(int activityId) {
        bidLogDao.delBidLog(activityId);
        return true;
    }

    @Override
    public BidLog getLog(int id) {
        return bidLogDao.getBidLog(id);
    }

    @Override
    public BidLog getLog(int activityId, int bidderId, int lotId) {
        return bidLogDao.getBidLog(activityId, bidderId, lotId);
    }

    @Override
    public BidLog getHighestOffer(int activityId, int lotId) {
        return bidLogDao.getHighestOffer(activityId, lotId);
    }

    @Override
    public List<BidLog> listLog(int activityId, int bidderId) {
        return bidLogDao.listBidLog(activityId, bidderId);
    }

    @Override
    public List<BidLog> listLog(int activityId, int pageIndex, int recordsPerPage) {
        if (pageIndex <= 0) pageIndex = 0;
        if (recordsPerPage < 20) recordsPerPage = 20;
        return bidLogDao.listBidLog(activityId, pageIndex, recordsPerPage);
    }

}
