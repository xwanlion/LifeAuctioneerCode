package com.github.xwanlion.lifeauctioneer.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.github.xwanlion.lifeauctioneer.dao.BidLogDao;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.model.app.BidLog;

import java.util.List;

public class BidLogRepository {

    private BidLogDao bidLogDao = null;
    private LiveData<List<BidLog>> liveData = null;

    public BidLogRepository() {
        IDatabase database = IDatabase.getDatabase();
        bidLogDao = database.getBidLogDao();
    }

    public void delBidLog(int id, OnDeleteListener listener) {
        BidLog bidLog = new BidLog();
        bidLog.setId(id);
        new DeleteSycTask(this.bidLogDao, listener).execute(bidLog);
    }

    public void delBidLog(int activityId, int bidderId, OnDeleteListener listener) {
        BidLog bidLog = new BidLog();
        bidLog.setActivityId(activityId);
        bidLog.setBidderId(bidderId);
        new DeleteSycTask(this.bidLogDao, listener).execute(bidLog);
    }

    public void clearBidLog(int activityId, OnDeleteListener listener) {
        BidLog bidLog = new BidLog();
        bidLog.setActivityId(activityId);
        new DeleteSycTask(this.bidLogDao, listener).execute(bidLog);
    }

    public  static class  DeleteSycTask extends AsyncTask<BidLog, Void, Void> {
        private BidLog bidLog = null;
        private BidLogDao dao = null;
        private OnDeleteListener listener = null;

        public DeleteSycTask(BidLogDao dao, OnDeleteListener listener) {
            this.dao = dao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(BidLog...bidLogs) {
            bidLog = bidLogs[0];
            if (bidLog.getId() > 0) {
                dao.delBidLog(bidLog);
            } else if (bidLog.getActivityId() > 0 && bidLog.getBidderId() > 0){
                dao.delBidLog(bidLog.getActivityId(), bidLog.getBidderId());
            } else if (bidLog.getActivityId() > 0){
                dao.delBidLog(bidLog.getActivityId());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (this.listener == null) return;
            this.listener.onDeleted(bidLog);
        }

    }

}
