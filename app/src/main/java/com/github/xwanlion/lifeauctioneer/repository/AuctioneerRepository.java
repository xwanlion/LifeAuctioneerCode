package com.github.xwanlion.lifeauctioneer.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.dao.AuctioneerDao;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.model.JsonPo;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.util.Logger;
import com.github.xwanlion.lifeauctioneer.view.AuctioneerSettingFragment;

import java.util.List;

public class AuctioneerRepository implements OnQueryListListener<Auctioneer>, OnQueryObjectListener<Auctioneer> {

    private AuctioneerDao auctioneerDao = null;
    private MutableLiveData<List<Auctioneer>> listLiveData = null;
    private MutableLiveData<Auctioneer> objLiveData = null;

    public AuctioneerRepository() {
        IDatabase database = IDatabase.getDatabase();
        auctioneerDao = database.getAuctioneerDao();
    }

    public void saveAuctioneer(Auctioneer auctioneer, OnSaveListener listener) {
        new InsertSycTask(this.auctioneerDao, listener).execute(auctioneer);
    }

    /**
     * save auctioneer to database<br/>
     * if id greater than zero, it will update the old one which has persisted in database.
     * @param id
     * @param username
     * @param password
     * @param listener
     */
    public void saveAuctioneer(int activityId, int id, String username, String password, OnSaveAuctioneerListener listener) {
        Auctioneer auctioneer = new Auctioneer();
        auctioneer.setId(id);
        auctioneer.setUsername(username);
        auctioneer.setPassword(password);
        new SaveSycTask(activityId, auctioneer, listener).execute();
    }

    /**
     * save auctioneer to database<br/>
     * if id greater than zero, it will update the old one which has persisted in database.
     * @param id
     * @param username
     * @param password
     * @param listener
     */
    public void saveAuctioneer(int id, String username, String password, OnSaveListener listener) {
        Auctioneer auctioneer = new Auctioneer();
        auctioneer.setId(id);
        auctioneer.setUsername(username);
        auctioneer.setPassword(password);
        this.saveAuctioneer(auctioneer, listener);
    }

    public void saveAuctioneer(String username, String password, OnSaveListener listener) {
        // set id to zero to add new one.
        int id = 0;
        this.saveAuctioneer(0, username, password, listener);
    }

    public void getAuctioneer(int auctioneerId, OnQueryObjectListener listener) {
        if (objLiveData == null) objLiveData = new MutableLiveData<>();
        // query data synchronized, get return data on callback, see onQueried Method
        new AuctioneerRepository.GetObjectSycTask(listener).execute(auctioneerId);
    }

    public LiveData<Auctioneer> getAuctioneer(int auctioneerId) {
        if (objLiveData == null) objLiveData = new MutableLiveData<>();
        // query data synchronized, get return data on callback, see onQueried Method
        new AuctioneerRepository.GetObjectSycTask(this).execute(auctioneerId);
        return objLiveData;
    }

    public void delAuctioneer(int auctionsId, OnDeleteListener listener) {
        Auctioneer auctioneer = new Auctioneer();
        auctioneer.setId(auctionsId);
        new DeleteSycTask(this.auctioneerDao, listener).execute(auctioneer);
    }

    public LiveData<List<Auctioneer>> listAuctions() {
         //return this.liveData;
        if (listLiveData == null) listLiveData = new MutableLiveData<>();
        // query data synchronized, get return data on callback, see onQueried Method
        new AuctioneerRepository.QuerySycTask(this).execute();
        return this.listLiveData;
    }

    public void listAuctions(OnQueryListListener listener) {
        new AuctioneerRepository.QuerySycTask(listener).execute();
    }

    @Override
    public void onQueried(List<Auctioneer> list) {
        listLiveData.setValue(list);
    }

    @Override
    public void onQueried(Auctioneer obj) {
        this.objLiveData.setValue(obj);
    }

    public  static class  DeleteSycTask extends AsyncTask<Auctioneer, Void, Void> {
        private Auctioneer auctioneer = null;
        private AuctioneerDao dao = null;
        private OnDeleteListener listener = null;

