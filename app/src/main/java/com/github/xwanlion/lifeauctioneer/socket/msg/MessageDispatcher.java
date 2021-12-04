package com.github.xwanlion.lifeauctioneer.socket.msg;

import com.github.xwanlion.lifeauctioneer.socket.Socket;
import com.github.xwanlion.lifeauctioneer.socket.user.WebSocketHolder;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageDispatcher {

    public void dispatchEveryoneMessage(WebSocketHolder socketHolder) {
        if (socketHolder == null) return;

        String message = WebSocketMessageHolder.getEveryoneMessage();
        if (message == null) return;

        ConcurrentLinkedQueue<Socket> socketList = socketHolder.listSocket();
        for (Socket socket: socketList) {
            socket.putSendingMessage(message);
        }

        socketList = null;
    }

//    public void dispatchEveryoneMessage(ConcurrentLinkedQueue<Socket> socketList) {
//        if (socketList == null) return;
//
//        String message = WebSocketMessageHolder.getEveryoneMessage();
//        if (message == null) return;
//
////        ConcurrentLinkedQueue<Socket> socketList = socketHolder.listSocket();
//        for (Socket socket: socketList) {
//            socket.putSendingMessage(message);
//        }
//
//        socketList = null;
//    }

    public void dispatchGroupMessage(String groupName, WebSocketHolder socketHolder) {
        if (socketHolder == null) return;

        String message = WebSocketMessageHolder.getGroupMessage(groupName);
        Logger.d("dispatchGroupMessage: msg:" + message);
        if (message == null) return;

        ConcurrentLinkedQueue<Socket> socketList = socketHolder.listSocket(new StringBuffer(groupName));
        Logger.d("dispatchGroupMessage: socketList:" + socketList.size());
        for (Socket socket: socketList) {
            socket.putSendingMessage(message);
        }

        socketList = null;
    }

    public void dispatchUserMessage(String sessionId, WebSocketHolder socketHolder) {
        if (socketHolder == null) return;

        String message = WebSocketMessageHolder.getUserMessage(sessionId);
        if (message == null) return;

        Socket socket =  socketHolder.getSocket(sessionId);
        socket.putSendingMessage(message);

        socket = null;
    }

}
