package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.model.app.BidderExpectingLot;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;

import java.util.List;
import java.util.Map;

public interface ILotService {
    /**
     * save lot to database.<br/>
     * will throw exception if duplicate name.<br/>
     *<br/>
     * @param activityId
     * @param name
     * @param startPrice
     * @param increment
     * @param purchasePrice
     * @param desc
     * @return the id of the new lot which has persisted on database row.
     */
    public long saveLot(int activityId, String name, long startPrice, long increment, long purchasePrice, String desc, String imageFile);

    /**
     * save lot to database.<br/>
     * will throw exception if duplicate name.<br/>
     * <br/>
     * @param lot
     * @return
     */
    public long saveLot(Lot lot);

    /**
     * delete lot by id
     *
     * @param id
     * @return
     */
    public boolean delLot(int id);

    /**
     * get Lot by id
     *
     * @param id
     * @return
     */
    public Lot getLot(int id);


    /**
     * list activity's Lot with condition<br/>
     *
     * @param activityId : activity id.
     * @param pageIndex : the index of the page, begins of 0, default to 0;
     * @param recordsPerPage: how many records each page, default to 20.
     * @return
     */
    public List<Lot> listLot(int activityId, int pageIndex, int recordsPerPage);

    /**
     * pointing lot that bidders can auction.
     *
     * @param lotId
     * @return
     */
    public Lot pointAuctionLot(int lotId);

    /**
     * save bidder's expecting Lot.
     *
     * @param activityId
     * @param bidderId
     * @param lotId
     * @param expectingPrice
     * @return
     */
    public boolean saveExpectingLot(int activityId, int bidderId, int lotId, long expectingPrice);

    /**
     * batch save bidder's expecting Lot.
     * @param activityId
     * @param bidderId
     * @param lots
     * @return
     */
    public boolean saveExpectingLot(int activityId, int bidderId, List<BidderExpectingLot> lots);

    /**
     * get bidder expecting lot
     * @param activityId
     * @param bidderId
     * @param lotId
     * @return
     */
    public BidderExpectingLot getExpectingLot(int activityId, int bidderId, int lotId);

    /**
     * achieve bidder's Expecting Lot
     * @param activityId
     * @param bidderId
     * @param lotId
     * @param price
     * @return
     */
    public boolean achieveExpectingLot(int activityId, int bidderId, int lotId, long price);

    /**
     * list bidder's expecting Lots.
     *
     * @param activityId
     * @param bidderId
     * @return
     */
    public List<Map<String, Object>> listExpectingLot(int activityId, int bidderId);

    /**
     * list all bidder's expecting Lots.
     *
     * @param activityId
     * @return
     */
    public List<Map<String, Object>> listExpectingLot(int activityId);

}
