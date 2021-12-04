package com.github.xwanlion.lifeauctioneer.controller;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.conf.Msg;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.BidderMoney;
import com.yanzhenjie.andserver.annotation.Controller;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PathVariable;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/api/bidderMoney")
public class BidderMoneyController {

    @ResponseBody
    @PostMapping(path = "/create")
    public Map<String, Object> createBidderMoney (@RequestParam(name = "bidderId", required = true) int bidderId) {
        Auctions activity = BeanManager.auctionsService.getCurrentActivity();
        if (activity == null) return Msg.error("ERR_CURRENT_ACTIVITY_NOT_STARTUP");

        boolean success = BeanManager.bidderMoneyService.createBidderMoney(activity.getId(), bidderId);
        if (!success) return Msg.error("ERR_SAVE_FAIL");
        return Msg.success();
    }

    @ResponseBody
    @PostMapping(path = "/create2")
    public Map<String, Object> createBidderMoney (
            @RequestParam(name = "bidderId", required = true) int bidderId,
            @RequestParam(name = "age", required = true) int age
    ) {
        Auctions activity = BeanManager.auctionsService.getCurrentActivity();
        if (activity == null) return Msg.error("ERR_CURRENT_ACTIVITY_NOT_STARTUP");

        try {
            long moneyAmount = BeanManager.bidderMoneyService.createBidderMoney(activity.getId(), bidderId, age);
            return Msg.success(moneyAmount);
        } catch (Exception e) {
            return Msg.error("ERR_SAVE_FAIL");
        }
    }

    @ResponseBody
    @GetMapping(path = "/get/{id}")
    public Map<String, Object> getBidderMoney (@PathVariable("id") int id) {
        BidderMoney bidLog = BeanManager.bidderMoneyService.getBidderMoney(id);
        if (bidLog == null) return Msg.success("data", "{}");
        return Msg.success("data", BidderMoney.toMap(bidLog));

    }

    @ResponseBody
    @GetMapping(path = "/get/{activityId}/{bidderId}")
    public Map<String, Object> getBidderMoney (
            @PathVariable("activityId") int activityId,
            @PathVariable("activityId") int bidderId
    ) {
        BidderMoney bidLog = BeanManager.bidderMoneyService.getBidderMoney(activityId, bidderId);
        if (bidLog == null) return Msg.success("data", "{}");
        return Msg.success("data", BidderMoney.toMap(bidLog));

    }

    @ResponseBody
    @GetMapping(path = "/list/{activityId}")
    public Map<String, Object> listBidderMoney (
            @PathVariable("activityId") int activityId
    ) {
        List<BidderMoney> list = BeanManager.bidderMoneyService.listBidderMoney(activityId);
        if (list == null || list.size() == 0) return Msg.success("data", "[]");

        List<Map<String, Object>> xList = BidderMoney.toMap(list);
        return Msg.success("data", xList);

    }

    @ResponseBody
    @GetMapping(path = "/list/{pageIndex}/{recordsPerPage}")
    public Map<String, Object> listBidderMoney (
            @PathVariable("activityId") int activityId,
            @PathVariable("pageIndex") int pageIndex,
            @PathVariable("recordsPerPage") int recordsPerPage
    ) {
        List<BidderMoney> list = BeanManager.bidderMoneyService.listBidderMoney(activityId, pageIndex, recordsPerPage);
        if (list == null || list.size() == 0) return Msg.success("data", "[]");

        List<Map<String, Object>> xList = BidderMoney.toMap(list);
        return Msg.success("data", xList);

    }

    @ResponseBody
    @GetMapping(path = "/del/{id}")
    public Map<String, Object> delBidderMoney (@PathVariable("id") int id) {
        boolean success = BeanManager.bidderMoneyService.delBidderMoney(id);
        if (!success) return Msg.error("ERR_UNKNOWN_ERROR");
        return Msg.success();

    }

}
