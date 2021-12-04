package com.github.xwanlion.lifeauctioneer.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;
import com.github.xwanlion.lifeauctioneer.repository.LotRepository;
import com.github.xwanlion.lifeauctioneer.repository.OnDeleteListener;
import com.github.xwanlion.lifeauctioneer.repository.OnQueryListListener;
import com.github.xwanlion.lifeauctioneer.repository.OnSaveListener;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class LotListViewModel extends AndroidViewModel implements OnQueryListListener<Lot> {

    private LotRepository repository;
    private MutableLiveData<List<RecyclerViewBaseMultiData>> multiLiveData = new MutableLiveData<>();

    public LotListViewModel(@NonNull Application application) {
        super(application);
        repository = BeanManager.lotRepository;
    }

    void  delete (int id, OnDeleteListener listener) {
        repository.delLot(id, listener);
    }

    void  insert (Lot lot, OnSaveListener listener) {
        repository.saveLot(lot, listener);
    }

    LiveData<List<Lot>> listLot(int activityId) {
        return repository.listLot(activityId);
    }

    LiveData<List<RecyclerViewBaseMultiData>> listData(int auctionsId) {
        Logger.i("auctionsId:" + auctionsId);
        OnQueryListListener listener = this;
        // get return data on onQueried method.
        repository.listData(auctionsId, listener);
        return this.multiLiveData;

    }

    @Override
    public void onQueried(List<Lot> list) {
        List<RecyclerViewBaseMultiData> data = new ArrayList<>();
        for (Lot lot : list) {
            data.add(lot);
        }

        data.add(new RecyclerViewAddItemMultiData());
        this.multiLiveData.setValue(data);

    }
}
