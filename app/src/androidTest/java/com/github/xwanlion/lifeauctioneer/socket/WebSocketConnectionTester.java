package com.github.xwanlion.lifeauctioneer.socket;

import com.github.xwanlion.lifeauctioneer.util.CookieUtils;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.http.HttpRequest;

import static org.junit.Assert.assertEquals;

//@RunWith(AndroidJUnit4.class)
public class WebSocketConnectionTester {
    private String bidderName = "bidder";
    private String auctioneerName = "auctioneer";
    private String host = "http://192.168.43.1:9090/";

    private String bidderSessionId = null;
    private String auctioneerSessionId = null;

    WebSocketClient bidderWebSocket = null;
    WebSocketClient auctioneerWebSocket = null;

    private long createActivity (int auctioneerId) {
        Map<String, Object> bidderMap = new HashMap<>();
        bidderMap.put("auctioneerId", auctioneerId);
        bidderMap.put("money", 3500);
        String body = HttpRequest.post(host + "activity/new1")
                .form(bidderMap)//表单内容
                .timeout(20000)//超时:毫秒
                .execute().body();
        System.out.println("dataMap:" + body);
        assertEquals(true, body != null);
        Map<String, Object> map = JsonUtils.parseJson(body, Map.class);

        Map<String, Object> dataMap = (Map<String, Object>) map.get("data");
        System.out.println("dataMap:" + JsonUtils.toJsonString(dataMap));
        assertEquals(true, (dataMap != null));

        int activityId = (int) dataMap.get("activityId");
        assertEquals(true, activityId > 0);

        return activityId;

    }

    private void startUpActivity (long activityId) {
        String body = HttpRequest.get(host + "activity/startup/" + activityId)
                .timeout(20000) //超时:毫秒
                .execute().body();
        assertEquals(true, body != null);
        Map<String, Object> map = JsonUtils.parseJson(body, Map.class);

        Integer code = (Integer) map.get("code");
        assertEquals(true, code == 0);

    }

    private long createBidder() {
        Map<String, Object> bidderMap = new HashMap<>();
        bidderMap.put("username", bidderName);
        bidderMap.put("password", "");
        String body = HttpRequest.post(host + "bidder/new")
                .form(bidderMap)//表单内容
                .timeout(20000)//超时:毫秒
                .execute().body();
        assertEquals(true, body != null);
        Map<String, Object> map = JsonUtils.parseJson(body, Map.class);
        System.out.println("map:" + JsonUtils.toJsonString(map));
        Map<String, Object> dataMap = (Map<String, Object>) map.get("data");
        System.out.println("dataMap:" + JsonUtils.toJsonString(dataMap));
        assertEquals(true, (dataMap != null));

        int bidderId = (int) dataMap.get("bidderId");
        assertEquals(true, bidderId > 0);

        return bidderId;

    }

    private long createAuctioneer() {
        Map<String, Object> bidderMap = new HashMap<>();
        bidderMap.put("username", auctioneerName);
        bidderMap.put("password", "");
        String body = HttpRequest.post(host + "auctioneer/new")
                .form(bidderMap)//表单内容
                .timeout(20000)//超时:毫秒
                .execute().body();
        assertEquals(true, body != null);
        System.out.println(body);
        Map<String, Object> map = JsonUtils.parseJson(body, Map.class);

        Map<String, Object> dataMap = (Map<String, Object>) map.get("data");
        assertEquals(true, (dataMap != null));

        int auctioneerId = (int) dataMap.get("auctioneerId");
        assertEquals(true, auctioneerId > 0);

        return auctioneerId;

    }

    private void userLogin() {
        // bidder login
        Map<String, Object> bidderMap = new HashMap<>();
        bidderMap.put("username", bidderName);
        bidderSessionId = HttpRequest.post(host + "/user/login")
                .form(bidderMap)//表单内容
                .timeout(20000)//超时:毫秒
                .execute().header(CookieUtils.SESSION_ID_KEY);
        assertEquals(true, bidderSessionId != null);
        System.out.println("bidderSessionId:" + bidderSessionId);

        // --+++++++++++++++++++++++++++++++++++++++++
        // auctioneer login
        Map<String, Object> auctioneerMap = new HashMap<>();
        auctioneerMap.put("username", auctioneerName);
        auctioneerSessionId = HttpRequest.post(host + "/user/login")
                .form(auctioneerMap)//表单内容
                .timeout(20000)//超时:毫秒
                .execute().header(CookieUtils.SESSION_ID_KEY);

        assertEquals(true, auctioneerSessionId != null);
        System.out.println("auctioneerSessionId:" + auctioneerSessionId);

    }

