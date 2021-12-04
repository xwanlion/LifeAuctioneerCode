package com.github.xwanlion.lifeauctioneer.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.dao.LotDao;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;

import java.util.List;

public class LotRepository implements OnQueryListListener<Lot> {

    private LotDao lotDao = null;
    private OnSaveListener saveCallback = null;
    private MutableLiveData<List<Lot>> lotLiveData = null;

    public LotRepository() {
        IDatabase database = IDatabase.getDatabase();
        lotDao = database.getLotDao();
    }

    public void saveLot(Lot lot, OnSaveListener callback) {
        new InsertSycTask(this.lotDao, callback).execute(lot);
    }


    public void delLot(int id, OnDeleteListener callback) {
        Lot lot = new Lot();
        lot.setId(id);
        new DeleteSycTask(this.lotDao, callback).execute(lot);
    }


    public LiveData<List<Lot>> listLot(Integer activityId) {
        if (lotLiveData == null) lotLiveData = new MutableLiveData<>();
        // query data synchronized, get return data on callback, see onQueried Method
        new LotRepository.QuerySycTask(activityId, this).execute();
        return this.lotLiveData;
    }

    public void listData(Integer auctionsId, OnQueryListListener listener) {
        new LotRepository.QuerySycTask(auctionsId, listener).execute();
    }

    @Override
    public void onQueried(List<Lot> list) {
        lotLiveData.setValue(list);
    }

    public  static class  DeleteSycTask extends AsyncTask<Lot, Void, Void> {
        private Lot lot = null;
        private LotDao dao = null;
        private OnDeleteListener callback = null;

        public DeleteSycTask(LotDao dao, OnDeleteListener callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Lot ...lots) {
            lot = lots[0];
            dao.delLot(lot);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback == null) return;
            callback.onDeleted(lot);
        }
    }

    public static class InsertSycTask extends AsyncTask<Lot, Void, Void> {
        private long id = 0L;
        private Lot lot = null;
        private LotDao dao = null;
        private OnSaveListener callback = null;

        public InsertSycTask(LotDao dao, OnSaveListener callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Lot ...lots) {
            lot = lots[0];
            if (lot == null) return  null;

            id = dao.saveLot(lots[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback == null) return;
            this.callback.onSaved(lot, id);
        }
    }

    public static class QuerySycTask extends AsyncTask<Void, Void, Void> {
        private Integer auctionsId = 0;
        private List<Lot> lotList;
        private OnQueryListListener listener;

        public QuerySycTask(Integer auctionsId, OnQueryListListener listener) {
            this.auctionsId = auctionsId;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void...voids) {
            int pageIndex = 0;
            int recordsPerPage = 200;
            lotList = BeanManager.lotService.listLot(auctionsId, pageIndex, recordsPerPage);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onQueried(lotList);
        }
    }

}
