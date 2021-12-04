package com.github.xwanlion.lifeauctioneer.controller;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.conf.Msg;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.yanzhenjie.andserver.annotation.Addition;
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

import static com.github.xwanlion.lifeauctioneer.BeanManager.auctionsService;


@Controller
@RequestMapping(path = "/api/bidder")
public class BidderController {

    @Addition
    @ResponseBody
    @PostMapping(path = "/new")
    public Map<String, Object> newBidder (
            @RequestParam(name = "username", required = true) String username,
            @RequestParam(name = "password", required = false) String password
    ) {
        Auctions auctions = BeanManager.auctionsService.getCurrentActivity();
        if (auctions == null) throw new RuntimeException("ERR_AUCTIONS_HAS_STOPPED");

        long bidderId = BeanManager.bidderService.saveBidder(auctions.getId(), username, password);
        Map<String, Object> map = new HashMap<>();
        map.put("bidderId", bidderId);
        return Msg.success("data", map);
    }

    @ResponseBody
    @GetMapping(path = "/list")
    public Map<String, Object> listBidder () {
        List<Bidder> list = BeanManager.bidderService.listBidder(0, 0);
        if (list == null || list.size() == 0) return Msg.success("data", "[]");

        //String json = Bidder.toJson(list);
        List<Map<String, Object>> xList = Bidder.toMap(list);
        return Msg.success("data", xList);

    }

    @ResponseBody
    @GetMapping(path = "/del/{id}")
    public Map<String, Object> delBidder (@PathVariable("id") int id) {
        boolean success = BeanManager.bidderService.delBidder(id);
        if (!success) return Msg.error("ERR_UNKNOWN_ERROR");
        return Msg.success();

    }

}
