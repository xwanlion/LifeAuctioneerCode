package com.github.xwanlion.lifeauctioneer.socket;

import com.alibaba.fastjson.JSON;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;

import java.util.Map;

public class SocketMessage {

    public SocketMessage () {
        createId();
    }

    private Long id;

    /**
     * see: MessageType.java
     */
    private Integer msgType;

    /**
     * equals to http request sessionId
     */
    private String sessionId;

    /**
     * user type
     * <br/>
     * see: UserType.java
     */
    private Integer userType;

    /**
     * message content
     */
    private String text;

    public Long getId() {
        return id;
    }

    public void createId() {
        this.id = System.currentTimeMillis();
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toJson() {
        return JsonUtils.toJsonString(this);
    }

    public static SocketMessage fromJson(String json) {
        return JSON.parseObject(json, SocketMessage.class);
    }

    public static Map<String, Object> toMap(String json) {
        if (json == null || json.length() ==0) return null;
        return JsonUtils.parseJson(json, Map.class);
    }

    public static SocketMessage valueOf(Integer msgType, String sessionId, Integer userType, String text ) {
        SocketMessage message = new SocketMessage();
        message.createId();
        message.setMsgType(msgType);
        message.setSessionId(sessionId);
        message.setUserType(userType);
        message.setText(text);
        return message;
    }

}
