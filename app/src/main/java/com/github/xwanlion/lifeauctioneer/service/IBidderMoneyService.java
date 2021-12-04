package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.model.app.BidderMoney;

import java.util.List;

public interface IBidderMoneyService {
    /**
     * create bidder money and save to database.
     *
     * @param activityId
     * @param bidderId
     * @return
     */
    public boolean createBidderMoney(int activityId, int bidderId);

    /**
     * create bidder money and save to database.
     * @param activityId
     * @param bidderId
     * @param age
     * @return
     */
    public long createBidderMoney(int activityId, int bidderId, int age);

    /**
     * update bidder's money
     * @param activityId
     * @param bidderId
     * @param amount
     * @return
     */
    public boolean reduceBidderMoney(int activityId, int bidderId, long amount);

    /**
     * delete bidder money by id
     *
     * @param id
     * @return
     */
    public boolean delBidderMoney(int id);

    /**
     * get bidder money by id
     *
     * @param id
     * @return
     */
    public BidderMoney getBidderMoney(int id);

    /**
     * get bidder's money by id.
     *
     * @param activityId
     * @param bidderId
     * @return
     */
    public BidderMoney getBidderMoney(int activityId, int bidderId);

    /**
     * list bidder's money with condition<br/>
     *
     * @param activityId
     * @return
     */
    public List<BidderMoney> listBidderMoney(int activityId);

    /**
     * list bidder's money with condition<br/>
     *
     * @param activityId : activity id.
     * @param pageIndex : the index of the page, begins of 0, default to 0;
     * @param recordsPerPage: how many records each page, default to 20.
     * @return
     */
    public List<BidderMoney> listBidderMoney(int activityId, int pageIndex, int recordsPerPage);

}
