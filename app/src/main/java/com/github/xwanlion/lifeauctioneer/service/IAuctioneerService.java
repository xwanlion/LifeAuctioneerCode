package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;

import java.util.List;

public interface IAuctioneerService {
    /**
     * save auctioneer to database.<br/>
     * will throw exceptions if username duplicate.
     *
     * @param username
     * @param password
     * @return the id of the new auctioneer which persisted on the database row.
     */
    public long saveAuctioneer(String username, String password);

    /**
     * save auctioneer to database.<br/>
     * will throw exceptions if username duplicate.
     *
     * @return the id of the new auctioneer which persisted on the database row.
     */
    public long saveAuctioneer(Auctioneer auctioneer);

    /**
     * delete auctioneer by id
     *
     * @param id
     * @return
     */
    public boolean delAuctioneer(int id);

    /**
     * get Auctioneer by id
     *
     * @param id
     * @return
     */
    public Auctioneer getAuctioneer(int id);

    /**
     * get Auctioneer by username;
     *
     * @param username
     * @return
     */
    public Auctioneer getAuctioneer(String username);

    /**
     * list Auctioneer with condition<br/>
     *
     * @param pageIndex : the index of the page, begins of 0, default to 0;
     * @param recordsPerPage: how many records each page, default to 20.
     * @return
     */
    public List<Auctioneer> listAuctioneer(int pageIndex, int recordsPerPage);

    /**
     * check auctioneer if exists
     * @param username
     * @param password
     * @return
     */
    public boolean checkAuctioneer(String username, String password);

}
