package com.github.xwanlion.lifeauctioneer.controller;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.conf.ActivitySate;
import com.github.xwanlion.lifeauctioneer.conf.Msg;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.github.xwanlion.lifeauctioneer.util.Logger;
import com.yanzhenjie.andserver.annotation.Controller;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PathVariable;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(path = "/api/activity")
public class AuctionsController {

    @ResponseBody
    @PostMapping(path = "/new")
    public Map<String, Object> newActivity (
        @RequestParam(name="auctioneerId", required = true) int auctioneerId,
        @RequestParam(name = "amountPerAge", required = true) int amountPerAge
    ) {
        long activityId = BeanManager.auctionsService.saveActivity(auctioneerId, amountPerAge);
        Map<String, Object> map = new HashMap<>();
        map.put("activityId", activityId);
        return Msg.success("data", map);
    }

    @ResponseBody
    @PostMapping(path = "/new1")
    public Map<String, Object> newActivity (
        @RequestParam(name="auctioneerId", required = true) int auctioneerId,
        @RequestParam(name = "money", required = true) long money
    ) {
        long activityId = BeanManager.auctionsService.saveActivity(auctioneerId, money);
        Map<String, Object> map = new HashMap<>();
        map.put("activityId", activityId);
        return Msg.success("data", map);
    }

    @ResponseBody
    @PostMapping(path = "/update")
    public Map<String, Object> updateActivity (
        @RequestParam(name="id", required = true) int id,
        @RequestParam(name="auctioneerId", required = true) int auctioneerId,
        @RequestParam(name = "moneyCreationWay", required = true) int moneyCreationWay
    ) {
        boolean success = BeanManager.auctionsService.updateActivity(id, auctioneerId, moneyCreationWay);
        if (!success) return Msg.error("ERR_SAVE_FAIL");
        return Msg.success();
    }

    @ResponseBody
    @PostMapping(path = "/state")
    public Map<String, Object> changeState (
        @RequestParam(name="id", required = true) int id,
        @RequestParam(name="auctioneerId", required = true) int state
    ) {
        if (state < ActivitySate.STOPPING && state > ActivitySate.FINISH) throw new RuntimeException("ERR_INVALID_STATE_VALUE");

        Auctions auctions = BeanManager.auctionsService.getActivity(id);
        auctions.setState(state);
        boolean success = BeanManager.auctionsService.updateActivity(auctions);
        if (!success) return Msg.error("ERR_SAVE_FAIL");
        return Msg.success();

    }

    @ResponseBody
    @PostMapping(path = "/currency")
    public Map<String, Object> setCurrency (
        @RequestParam(name="id", required = true) int id,
        @RequestParam(name = "amountPerAge", required = true) int amountPerAge
    ) {
        boolean success = BeanManager.auctionsService.setCurrency(id, amountPerAge);
        if (!success) return Msg.error("ERR_SAVE_FAIL");
        return Msg.success();

    }

    @ResponseBody
    @PostMapping(path = "/currency2")
    public Map<String, Object> setCurrency (
        @RequestParam(name="id", required = true) int id,
        @RequestParam(name = "amountPerAge", required = true) long amount
    ) {
        boolean success = BeanManager.auctionsService.setCurrency(id, amount);
        if (!success) return Msg.error("ERR_SAVE_FAIL");
        return Msg.success();
    }

    @ResponseBody
    @PostMapping(path = "/get/{activityId}")
    public Map<String, Object> getActivity (@PathVariable(name = "activityId") int activityId) {
        Auctions auctions = BeanManager.auctionsService.getActivity(activityId);
        if (auctions == null) return Msg.success("data", "{}");

        Map<String, Object> map = Auctions.toMap(auctions);
        return Msg.success("data", map);

    }

    @ResponseBody
    @PostMapping(path = "/get")
    public Map<String, Object> getCurrentActivity () {
        Auctions auctions = BeanManager.auctionsService.getCurrentActivity();
        if (auctions == null) return Msg.success("data", "{}");
        Map<String, Object> map = Auctions.toMap(auctions);
        return Msg.success("data", map);
    }

    @ResponseBody
    @PostMapping(path = "/list")
    public Map<String, Object> listActivity () {
        int pageIndex = 0;
        int recordsPerPage = 20;
        List<Auctions> list = BeanManager.auctionsService.listActivity(pageIndex, recordsPerPage);
        if (list == null || list.size() == 0) return Msg.success("data", "[]");

        //String json = Activity.toJson(list);
        List<Map<String, Object>> xList = Auctions.toMap(list);
        return Msg.success("data", xList);
    }

    @ResponseBody
    @GetMapping(path = "/del/{id}")
    public Map<String, Object> delActivity (@PathVariable("id") int id) {
        boolean success = BeanManager.auctionsService.delActivity(id);
        if (!success) return Msg.error("ERR_UNKNOWN_ERROR");
        return Msg.success();
    }

    @ResponseBody
    @GetMapping(path = "/copy/{id}")
    public Map<String, Object> copyActivity (@PathVariable("id") int id) {
        // FIXME: need to copy bidder and lots
        boolean success = BeanManager.auctionsService.copyActivity(id);
        if (!success) return Msg.error("ERR_UNKNOWN_ERROR");
        return Msg.success();
    }

    @ResponseBody
    @GetMapping(path = "/startup/{id}")
    public Map<String, Object> startActivity (@PathVariable("id") int id) {
        Auctions auctions = BeanManager.auctionsService.getActivity(id);
        if (auctions == null) return Msg.error("ERR_INVALID_AUCTIONS");

        // why clear bid log？
        // if dons't finished， auctions will continue from first again， if so, bid's log of last time deprecated
        if (auctions.getState() < ActivitySate.FILL_TESTIMONIALS) BeanManager.bidLogService.clearLog(id);

        boolean success = BeanManager.auctionsService.startActivity(id);
        if (!success) return Msg.error("ERR_UNKNOWN_ERROR");
        return Msg.success();
    }

    @ResponseBody
    @GetMapping(path = "/stop/{id}")
    public Map<String, Object> stopActivity (@PathVariable("id") int id) {
        boolean success = BeanManager.auctionsService.stopActivity(id);
        if (!success) return Msg.error("ERR_UNKNOWN_ERROR");
        return Msg.success();
    }

    @ResponseBody
    @GetMapping(path = "/needLogin")
    public Map<String, Object> needLogin () {
        Auctions activity = BeanManager.auctionsService.getCurrentActivity();
        if (activity == null) return Msg.error("ERR_CURRENT_ACTIVITY_IS_NOT_STARTED");

        Auctioneer auctioneer = BeanManager.auctioneerService.getAuctioneer(activity.getAuctioneerId());
        if (auctioneer == null) return Msg.error("ERR_AUCTIONEER_IS_NOT_SETTING");

        Map<String, Object> map = new HashMap<>();
        map.put("needLogin", activity.isNeedLogin());
        map.put("auctioneer", auctioneer.getUsername());
        return Msg.success(map);
    }

}
