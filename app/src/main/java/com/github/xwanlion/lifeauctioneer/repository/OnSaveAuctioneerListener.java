package com.github.xwanlion.lifeauctioneer.repository;

import com.github.xwanlion.lifeauctioneer.model.JsonPo;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;

public interface OnSaveAuctioneerListener<T> extends OnSaveListener<T> {
    public void onSaved(T auctioneer, Auctions auctions);
}
