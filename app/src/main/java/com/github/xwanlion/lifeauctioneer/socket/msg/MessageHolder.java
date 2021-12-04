package com.github.xwanlion.lifeauctioneer.socket.msg;

import com.github.xwanlion.lifeauctioneer.util.JsonUtils;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageHolder implements MessageHolderInterface {
    private ConcurrentHashMap<String, ConcurrentLinkedQueue> messageMap = null;

    public synchronized String getMessage(String group) {
        if (messageMap == null || messageMap.size() == 0) return null;

        ConcurrentLinkedQueue<String> queue = messageMap.get(group);
        messageMap.clear();
        // messageMap.put(group, null); // clear message to mark that has got it

        return JsonUtils.toJsonString(queue);
    }

    public synchronized void putMessage(String group, String message) {
        if (group == null) throw new RuntimeException("ERR_GROUP_NAME_CAN_NOT_BE_NULL");
        if (message == null) throw new RuntimeException("ERR_MESSAGE_NAME_CAN_NOT_BE_NULL");

        if (messageMap == null) messageMap = new ConcurrentHashMap<>();

        ConcurrentLinkedQueue<String> queue = messageMap.get(group);
        if (queue == null) queue = new ConcurrentLinkedQueue<>();

        queue.offer(message);
        messageMap.put(group, queue);
    }
}
