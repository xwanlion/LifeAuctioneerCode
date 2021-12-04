package com.github.xwanlion.lifeauctioneer.controller;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.conf.ActivitySate;
import com.github.xwanlion.lifeauctioneer.conf.Msg;
import com.github.xwanlion.lifeauctioneer.dao.IDatabase;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.BidLog;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.github.xwanlion.lifeauctioneer.model.app.BidderExpectingLot;
import com.github.xwanlion.lifeauctioneer.model.app.BidderMoney;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;
import com.github.xwanlion.lifeauctioneer.model.app.UserType;
import com.github.xwanlion.lifeauctioneer.socket.MessageType;
import com.github.xwanlion.lifeauctioneer.socket.SocketMessage;
import com.github.xwanlion.lifeauctioneer.socket.msg.WebSocketMessageHolder;
import com.github.xwanlion.lifeauctioneer.util.CookieUtils;
import com.github.xwanlion.lifeauctioneer.util.FileUtils;
import com.github.xwanlion.lifeauctioneer.util.Logger;
import com.github.xwanlion.lifeauctioneer.util.StringUtils;
import com.yanzhenjie.andserver.annotation.Controller;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PathVariable;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.QueryParam;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.ResponseBody;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


@Controller
@RequestMapping(path = "/api/lot")
public class LotController {
//    private Lot currentAuctionLot = null;

    @ResponseBody
    @PostMapping(path = "/new")
    public Map<String, Object> newLot(
            @RequestParam(name = "activityId", required = true) int activityId,
            @RequestParam(name = "name", required = true) String name,
            @RequestParam(name = "startingPrice", required = false) long startingPrice,
            @RequestParam(name = "increment", required = false) long increment,
            @RequestParam(name = "desc", required = false) String desc,
            @RequestParam(name = "pic", required = true) MultipartFile picture
    ) {
        String saveTo = FileUtils.generateFileName();
        File file = new File(saveTo);
        try {
            picture.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.error("ERR_SAVE_FILE_IO_EXCEPTION");
        }

        long lotId = BeanManager.lotService.saveLot(activityId, name, startingPrice, increment, 0, desc, saveTo);
        Map<String, Object> map = new HashMap<>();
        map.put("lotId", lotId);
        return Msg.success("data", map);

    }

    @ResponseBody
    @PostMapping(path = "/get/{id}")
    public Map<String, Object> getLot(@PathVariable(name = "id") int id) {
        Lot lot = BeanManager.lotService.getLot(id);
        if (lot == null) return Msg.success("data", "{}");

        //String json = Lot.toJson(list);
        Map<String, Object> map = Lot.toMap(lot);
        return Msg.success("data", map);
    }

    @ResponseBody
    @PostMapping(path = "/get/cur")
    public Map<String, Object> getCurrentLot() {
        if (Lot.activeLot == null) return Msg.success("data", "{}");
        return Msg.success(Lot.toMap(Lot.activeLot));
    }

    @ResponseBody
    @PostMapping(path = "/list/{activityId}")
    public Map<String, Object> listLot(@PathVariable(name = "activityId") int activityId) {
        int pageIndex = 0;
        int recordsPerPage = 20;
        List<Lot> list = BeanManager.lotService.listLot(activityId, pageIndex, recordsPerPage);
        if (list == null || list.size() == 0) return Msg.success("data", "[]");

        //String json = Lot.toJson(list);
        List<Map<String, Object>> xList = Lot.toMap(list);
        return Msg.success("data", xList);
    }

    @ResponseBody
    @GetMapping(path = "/del/{id}")
    public Map<String, Object> delLot(@PathVariable("id") int id) {
        boolean success = BeanManager.lotService.delLot(id);
        if (!success) return Msg.error("ERR_UNKNOWN_ERROR");
        return Msg.success();
    }

