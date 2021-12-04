package com.github.xwanlion.lifeauctioneer.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.dao.BidderDao;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;

import java.util.List;

public class BidderRepository implements OnQueryListListener<Bidder> {

    private BidderDao bidderDao = null;
    private MutableLiveData<List<Bidder>> bidderLiveData = null;
    private BidderRepository.QuerySycTask querySycTask = null;

    public BidderRepository() {
        IDatabase database = IDatabase.getDatabase();
        bidderDao = database.getBidderDao();
    }

    public void saveBidder(Bidder bidder, OnSaveListener callback) {
        new InsertSycTask(this.bidderDao, callback).execute(bidder);
    }


    public void delBidder(int id, OnDeleteListener callback) {
        Bidder bidder = new Bidder();
        bidder.setId(id);
        new DeleteSycTask(this.bidderDao, callback).execute(bidder);
    }

    public LiveData<List<Bidder>> listBidder(Integer auctionsId) {
        if (bidderLiveData == null) bidderLiveData = new MutableLiveData<>();

        // query data synchronized, get return data on callback, see onQueried Method
        new BidderRepository.QuerySycTask(auctionsId, this).execute();
        return this.bidderLiveData;
    }

    public void listAuctions(Integer auctionsId, OnQueryListListener listener) {
        new BidderRepository.QuerySycTask(auctionsId, listener).execute();
    }

    @Override
    public void onQueried(List<Bidder> list) {
        bidderLiveData.setValue(list);
    }

    public  static class  DeleteSycTask extends AsyncTask<Bidder, Void, Void> {
        private Bidder bidder = null;
        private BidderDao dao = null;
        private OnDeleteListener callback = null;

        public DeleteSycTask(BidderDao dao, OnDeleteListener callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Bidder ...bidders) {
            bidder = bidders[0];
            dao.delBidder(bidder);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback == null) return;
            callback.onDeleted(bidder);
        }
    }

    public static class InsertSycTask extends AsyncTask<Bidder, Void, Void> {
        private long id = 0L;
        private Bidder bidder = null;
        private BidderDao dao = null;
        private OnSaveListener callback = null;

        public InsertSycTask(BidderDao dao, OnSaveListener callback) {
            this.dao = dao;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Bidder ...bidders) {
            bidder = bidders[0];
            if (bidder == null) return null;

            id = dao.saveBidder(bidders[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (callback == null) return;
            this.callback.onSaved(bidder, id);
        }
    }

    public static class QuerySycTask extends AsyncTask<Void, Void, Void> {
        private Integer auctionsId = 0;
        private List<Bidder> auctionsList;
        private OnQueryListListener listener;

        public QuerySycTask(Integer auctionsId, OnQueryListListener listener) {
            this.auctionsId = auctionsId;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void...voids) {
            int pageIndex = 0;
            int recordsPerPage = 200;
            auctionsList = BeanManager.bidderService.listBidder(auctionsId, pageIndex, recordsPerPage);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onQueried(auctionsList);

            this.auctionsId = null;
            this.auctionsList = null;
            this.listener = null;
        }
    }
}
