package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.model.app.Auctions;

import java.util.List;

public interface IAuctionsService {
    /**
     * save activity to database.
     *
     * @param auctioneerId
     * @param amountPerAge
     * @return: the id of the new auctioneer which persist on database row.
     */
    public long saveActivity(int auctioneerId, int amountPerAge);

    /**
     * save activity to database.
     * @param auctioneerId
     * @param money
     * @return: the id of the new auctioneer which persisted on database row.
     */
    public long saveActivity(int auctioneerId, long money);

    /**
     * save activity to database.
     * @param auctions
     * @return: the id of the new auctioneer which persisted on database row.
     */
    public long saveActivity(Auctions auctions);


    /**
     * update activity which persisted in database.
     * @param id
     * @param auctioneerId
     * @param moneyCreationWay
     * @return
     */
    public boolean updateActivity(int id, int auctioneerId, int moneyCreationWay);

    /**
     * update activity which persisted in database.
     * @param auctions
     * @return
     */
    public boolean updateActivity(Auctions auctions);


    /**
     * set activity's currency
     * @param id
     * @param money
     * @return
     */
    public boolean setCurrency(int id, long money);

    /**
     * set activity's currency
     * @param id
     * @param amountPerAge
     * @return
     */
    public boolean setCurrency(int id, int amountPerAge);

    /**
     * delete activity by id.
     *
     * @param id
     * @return
     */
    public boolean delActivity(int id);

    /**
     * get Activity by id
     *
     * @param id
     * @return
     */
    public Auctions getActivity(int id);

    /**
     * get current activity which has start up.
     *
     * @return
     */
    public Auctions getCurrentActivity();


    /**
     * list activity.<br/>
     *
     * @param pageIndex : the index of the page, begins of 0, default to 0;
     * @param recordsPerPage: how many records each page, default to 20.
     * @return
     */
    public List<Auctions> listActivity(int pageIndex, int recordsPerPage);

    /**
     * create  new activity from old one by id.
     *
     * @param id
     * @return
     */
    public boolean copyActivity(int id);


    /**
     * start activity
     *
     * @param id
     * @return
     */
    public boolean startActivity(int id);

    /**
     * stop activity.
     *
     * @param id
     * @return
     */
    public boolean stopActivity(int id);


    /**
     * set current activity
     * @param newAuctions
     */
    void setCurrentActivity(Auctions newAuctions);

    /**
     * set current activity
     *
     * @param activityId
     */
    void setCurrentActivity(int activityId);
}
