package com.github.xwanlion.lifeauctioneer.socket;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketFactory;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SocketContainerTester {

//    @Test
//    public void testPutSocket() throws URISyntaxException {
//        Socket socket = new Socket();
//        URI uri = new URI("ws://192.168.43.1:9090/");
//        WebSocket webSocket = new WebSocketClient(uri) {
//            @Override
//            public void onOpen(ServerHandshake handshakedata) {}
//
//            @Override
//            public void onMessage(String message) {}
//
//            @Override
//            public void onClose(int code, String reason, boolean remote) {}
//
//            @Override
//            public void onError(Exception ex) {}
//        };
//
//        String tag = "tag1";
//        String sessionId = UUID.randomUUID().toString();
//        socket.setSocket(webSocket);
//        socket.setSessionId(sessionId);
//        socket.addSocketTag(tag);
//        SocketContainer.putSocket(socket);
//
//        Socket getBackSocket = SocketContainer.getSocket(sessionId);
//        assertEquals(true, socket == getBackSocket);
//
//        List<Socket> socketList = SocketContainer.listSocket(new StringBuffer(tag));
//        assertEquals(true, socketList != null);
//        assertEquals(true, socketList.get(0) == socket);
//
//        List<Socket> socketList2 = SocketContainer.listSocket(new StringBuffer(tag + "44"));
//        assertEquals(true, socketList2 == null || socketList2.size() == 0);
//
//        List<Socket> socketList3 = SocketContainer.listSocket();
//        assertEquals(true, socketList3 != null);
//        assertEquals(true, socketList3.get(0) == socket);
//
//    }

}
