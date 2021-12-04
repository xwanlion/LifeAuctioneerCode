package com.github.xwanlion.lifeauctioneer.controller;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.conf.Msg;
import com.github.xwanlion.lifeauctioneer.model.app.BidLog;
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
@RequestMapping(path = "/api/bidLog")
public class BidLogController {

    @ResponseBody
    @PostMapping(path = "/new")
    public Map<String, Object> newBidLog (
            @RequestParam(name = "activityId", required = true) int activityId,
            @RequestParam(name = "bidderId", required = true) int bidderId,
            @RequestParam(name = "lotId", required = true) int lotId,
            @RequestParam(name = "price", required = true) long price
    ) {
        boolean success = BeanManager.bidLogService.saveLog(activityId, bidderId, lotId, price);
        if (!success) return Msg.error("ERR_SAVE_FAIL");
        return Msg.success();
    }

    @ResponseBody
    @GetMapping(path = "/del/{id}")
    public Map<String, Object> delBidLog (@PathVariable("id") int id) {
        boolean success = BeanManager.bidLogService.delLog(id);
        if (!success) return Msg.error("ERR_UNKNOWN_ERROR");
        return Msg.success();

    }

    @ResponseBody
    @GetMapping(path = "/get/{id}")
    public Map<String, Object> getBidLog (@PathVariable("id") int id) {
        BidLog bidLog = BeanManager.bidLogService.getLog(id);
        if (bidLog == null) return Msg.success("data", "{}");
        return Msg.success("data", BidLog.toMap(bidLog));

    }

    @ResponseBody
    @GetMapping(path = "/get/{activityId}/{bidderId}/{lotId}")
    public Map<String, Object> getBidLog (
            @PathVariable("activityId") int activityId,
            @PathVariable("id") int bidderId,
            @PathVariable("lotId") int lotId
    ) {
        BidLog bidLog = BeanManager.bidLogService.getLog(activityId, bidderId, lotId);
        if (bidLog == null) return Msg.success("data", "{}");
        return Msg.success("data", BidLog.toMap(bidLog));

    }

    @ResponseBody
    @GetMapping(path = "/list/{activityId}/{bidderId}")
    public Map<String, Object> listBidLog (
            @PathVariable("activityId") int activityId,
            @PathVariable("id") int bidderId
    ) {
        List<BidLog> list = BeanManager.bidLogService.listLog(activityId, bidderId);
        if (list == null || list.size() == 0) return Msg.success("data", "[]");

        List<Map<String, Object>> xList = BidLog.toMap(list);
        return Msg.success("data", xList);

    }

    @ResponseBody
    @GetMapping(path = "/list2/{pageIndex}/{recordsPerPage}")
    public Map<String, Object> listBidLog (
            @PathVariable("activityId") int activityId,
            @PathVariable("pageIndex") int pageIndex,
            @PathVariable("recordsPerPage") int recordsPerPage
    ) {
        List<BidLog> list = BeanManager.bidLogService.listLog(activityId, pageIndex, recordsPerPage);
        if (list == null || list.size() == 0) return Msg.success("data", "[]");

        List<Map<String, Object>> xList = BidLog.toMap(list);
        return Msg.success("data", xList);

    }

}
