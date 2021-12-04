package com.github.xwanlion.lifeauctioneer.controller;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.conf.Msg;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Bidder;
import com.github.xwanlion.lifeauctioneer.model.app.UserType;
import com.github.xwanlion.lifeauctioneer.util.CookieUtils;
import com.yanzhenjie.andserver.annotation.Controller;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.ResponseBody;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;

import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping(path = "/api/user")
public class UserController {

    /**
     * if need login, user must provied a valid username and password, otherwise，anyone can use any name to login except auctioneer.
     * and anyone can use any name to login many times when not require login.
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @PostMapping(path = "/login")
    public Map<String, Object> userLogin (
        @RequestParam(name = "username", required = true) String username,
        @RequestParam(name = "password", required = false) String password,
        HttpRequest request, HttpResponse response
    ) {
        Auctions auctions = BeanManager.auctionsService.getCurrentActivity();
        if (auctions == null) throw new RuntimeException("ERR_AUCTIONS_HAS_STOPPED");

        // auctioneer login
        if (BeanManager.auctioneerService.checkAuctioneer(username, password)) {
            this.addSessionToResponseHeader(response);
            return Msg.success(this.getAuctioneer(username));
        }

        // bidder login
        if (auctions.isNeedLogin()) {
            if (BeanManager.bidderService.checkBidder(auctions.getId(), username, password)) {
                this.addSessionToResponseHeader(response);
                return Msg.success(this.getBidder(auctions.getId(), username));
            }
        } else {
            // not require login, save bidder first.
            password = ""; // front-end can not input password.
            Bidder bidder = this.saveBidder(auctions.getId(), username, password);
            this.addSessionToResponseHeader(response);
            return Msg.success(this.bidderToMap(bidder));
        }

        return Msg.error("ERR_INVALID_USERNAME_OR_PASSWORD");
    }

    private void addSessionToResponseHeader(HttpResponse response) {
        String uuid = UUID.randomUUID().toString();
        response.addHeader(CookieUtils.SESSION_ID_KEY, uuid);
    }

    private Map<String, Object> getAuctioneer(String username) {
        Auctioneer auctioneer = BeanManager.auctioneerService.getAuctioneer(username);
        auctioneer.setPassword("");
        return auctioneerToMap(auctioneer);
    }

    private Map<String, Object> getBidder(int activityId, String username) {
        Bidder bidder = BeanManager.bidderService.getBidder(activityId, username);
        bidder.setPassword("");
        return bidderToMap(bidder);
    }

    private Map<String, Object> auctioneerToMap(Auctioneer auctioneer) {
        Map<String, Object> map = Auctioneer.toMap(auctioneer);
        map.put("userType", UserType.AUCTIONEER);
        return map;
    }

    private Bidder saveBidder(int activityId, String username, String password) {
        Bidder bidder = BeanManager.bidderService.getBidder(activityId, username);

        if (bidder == null) {
            long bidderId = BeanManager.bidderService.saveBidder(activityId, username, password);
            bidder = Bidder.valueOf((int) bidderId, activityId, username, password);
        } else {
            long bidderId = BeanManager.bidderService.saveBidder(bidder);
        }
        return bidder;
    }

    private Map<String, Object> bidderToMap(Bidder bidder) {
        Map<String, Object> map = Bidder.toMap(bidder);
        map.put("userType", UserType.BIDDER);
        return map;
    }

}