        public DeleteSycTask(AuctioneerDao dao, OnDeleteListener listener) {
            this.dao = dao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Auctioneer...activities) {
            auctioneer = activities[0];
            dao.delAuctioneer(auctioneer);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onDeleted(auctioneer);
        }

    }

    public static class InsertSycTask extends AsyncTask<Auctioneer, Void, Void> {
        private long id = 0;
        private Auctioneer auctioneer = null;
        private AuctioneerDao dao = null;
        private OnSaveListener listener;

        public InsertSycTask(AuctioneerDao dao, OnSaveListener listener) {
            this.dao = dao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Auctioneer...activities) {
            auctioneer = activities[0];
            id = dao.saveAuctioneer(auctioneer);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onSaved(auctioneer, id);
        }

    }

    public class SaveSycTask extends AsyncTask<Void, Void, Void> implements OnSaveAuctioneerListener<Auctioneer> {
        private int activityId = 0;
        private Auctions auctions;
        private Auctioneer auctioneer;
        private OnSaveAuctioneerListener listener;

        public SaveSycTask(int activityId, Auctioneer auctioneer, OnSaveAuctioneerListener listener) {
            this.activityId = activityId;
            this.auctioneer = auctioneer;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void...activities) {
            PersistAuctioneerRunnable runnalble = null;
            runnalble = new PersistAuctioneerRunnable(activityId, auctioneer , listener);
            IDatabase.getDatabase().runInTransaction(runnalble);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onSaved(this.auctioneer, this.auctions);
        }

        @Override
        public void onSaved(Auctioneer auctioneer, Auctions auctions) {
            this.auctions = auctions;
            this.auctioneer = auctioneer;
        }

        @Override
        public void onSaved(Auctioneer auctioneer, long id) {}

    }

    class PersistAuctioneerRunnable implements Runnable {
        private int activityId = 0;
        private Auctioneer auctioneer;
        private OnSaveAuctioneerListener listener;

        public PersistAuctioneerRunnable(int activityId, Auctioneer auctioneer, OnSaveAuctioneerListener listener) {
            this.activityId = activityId;
            this.auctioneer = auctioneer;
            this.listener = listener;
        }

        @Override
        public void run() {
            long auctioneerId = BeanManager.auctioneerService.saveAuctioneer(auctioneer);
            this.auctioneer.setId((int) auctioneerId);

            long actId = 0;
            if (activityId == 0) {
                actId =  BeanManager.auctionsService.saveActivity((int) auctioneerId, System.DEFAULT_FIXED_MONEY_AMOUNT);
                Auctions auctions = BeanManager.auctionsService.getActivity((int) actId);
                this.listener.onSaved(this.auctioneer, auctions);
            } else {
                Auctions auctions = BeanManager.auctionsService.getActivity(activityId);
                this.listener.onSaved(this.auctioneer, auctions);
            }
        }

    }

    public static class QuerySycTask extends AsyncTask<Void, Void, Void> {
        private List<Auctioneer> auctioneerList;
        private OnQueryListListener listener;

        public QuerySycTask(OnQueryListListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void...voids) {
            int pageIndex = 0;
            int recordsPerPage = 200;
            auctioneerList = BeanManager.auctioneerService.listAuctioneer(pageIndex, recordsPerPage);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onQueried(auctioneerList);
        }

    }

    public static class GetObjectSycTask extends AsyncTask<Integer, Void, Void> {
        private Auctioneer auctioneer;
        private OnQueryObjectListener listener;

        public GetObjectSycTask(OnQueryObjectListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Integer...ids) {
            Logger.i("auctioneerId:" + ids[0]);
            auctioneer = BeanManager.auctioneerService.getAuctioneer(ids[0]);
            Logger.i("query auctioneer:" + auctioneer);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            Logger.i("executed auctioneer is null:" + (auctioneer == null));
            this.listener.onQueried(auctioneer);
        }
    }

}