    @ResponseBody
    @GetMapping(path = "/point/{lotId}")
    public Map<String, Object> pointAuctionLot(
            @PathVariable("lotId") int lotId,
            HttpRequest request
    ) {
        String sessionId = request.getHeader(CookieUtils.SESSION_ID_KEY);
        Lot lot = BeanManager.lotService.pointAuctionLot(lotId);
        if (lot == null) return Msg.error("ERR_UNKNOWN_ERROR");

        // save buyer's info of Lot object
        if (Lot.activeLot != null && Lot.activeLot != Lot.getRestLot()) {
            BidLog bidLog = BeanManager.bidLogService.getHighestOffer(Lot.activeLot.getActivityId(), Lot.activeLot.getId());
            if (bidLog != null) {
                // Logger.i("currentAuctionLot id 1:" + Lot.activeLot.getId() + ", bidderId:" + bidLog.getBidderId() + ", lot name:" + Lot.activeLot.getName());
                Map<String, Object> rMap = this.sold(bidLog.getBidderId(), bidLog.getPrice());
                // Logger.i("currentAuctionLot id 2:" + Lot.activeLot.getId() + ", lot name:" + Lot.activeLot.getName());
                if (Msg.isSuccess(rMap) == false) return Msg.error("ERR_UPDATE_LOTS_INFO_ERROR");
            }
        } else {
            Auctions auctions = BeanManager.auctionsService.getCurrentActivity();
            if (auctions.getState() >= ActivitySate.FILL_TESTIMONIALS) return Msg.error("ERR_INVALID_ACTIVITY_STATE");

            auctions.setState(ActivitySate.AUCTION);
            long activityId = BeanManager.auctionsService.saveActivity(auctions);
            if (activityId <= 0) return Msg.error("ERR_UPDATE_ACTIVITY_STATE_FAIL");
        }

        Lot.activeLot = lot;
//        Logger.i("currentAuctionLot id 3:" + currentAuctionLot.getId());
        SocketMessage message = SocketMessage.valueOf(MessageType.POINT_AUCTION_LOT, sessionId, UserType.AUCTIONEER, lot.toJson());
        //SocketContainer.getInstance().clearMessage();
        //SocketMessageSender.getInstance().sendMessage(message);
        WebSocketMessageHolder.putToEveryone(message.toJson());

        return Msg.success();
    }

    @ResponseBody
    @PostMapping(path = "/new/expectLot")
    public Map<String, Object> saveExpectingLot(
            @RequestParam(name = "activityId", required = true) int activityId,
            @RequestParam(name = "bidderId", required = true) int bidderId,
            @RequestParam(name = "lotId", required = false) int lotId,
            @RequestParam(name = "expectingPrice", required = false) long expectingPrice
    ) {
        boolean success = BeanManager.lotService.saveExpectingLot(activityId, bidderId, lotId, expectingPrice);
        if (!success) return Msg.error("ERR_SAVE_FAIL");
        return Msg.success();
    }

    @ResponseBody
    @PostMapping(path = "/new/expectLots")
    public Map<String, Object> saveExpectingLots(
            @RequestParam(name = "bidderId", required = true) int bidderId,
            @RequestParam(name = "lots", required = true) String lots
    ) {
        // Logger.i("lots:" + lots);
        Auctions activity = BeanManager.auctionsService.getCurrentActivity();
        if (activity == null) return Msg.error("ERR_CURRENT_ACTIVITY_NOT_STARTED_UP");

        List<BidderExpectingLot> lotList = StringUtils.toBidderExpectingLots(lots);
        // Logger.i("lotList.size():" + lotList.size());

        int activityId = BeanManager.auctionsService.getCurrentActivity().getId();
        ExpectingLotPersisCallable callable = new ExpectingLotPersisCallable(activityId, bidderId, lotList);
        return IDatabase.getDatabase().runInTransaction(callable);
    }

    @ResponseBody
    @PostMapping(path = "/list/expectLot")
    public Map<String, Object> listExpectingLot(@QueryParam(name = "activityId") int activityId) {
        List<Map<String, Object>> list = BeanManager.lotService.listExpectingLot(activityId);
        if (list == null || list.size() == 0) return Msg.success("data", "[]");
        return Msg.success("data", list);
    }

    @ResponseBody
    @PostMapping(path = "/list/expectLot2")
    public Map<String, Object> listExpectingLot(
            @RequestParam(name = "activityId") int activityId,
            @RequestParam(name = "bidderId") int bidderId
    ) {
        List<Map<String, Object>> list = BeanManager.lotService.listExpectingLot(activityId, bidderId);
        if (list == null || list.size() == 0) return Msg.success("data", "[]");
        return Msg.success("data", list);
    }

    @ResponseBody
    @PostMapping(path = "/bid")
    public Map<String, Object> bid(
            @RequestParam(name = "bidderId") int bidderId,
            @RequestParam(name = "price") long price,
            HttpRequest request
    ) {
        if (Lot.activeLot == null) return Msg.error("ERR_CAN_NOT_BID_CURRENTLY");
        if (Lot.activeLot == Lot.getRestLot()) return Msg.error("ERR_CURRENT_LOT_HAS_SOLD");

        String sessionId = request.getHeader(CookieUtils.SESSION_ID_KEY);
        if (sessionId == null || sessionId.length() == 0) return Msg.error("ERR_INVALID_USER_SESSION");

        int activityId = BeanManager.auctionsService.getCurrentActivity().getId();
        Bidder bidder = BeanManager.bidderService.getBidder(bidderId);

        BidderMoney money = BeanManager.bidderMoneyService.getBidderMoney(activityId, bidderId);
        if (money == null) return Msg.error("ERR_INVALID_BIDDER_MONEY");
        if (money.getAmount() < price) return Msg.error("ERR_REMAIN_MONEY_MUST_GREATER_THAN_PRICE");

        // 保存竞价记录
        BidLog log = new BidLog();
        log.setActivityId(activityId);
        log.setBidderId(bidderId);
        log.setBidder(bidder.getUsername());
        log.setLotId(Lot.activeLot.getId());
        log.setPrice(price);
        log.setTime(System.currentTimeMillis());
        BeanManager.bidLogService.saveLog(log);

        // 通知所有用户
        SocketMessage message = SocketMessage.valueOf(MessageType.OFFER_PRICE, sessionId, UserType.BIDDER, log.toJson());
        WebSocketMessageHolder.putToEveryone(message.toJson());
        message = null;

        return Msg.success();
    }

