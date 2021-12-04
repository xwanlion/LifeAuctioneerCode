package com.github.xwanlion.lifeauctioneer.socket.msg;

public interface MessageHolderInterface {
    String getMessage(String groupName);
    void putMessage(String groupName, String message);
}
