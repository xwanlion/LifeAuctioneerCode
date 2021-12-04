package com.github.xwanlion.lifeauctioneer.service;

import com.github.xwanlion.lifeauctioneer.model.app.Bidder;

import java.util.List;

public interface IBidderService {
    /**
     * save bidder to database.<br/>
     * will throw exceptions if username duplicate.
     *
     * @param auctionsId
     * @param username
     * @param password
     * @return id of the new bidder which has persist on the database row.
     */
    public long saveBidder(int auctionsId, String username, String password);

    /**
     * save bidder to database.<br/>
     * will throw exceptions if username duplicate.
     *
     * @param bidder
     * @return id of the new bidder which has persist on the database row.
     */
    public long saveBidder(Bidder bidder);

    /**
     * delete bidder by id
     *
     * @param id
     * @return
     */
    public boolean delBidder(int id);

    /**
     * get Bidder by id
     *
     * @param id
     * @return
     */
    public Bidder getBidder(int id);

    /**
     * get Bidder by username;
     *
     * @param auctionsId
     * @param username
     * @return
     */
    public Bidder getBidder(int auctionsId, String username);

    /**
     * list Bidder with condition<br/>
     *
     * @param pageIndex : the index of the page, begins of 0, default to 0;
     * @param recordsPerPage: how many records each page, default to 20.
     * @return
     */
    public List<Bidder> listBidder(int pageIndex, int recordsPerPage);

    /**
     * list Bidder with condition<br/>
     * @param auctionsId
     * @param pageIndex
     * @param recordsPerPage
     * @return
     */
    public List<Bidder> listBidder(int auctionsId, int pageIndex, int recordsPerPage);

    /**
     * check if bidder exists
     *
     * @param auctionsId
     * @param username
     * @param password
     * @return
     */
    public boolean checkBidder(int auctionsId, String username, String password);

}
