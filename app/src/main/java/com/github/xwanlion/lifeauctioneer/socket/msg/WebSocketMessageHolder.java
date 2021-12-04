package com.github.xwanlion.lifeauctioneer.socket.msg;

import com.github.xwanlion.lifeauctioneer.util.Logger;

public class WebSocketMessageHolder {
    private static MessageHolder toAllUserMessageHolder = new MessageHolder();

    private static MessageHolder toGroupUserMessageHolder = new MessageHolder();

    private static MessageHolder toSingleUserMessageHolder = new MessageHolder();

    private static final String TO_ALL_USER_GROUP_NAME = "_all$";

    public static void putToUser(String userSessionId, String message) {
        toSingleUserMessageHolder.putMessage(userSessionId, message);
    }

    protected static String getUserMessage(String userSessionId) {
        return toSingleUserMessageHolder.getMessage(userSessionId);
    }

    public static void putToGroupUser(String groupName, String message) {
        toGroupUserMessageHolder.putMessage(groupName, message);
    }

    protected static String getGroupMessage(String groupName) {
        return toGroupUserMessageHolder.getMessage(groupName);
    }

    public static void putToEveryone(String message) {
        toAllUserMessageHolder.putMessage(TO_ALL_USER_GROUP_NAME, message);
    }

    protected static String getEveryoneMessage() {
        return toAllUserMessageHolder.getMessage(TO_ALL_USER_GROUP_NAME);
    }

}
