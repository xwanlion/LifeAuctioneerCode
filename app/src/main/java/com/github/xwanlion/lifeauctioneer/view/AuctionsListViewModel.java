package com.github.xwanlion.lifeauctioneer.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.repository.AuctionsRepository;
import com.github.xwanlion.lifeauctioneer.repository.OnDeleteListener;
import com.github.xwanlion.lifeauctioneer.repository.OnQueryListListener;
import com.github.xwanlion.lifeauctioneer.repository.OnSaveListener;

import java.util.ArrayList;
import java.util.List;

public class AuctionsListViewModel extends AndroidViewModel implements OnQueryListListener<Auctions> {

    private List<Auctions> auctionsList = new ArrayList<>();
    private LiveData<List<Auctions>> auctionsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<RecyclerViewBaseMultiData>> multiLiveData = new MutableLiveData<>();

    private AuctionsRepository repository;

    public AuctionsListViewModel(@NonNull Application application) {
        super(application);
        repository = BeanManager.auctionsRepository;
    }

    void  delete (int id, OnDeleteListener listener) {
        repository.delAuctions(id, listener);
    }

    void  insert (Auctions auctions, OnSaveListener listener) {
        repository.saveAuctions(auctions, listener);
    }

    LiveData<List<Auctions>> listAuctions() {
        this.auctionsLiveData = repository.listAuctions();
        return this.auctionsLiveData;
    }

    LiveData<List<RecyclerViewBaseMultiData>> listData() {
        OnQueryListListener listener = this;
        // get return data on onQueried method.
        repository.listAuctions(listener);
        return this.multiLiveData;
    }

    @Override
    public void onQueried(List<Auctions> list) {
        List<RecyclerViewBaseMultiData> data = new ArrayList<>();
        for (Auctions auctions : list) {
            data.add(auctions);
        }

        data.add(new RecyclerViewAddItemMultiData());
        multiLiveData.setValue(data);
        this.multiLiveData.setValue(data);

    }
}
