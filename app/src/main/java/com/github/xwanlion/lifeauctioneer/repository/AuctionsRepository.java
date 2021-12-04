package com.github.xwanlion.lifeauctioneer.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.conf.ActivitySate;
import com.github.xwanlion.lifeauctioneer.dao.AuctionsDao;
import com.github.xwanlion.lifeauctioneer.dao.BidderDao;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.dao.LotDao;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;

import java.util.List;

public class AuctionsRepository implements OnQueryListListener<Auctions> {

    private AuctionsDao auctionsDao = null;
    private BidderDao bidderDao = null;
    private LotDao lotDao = null;
    private MutableLiveData<List<Auctions>> liveData = null;

    public AuctionsRepository() {
        IDatabase database = IDatabase.getDatabase();
        auctionsDao = database.getAuctionsDao();
        bidderDao = database.getBidderDao();
        lotDao = database.getLotDao();
    }

    public void saveAuctions(Auctions auctions, OnSaveListener listener) {
        new InsertSycTask(this.auctionsDao, listener).execute(auctions);
    }

    public void saveAuctions(int id, int auctioneerId, String auctioneer, int moneyCreationWay, long money, int perAge, OnSaveListener listener) {
        if (auctioneerId <= 0) throw new RuntimeException("ERR_AUCTIONEER_ID_CAN_NOT_BE_NULL");
        if (auctioneer == null) throw new RuntimeException("ERR_AUCTIONEER_CAN_NOT_BE_NULL");

        Auctions auctions = new Auctions();
        auctions.setId(id);
        auctions.setAuctioneerId(auctioneerId);
        auctions.setAuctioneerName(auctioneer);
        auctions.setMoneyCreationWay(moneyCreationWay);
        auctions.setMoney(money);
        auctions.setAmountPerAge(perAge);
        auctions.setState(ActivitySate.STOPPING);
        auctions.setDate(java.lang.System.currentTimeMillis());
        auctions.setNeedLogin(false);
        this.saveAuctions(auctions, listener);
    }

    public void saveAuctions(int auctioneerId, String auctioneer, int moneyCreationWay, long money, int perAge, OnSaveListener listener) {
        this.saveAuctions(0, auctioneerId, auctioneer, moneyCreationWay, money, perAge, listener);
    }

    public void updateAuctions(Auctions auctions, OnUpdateListener listener) {
        new UpdateSycTask(this.auctionsDao, listener).execute(auctions);
    }


    public void delAuctions(int id, OnDeleteListener listener) {
        Auctions auctions = new Auctions();
        auctions.setId(id);
        new DeleteSycTask(this.auctionsDao, listener).execute(auctions);
    }


    public LiveData<List<Auctions>> listAuctions() {
        if (liveData == null) liveData = new MutableLiveData<>();
        // query data synchronized, get return data on callback, see onQueried Method
        new AuctionsRepository.QuerySycTask(this).execute();
        return this.liveData;
    }

    public void listAuctions(OnQueryListListener listener) {
        new AuctionsRepository.QuerySycTask(listener).execute();
    }


    public boolean copyAuctions(Auctions auctions, OnSaveListener listener) {
        new CopySycTask(this.auctionsDao, this.bidderDao, this.lotDao, listener).execute(auctions);
        return true;

    }

    public boolean startAuctions(Auctions auctions, OnUpdateListener listener) {
//        Logger.i("auctions.isNeedLogin():" + auctions.isNeedLogin());
        Auctions newAuctions = auctions.clone();
        newAuctions.setId(auctions.getId());
        if (auctions.getState() < ActivitySate.READY) {
            newAuctions.setState(ActivitySate.READY);
        }

        this.updateAuctions(newAuctions, listener);
        BeanManager.auctionsService.setCurrentActivity(newAuctions);
        return true;
    }

    public boolean stopAuctions(Auctions auctions, OnUpdateListener listener) {
        Auctions newAuctions = auctions.clone();
        // newAuctions.setState(ActivitySate.STOPPING);
        this.updateAuctions(newAuctions, listener);
        BeanManager.auctionsService.setCurrentActivity(null);
        return true;
    }

    @Override
    public void onQueried(List<Auctions> list) {
        liveData.setValue(list);
    }

    public  static class  DeleteSycTask extends AsyncTask<Auctions, Void, Void> {
        private Auctions auctions = null;
        private AuctionsDao dao = null;
        private OnDeleteListener listener = null;

        public DeleteSycTask(AuctionsDao dao, OnDeleteListener listener) {
            this.dao = dao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Auctions...activities) {
            auctions = activities[0];
            dao.delAuctions(auctions);
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onDeleted(auctions);
        }
    }

    public  static class  UpdateSycTask extends AsyncTask<Auctions, Void, Void> {

        private Auctions auctions = null;
        private AuctionsDao dao = null;
        private OnUpdateListener listener = null;

        public UpdateSycTask(AuctionsDao dao, OnUpdateListener listener) {
            this.dao = dao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Auctions...activities) {
            auctions = activities[0];
            dao.updateAuctions(auctions);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onUpdated(auctions);
        }

    }

    public static class InsertSycTask extends AsyncTask<Auctions, Void, Void> {
        private long id = 0;
        private Auctions auctions = null;
        private AuctionsDao dao = null;
        private OnSaveListener listener;

        public InsertSycTask(AuctionsDao dao, OnSaveListener listener) {
            this.dao = dao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Auctions...activities) {
            auctions = activities[0];
            id = dao.saveAuctions(auctions);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onSaved(auctions, id);
        }
    }

    public static class CopySycTask extends AsyncTask<Auctions, Void, Void> {
        private long newActivityId = 0L;
        private Auctions auctions = null;
        private AuctionsDao activityDao = null;
        private BidderDao bidderDao = null;
        private LotDao lotDao = null;
        private OnSaveListener listener;

        public CopySycTask(AuctionsDao activityDao, BidderDao bidderDao, LotDao lotDao, OnSaveListener listener) {
            this.activityDao = activityDao;
            this.bidderDao = bidderDao;
            this.lotDao = lotDao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Auctions...activities) {
            this.auctions = activities[0];

            IDatabase.getDatabase().runInTransaction(
                new Runnable() {
                    @Override
                    public void run() {
                        exe(auctions);
                    }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onSaved(auctions, newActivityId);
        }

        protected void exe (Auctions auctions) {
            Auctions cloneAuctions = auctions.clone();
            cloneAuctions.setDate(System.currentTimeMillis());
            cloneAuctions.setState(ActivitySate.STOPPING);

            newActivityId = activityDao.saveAuctions(cloneAuctions);
            if (newActivityId <= 0) throw new RuntimeException("ERR_COPY_ACTIVITY_OBJECT_FAIL");

            bidderDao.copyBidder(auctions.getId(), newActivityId);
            lotDao.copyLot(auctions.getId(), newActivityId);
        }
    }

    public static class QuerySycTask extends AsyncTask<Void, Void, Void> {
        private List<Auctions> auctionsList;
        private OnQueryListListener listener;

        public QuerySycTask(OnQueryListListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void...voids) {
            int pageIndex = 0;
            int recordsPerPage = 200;
            auctionsList = BeanManager.auctionsService.listActivity(pageIndex, recordsPerPage);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onQueried(auctionsList);
        }
    }


}
