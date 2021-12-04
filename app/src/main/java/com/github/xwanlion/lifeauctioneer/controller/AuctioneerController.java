package com.github.xwanlion.lifeauctioneer.controller;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.conf.Msg;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
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


@Controller
@RequestMapping(path = "/api/auctioneer")
public class AuctioneerController {

    @Addition
    @ResponseBody
    @PostMapping(path = "/new")
    public Map<String, Object> newAuctioneer (
            @RequestParam(name = "username", required = true) String username,
            @RequestParam(name = "password", required = false) String password
    ) {
        long auctioneerId = BeanManager.auctioneerService.saveAuctioneer(username, password);
        Map<String, Object> map = new HashMap<>();
        map.put("auctioneerId", auctioneerId);
        return Msg.success("data", map);
    }

    @ResponseBody
    @GetMapping(path = "/list")
    public Map<String, Object> listAuctioneer () {
        List<Auctioneer> list = BeanManager.auctioneerService.listAuctioneer(0, 0);
        if (list == null || list.size() == 0) return Msg.success("data", "[]");

        //String json = Auctioneer.toJson(list);
        List<Map<String, Object>> xList = Auctioneer.toMap(list);
        return Msg.success("data", xList);

    }

    @ResponseBody
    @GetMapping(path = "/del/{id}")
    public Map<String, Object> delAuctioneer (@PathVariable("id") int id) {
        boolean success = BeanManager.auctioneerService.delAuctioneer(id);
        if (!success) return Msg.error("ERR_UNKNOWN_ERROR");
        return Msg.success();

    }

}
