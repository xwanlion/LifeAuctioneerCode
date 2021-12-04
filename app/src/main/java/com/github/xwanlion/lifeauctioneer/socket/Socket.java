package com.github.xwanlion.lifeauctioneer.socket;

import com.github.xwanlion.lifeauctioneer.util.JsonUtils;
import com.github.xwanlion.lifeauctioneer.util.Logger;
import com.github.xwanlion.lifeauctioneer.view.MainActivity;

import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.WebsocketNotConnectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import cn.hutool.json.JSONUtil;

public class Socket {
    private boolean inSending = false;

    private String sessionId;

    private List<String> socketTagList;

    private WebSocket socket;

    private ConcurrentLinkedQueue<String> forSendingMessageList;

    public Socket() {
        // MainActivity.getRefWatcher().watch(this);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void addSocketTag(String tag) {
        if (this.socketTagList == null) this.socketTagList = new ArrayList<>();
        this.socketTagList.add(tag);
    }

    public void removeSocketTag(String tag) {
        this.socketTagList.remove(tag);
    }

    public boolean containSocketTag(String tag) {
        return this.socketTagList.contains(tag);
    }

    public WebSocket getSocket() {
        return socket;
    }

    public void setSocket(WebSocket socket) {
        this.socket = socket;
    }

    public synchronized void putSendingMessage(String message) {
        if (this.forSendingMessageList == null) this.forSendingMessageList = new ConcurrentLinkedQueue<>();
        this.forSendingMessageList.offer(message);

//        long size = getMessageSize();
//        Logger.d("getMessageSize:" + size);
//        if (size < 100) return;
//         this.removeDeprecateMessage((size - 100));
    }

    public void putSendingMessage(SocketMessage message) {
        // FIXME: if can return false
        this.putSendingMessage(message.toJson());
    }

    public int getMessageSize() {
        if (this.forSendingMessageList == null) return 0;
        // Logger.d(JsonUtils.toJsonString(this.forSendingMessageList));
        return this.forSendingMessageList.size();
    }

    /**
     * sending message to client-end<br/>
     * must be synchronized, because need to clear message.
     */
    public synchronized void sendMessage() {
        if (this.socket == null) return;
        if (!this.socket.isOpen()) return;
        if (this.forSendingMessageList == null || this.forSendingMessageList.size() == 0) return;

        if (this.inSending == true) return;
        this.inSending = true;

        try {
            String message = JSONUtil.toJsonStr(this.forSendingMessageList);
            if (message == null || message.length() == 0) return;

            this.socket.send(message);
//            this.forSendingMessageList.clear();
             this.forSendingMessageList = null;

        } catch (WebsocketNotConnectedException e) {
            this.socket = null;
            e.printStackTrace();
            Logger.e("sendMessage:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("sendMessage2:" + e.getMessage());
        } finally {
            this.inSending = false;
        }
    }

    public synchronized void clearMessage() {
        if (this.socket == null) return;
        if (this.forSendingMessageList == null || this.forSendingMessageList.size() == 0) return;
        this.forSendingMessageList.clear();
    }

//    public synchronized void removeWebSocket() {
//        if (this.socket == null) return;
//        if (this.forSendingMessageList == null || this.forSendingMessageList.size() == 0) return;
//        this.forSendingMessageList.clear();
//    }

    public synchronized void removeDeprecateMessage(long count) {
        //Logger.d("removeDeprecateMessage --> count:" + count + ",sessionId:" + this.sessionId);
        for (long i=0;i<count;i++) {
            this.forSendingMessageList.poll();
        }
    }

    public boolean isInSending() {
        return this.inSending;
    }

    public static Socket valueOf(String sessionId, String socketTag, WebSocket webSocket) {
        Socket socket = new Socket();
        socket.setSessionId(sessionId);
        socket.addSocketTag(socketTag);
        socket.setSocket(webSocket);
        return socket;
    }
}
