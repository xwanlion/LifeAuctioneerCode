package com.github.xwanlion.lifeauctioneer.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.github.xwanlion.lifeauctioneer.dao.BidderMoneyDao;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.model.app.BidderMoney;

import java.util.List;

public class BidderMoneyRepository {

    private BidderMoneyDao bidderMoneyDao = null;
    private LiveData<List<BidderMoney>> liveData = null;

    public BidderMoneyRepository() {
        IDatabase database = IDatabase.getDatabase();
        bidderMoneyDao = database.getBidderMoneyDao();
    }

    public void saveBidderMoney(BidderMoney bidderMoney, OnSaveListener listener) {
        new InsertSycTask(this.bidderMoneyDao, listener).execute(bidderMoney);
    }

    public void delBidderMoney(int id, OnDeleteListener listener) {
        BidderMoney bidderMoney = new BidderMoney();
        bidderMoney.setId(id);
        new DeleteSycTask(this.bidderMoneyDao, listener).execute(bidderMoney);
    }

    public void delBidderMoney(int activityId, int bidderId, OnDeleteListener listener) {
        BidderMoney bidderMoney = new BidderMoney();
        bidderMoney.setActivityId(activityId);
        bidderMoney.setBidderId(bidderId);
        new DeleteSycTask(this.bidderMoneyDao, listener).execute(bidderMoney);
    }

    public LiveData<List<BidderMoney>> listData(long auctionId) {
         return bidderMoneyDao.listBidderMoney(auctionId);
    }

    public  static class  DeleteSycTask extends AsyncTask<BidderMoney, Void, Void> {
        private BidderMoney bidderMoney = null;
        private BidderMoneyDao dao = null;
        private OnDeleteListener listener = null;

        public DeleteSycTask(BidderMoneyDao dao, OnDeleteListener listener) {
            this.dao = dao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(BidderMoney...activities) {
            bidderMoney = activities[0];
            if (bidderMoney.getId() > 0) {
                dao.delBidderMoney(bidderMoney);
            } else if (bidderMoney.getActivityId() > 0 && bidderMoney.getBidderId() > 0){
                dao.delBidderMoney(bidderMoney.getActivityId(), bidderMoney.getBidderId());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onDeleted(bidderMoney);
        }

    }

    public static class InsertSycTask extends AsyncTask<BidderMoney, Void, Void> {
        private long id = 0;
        private BidderMoney bidderMoney = null;
        private BidderMoneyDao dao = null;
        private OnSaveListener listener;

        public InsertSycTask(BidderMoneyDao dao, OnSaveListener listener) {
            this.dao = dao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(BidderMoney...activities) {
            bidderMoney = activities[0];
            id = dao.saveBidderMoney(bidderMoney);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onSaved(bidderMoney, id);

            this.dao = null;
            this.listener = null;
        }
    }

}
