package com.github.xwanlion.lifeauctioneer.socket.user;

import com.github.xwanlion.lifeauctioneer.socket.Socket;
import com.github.xwanlion.lifeauctioneer.view.MainActivity;

import org.java_websocket.WebSocket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WebSocketHolder {

    // private Map<String, Socket> socketMap = new ConcurrentHashMap<>();
    private Map<String, Socket> socketMap = new HashMap<>();

    public void putSocket(Socket socket) {
        socketMap.put(socket.getSessionId(), socket);
    }

    public void putSocket(String sessionId, String groupName, WebSocket webSocket) {
        Socket socket = this.getSocket(sessionId);
        if (socket == null) {
            socket = Socket.valueOf(sessionId, groupName, webSocket);
            this.putSocket(socket);
        } else {
            socket.setSocket(webSocket);
        }
    }

    public Socket getSocket(WebSocket socket) {
        if (socketMap == null || socketMap.size() == 0) return null;

        Iterator<Map.Entry<String, Socket>> itr = socketMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, Socket> entry = itr.next();
            Socket socket1 = entry.getValue();
            if (socket1.getSocket() == socket) return socket1;
        }
        return null;
    }

    public Socket getSocket(String sessionId) {
        return socketMap.get(sessionId);
    }

    public void resetSocket(WebSocket socket) {
        Iterator<Map.Entry<String, Socket>> itr = socketMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, Socket> entry = itr.next();
            if (socket != entry.getValue()) continue;

            entry.setValue(null);
            break;
        }
    }

    public ConcurrentLinkedQueue<Socket> listSocket(StringBuffer groupName) {
        if (socketMap.size() == 0) return null;

        ConcurrentLinkedQueue<Socket> socketList = new ConcurrentLinkedQueue<>();
        Iterator<Map.Entry<String, Socket>> itr = socketMap.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry<String, Socket> entry = itr.next();
            Socket socket = entry.getValue();
            if (!socket.containSocketTag(groupName.toString())) continue;
            socketList.add(socket);
        }

        return socketList;
    }

    public ConcurrentLinkedQueue<Socket> listSocket() {
        if (socketMap.size() == 0) return null;

        ConcurrentLinkedQueue<Socket> socketList = new ConcurrentLinkedQueue();
        Iterator<Map.Entry<String, Socket>> itr = socketMap.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, Socket> entry = itr.next();
            socketList.add(entry.getValue());
        }

        return socketList;
    }

    public void clearMessage() {
        if (socketMap.size() == 0) return;
        Iterator<Map.Entry<String, Socket>> itr = socketMap.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry<String, Socket> entry = itr.next();
            Socket socket = entry.getValue();
            if (socket == null) continue;
            socket.clearMessage();
        }
    }

    public synchronized void sendMessage() {
        ConcurrentLinkedQueue<Socket> queue = this.listSocket();
        for (Socket socket : queue) {
            socket.sendMessage();
        }
        queue = null;
    }
}