    @ResponseBody
    @GetMapping(path = "/auction/over/{lotId}")
    public Map<String, Object> auctionOver(
            @PathVariable("lotId") int lotId,
            HttpRequest request
    ) {
        String sessionId = request.getHeader(CookieUtils.SESSION_ID_KEY);
        Lot lot = BeanManager.lotService.getLot(lotId);
        if (lot == null) return Msg.error("ERR_UNKNOWN_ERROR");

        // save buyer's info of Lot object
        BidLog bidLog = BeanManager.bidLogService.getHighestOffer(lot.getActivityId(), lot.getId());
        if (bidLog != null) {
            Map<String, Object> rMap = this.sold(bidLog.getBidderId(), bidLog.getPrice());
            if (Msg.isSuccess(rMap) == false) return Msg.error("ERR_UPDATE_LOTS_INFO_ERROR");
        }

        Auctions auctions = BeanManager.auctionsService.getCurrentActivity();
        auctions.setState(ActivitySate.FILL_TESTIMONIALS);
        long activityId = BeanManager.auctionsService.saveActivity(auctions);
        if (activityId <= 0) return Msg.error("ERR_UPDATE_ACTIVITY_STATE_FAIL");

        Lot.activeLot = Lot.getRestLot();
        // Logger.i("currentAuctionLot id 3:" + currentAuctionLot.getId());
        SocketMessage message = SocketMessage.valueOf(MessageType.AUCTION_OVER, sessionId, UserType.AUCTIONEER, null);
        // SocketMessageSender.getInstance().sendMessage(message);
        WebSocketMessageHolder.putToEveryone(message.toJson());
        message = null;

        return Msg.success();
    }

    @ResponseBody
    @PostMapping(path = "/sold")
    public Map<String, Object> sold(
            @RequestParam(name = "bidderId") int bidderId,
            @RequestParam(name = "activityId") long price
    ) {
//        int activityId = BeanManager.auctionsService.getCurrentActivity().getId();
        // Logger.d("sold --> activityId:" + activityId + " bidderId:" + bidderId);
        SoldCallable soldCallable = new SoldCallable(Lot.activeLot.getActivityId(), bidderId, Lot.activeLot.getId(), price);
        Map<String, Object> rMap = IDatabase.getDatabase().runInTransaction(soldCallable);
        soldCallable = null;

        // mark the auction has stopped, can not be bidding now.
        if (Msg.isSuccess(rMap)) Lot.activeLot = Lot.getRestLot();

        return rMap;
    }

    class SoldCallable implements Callable<Map<String, Object>> {
        private int activityId;
        private int bidderId;
        private int lotId;
        private long price;

        public SoldCallable(int activityId, int bidderId, int lotId, long price) {
            this.activityId = activityId;
            this.bidderId = bidderId;
            this.lotId = lotId;
            this.price = price;
        }

        @Override
        public Map<String, Object> call() throws Exception {
            // 将竞价人的金额扣减掉
//            Logger.d("SoldCallable --> activityId:" + activityId + " bidderId:" + bidderId);
            BeanManager.bidderMoneyService.reduceBidderMoney(activityId, bidderId, price);

            // 保存竞价记录
            boolean success = BeanManager.lotService.achieveExpectingLot(activityId, bidderId, lotId, price);
            if (!success) return Msg.error("ERR_UNKNOWN_ERROR");

            Lot lot = Lot.activeLot.clone();
            Bidder bidder = BeanManager.bidderService.getBidder(bidderId);
            lot.setBuyerId(bidderId);
            lot.setBuyer(bidder.getUsername());
            lot.setPurchasePrice(price);
            BeanManager.lotService.saveLot(lot);

            return Msg.success();
        }
    }

    class ExpectingLotPersisCallable implements Callable<Map<String, Object>> {
        private int activityId;
        private int bidderId;
        private List<BidderExpectingLot> lotList;

        public ExpectingLotPersisCallable(int activityId, int bidderId, List<BidderExpectingLot> lotList) {
            this.activityId = activityId;
            this.bidderId = bidderId;
            this.lotList = lotList;
        }

        @Override
        public Map<String, Object> call() throws Exception {
            boolean success = BeanManager.lotService.saveExpectingLot(activityId, bidderId, lotList);
            this.lotList = null;
            if (!success) return Msg.error("ERR_UNKNOWN_ERROR");
            return Msg.success();
        }
    }
}
