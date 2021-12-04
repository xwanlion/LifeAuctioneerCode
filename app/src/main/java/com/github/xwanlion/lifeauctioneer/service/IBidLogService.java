package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.model.app.BidLog;

import java.util.List;
import java.util.Map;

public interface IBidLogService {
    /**
     * save lot to database.
     * will throw exception if duplicate name.
     *
     * @param activityId
     * @param bidderId
     * @param lotId
     * @param price
     * @return
     */
    public boolean saveLog(int activityId, int bidderId, int lotId, long price);

    /**
     * save lot to database.
     * will throw exception if duplicate name.
     * @param log
     * @return
     */
    public boolean saveLog(BidLog log);

    /**
     * delete lot by id
     *
     * @param id
     * @return
     */
    public boolean delLog(int id);

    /**
     * delete activity's log
     *
     * @param activityId
     * @return
     */
    public boolean clearLog(int activityId);

    /**
     * get Bid's Log by id
     *
     * @param id
     * @return
     */
    public BidLog getLog(int id);

    /**
     * get Bid's Log by id.
     *
     * @param activityId
     * @param bidderId
     * @param lotId
     * @return
     */
    public BidLog getLog(int activityId, int bidderId, int lotId);

    /**
     * get highest offer's
     * @param activityId
     * @param lotId
     * @return
     */
    public BidLog getHighestOffer(int activityId, int lotId);

    /**
     * list bidder's Log with condition<br/>
     *
     * @param activityId
     * @param bidderId
     * @return
     */
    public List<BidLog> listLog(int activityId, int bidderId);

    /**
     * list activity's Log with condition<br/>
     *
     * @param activityId : activity id.
     * @param pageIndex : the index of the page, begins of 0, default to 0;
     * @param recordsPerPage: how many records each page, default to 20.
     * @return
     */
    public List<BidLog> listLog(int activityId, int pageIndex, int recordsPerPage);

}
