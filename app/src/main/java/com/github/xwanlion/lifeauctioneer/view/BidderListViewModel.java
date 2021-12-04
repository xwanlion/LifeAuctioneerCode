package com.github.xwanlion.lifeauctioneer.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.github.xwanlion.lifeauctioneer.repository.BidderRepository;
import com.github.xwanlion.lifeauctioneer.repository.OnDeleteListener;
import com.github.xwanlion.lifeauctioneer.repository.OnQueryListListener;
import com.github.xwanlion.lifeauctioneer.repository.OnSaveListener;

import java.util.ArrayList;
import java.util.List;

public class BidderListViewModel extends AndroidViewModel implements OnQueryListListener<Bidder> {

    private BidderRepository repository;
    private MutableLiveData<List<RecyclerViewBaseMultiData>> multiLiveData = new MutableLiveData<>();

    public BidderListViewModel(@NonNull Application application) {
        super(application);
        repository = BeanManager.bidderRepository;
    }

    void  delete (int id, OnDeleteListener listener) {
        repository.delBidder(id, listener);
    }

    void  insert (Bidder bidder, OnSaveListener listener) {
        repository.saveBidder(bidder, listener);
    }

    LiveData<List<Bidder>> listBidder(int activityId) {
        return repository.listBidder(activityId);
    }

    LiveData<List<RecyclerViewBaseMultiData>> listData(int activityId) {
        OnQueryListListener listener = this;
        // get return data on onQueried method.
        repository.listAuctions(activityId, listener);
        return this.multiLiveData;

    }

    @Override
    public void onQueried(List<Bidder> list) {
        List<RecyclerViewBaseMultiData> data = new ArrayList<>();
        for (Bidder bidder : list) {
            data.add(bidder);
        }

        data.add(new RecyclerViewAddItemMultiData());
        multiLiveData.setValue(data);
        this.multiLiveData.setValue(data);
    }
}
