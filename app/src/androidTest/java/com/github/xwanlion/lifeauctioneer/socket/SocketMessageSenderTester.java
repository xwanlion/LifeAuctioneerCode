package com.github.xwanlion.lifeauctioneer.socket;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.xwanlion.lifeauctioneer.model.app.UserType;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SocketMessageSenderTester {

//    private void putSocket(String sessionId, String tag, Socket socket, String url, int port) throws URISyntaxException {
//        WebSocket webSocket = getSocket(url, port);
//        socket.setSocket(webSocket);
//        socket.setSessionId(sessionId);
//        socket.addSocketTag(tag);
//        SocketContainer.putSocket(socket);
//
//    }
//
//    private WebSocket getSocket(String url, int port) throws URISyntaxException {
//        URI uri = new URI("ws://" + url + ":" + port + "/");
//        return new WebSocketClient(uri) {
//                @Override
//                public void onOpen(ServerHandshake handshakedata) {}
//
//                @Override
//                public void onMessage(String message) {}
//
//                @Override
//                public void onClose(int code, String reason, boolean remote) {}
//
//                @Override
//                public void onError(Exception ex) {}
//            };
//    }
//
//    @Test
//    public void testSendMessage() throws URISyntaxException {
//        String sessionId1 = UUID.randomUUID().toString();
//        String tag1 = "tag1";
//        Socket socket1 = new Socket();
//        String url1 = "192.168.43.1"; // must connect successful
//        int port1 = 9090;
//        putSocket(sessionId1, tag1, socket1, url1, port1);
//
//        String sessionId2 = UUID.randomUUID().toString();
//        String tag2 = "tag2";
//        Socket socket2 = new Socket();
//        String url2 = "192.168.43.2"; // must connect fail
//        int port2 = 9090;
//        putSocket(sessionId2, tag2, socket2, url2, port2);
//
//        SocketMessage message = new SocketMessage();
//        message.setText("text1");
//        message.setSenderType(UserType.SYSTEM);
//        message.setMsgType(MessageType.POINT_AUCTION_LOT);
//        message.setSenderSessionId("senderSessionId");
//        SocketMessageSender.sendMessage(sessionId1, message);
//
//        assertEquals(true, socket1.getMessageSize() == 1);
//        assertEquals(true, socket2.getMessageSize() == 0);
//
//        // -------++++++++++++++++++++++++++++++++++--------------------------
//        SocketMessage message2 = new SocketMessage();
//        message2.setText("text2");
//        message2.setSenderType(UserType.SYSTEM);
//        message2.setMsgType(MessageType.POINT_AUCTION_LOT);
//        message2.setSenderSessionId("senderSessionId");
//        SocketMessageSender.sendMessage(new StringBuffer(tag1), message2);
//
//        assertEquals(true, socket1.getMessageSize() == 2);
//        assertEquals(true, socket2.getMessageSize() == 0);
//
//        // -------++++++++++++++++++++++++++++++++++--------------------------
//        SocketMessage message3 = new SocketMessage();
//        message3.setText("text3");
//        message3.setSenderType(UserType.SYSTEM);
//        message3.setMsgType(MessageType.POINT_AUCTION_LOT);
//        message3.setSenderSessionId("senderSessionId");
//        SocketMessageSender.sendMessage(new StringBuffer(tag2), message3);
//
//        assertEquals(true, socket1.getMessageSize() == 2);
//        assertEquals(true, socket2.getMessageSize() == 1);
//
//    }

}