    private long createLot(int activityId) {
        Map<String, Object> lotMap = new HashMap<>();
        lotMap.put("activityId", activityId);
        lotMap.put("name", "name");
        lotMap.put("startingPrice", 500);
        lotMap.put("increment", 0);
        lotMap.put("desc", "desc");
        String body = HttpRequest.post(host + "lot/new")
                .form(lotMap)//表单内容
                .timeout(20000)//超时:毫秒
                .execute().body();
        System.out.println("createLot:" + body);
        Map<String, Object> map = JsonUtils.parseJson(body, Map.class);

        Map<String, Object> dataMap = (Map<String, Object>) map.get("data");
        assertEquals(true, (dataMap != null));

        int lotId = (int) dataMap.get("lotId");
        assertEquals(true, lotId > 0);
        return lotId;

    }

    private void pointAuctionLot(long lotId) {
        assertEquals(true, lotId > 0);
        assertEquals(true, auctioneerSessionId != null);

        HttpCookie cookie = new HttpCookie(CookieUtils.SESSION_ID_KEY, auctioneerSessionId);
        String body = HttpRequest.get(host + "lot/point/" + lotId)
                .cookie(cookie)
                .timeout(20000)//超时:毫秒
                .execute().body();
        System.out.println("pointAuctionLot:" + body);
        assertEquals(true, body != null);
        assertEquals(true, body.indexOf("0") > 0);
    }

    private void bidAuctionLot(long bidderId) {
        assertEquals(true, bidderId > 0);
        assertEquals(true, auctioneerSessionId != null);

        Map<String, Object> map = new HashMap<>();
        map.put("bidderId", bidderId);
        map.put("price", 1500L);

        HttpCookie cookie = new HttpCookie(CookieUtils.SESSION_ID_KEY, bidderSessionId);
        String body = HttpRequest.post(host + "lot/bid/" )
                .form(map)
                .cookie(cookie)
                .timeout(20000)//超时:毫秒
                .execute().body();
        System.out.println("bidAuctionLot:" + body);
        assertEquals(true, body != null);
        assertEquals(true, body.indexOf("0") > 0);
    }

    private WebSocketClient getBidderWebSocket(URI uri) {
        WebSocketClient webSocket = new WebSocketClient(uri, new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {}

            @Override
            public void onMessage(String message) {
                System.out.println("bidder message:" + message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {}

            @Override
            public void onError(Exception ex) {}
        };
        webSocket.connect();
        return webSocket;
    }

    private WebSocketClient getAuctioneerWebSocket(URI uri) {
        WebSocketClient webSocket = new WebSocketClient(uri, new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {}

            @Override
            public void onMessage(String message) {
                System.out.println("auctioneer message:" + message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {}

            @Override
            public void onError(Exception ex) {}

        };
        webSocket.connect();
        return webSocket;
    }

    private void webSocketConnection() throws URISyntaxException {
        URI uri = new URI("ws://192.168.43.1:8886/");
        bidderWebSocket = getBidderWebSocket(uri);
        auctioneerWebSocket = getAuctioneerWebSocket(uri);
    }

    public void testAll() throws URISyntaxException {
        long auctioneerId = createAuctioneer();
        long bidderId = createBidder();

        long activityId = createActivity((int) auctioneerId);
        long lotId = createLot((int) activityId);

        startUpActivity(activityId);

        userLogin();
        webSocketConnection();

        pointAuctionLot(lotId);
        bidAuctionLot(bidderId);

    }

    public static void main(String[] args){
        WebSocketConnectionTester tester = new WebSocketConnectionTester();
        try {
            tester.testAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
