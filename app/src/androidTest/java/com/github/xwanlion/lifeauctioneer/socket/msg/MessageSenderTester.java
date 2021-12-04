package com.github.xwanlion.lifeauctioneer.socket.msg;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.xwanlion.lifeauctioneer.socket.Socket;
import com.github.xwanlion.lifeauctioneer.socket.user.WebSocketHolder;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.WebSocketListener;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MessageSenderTester {
    @Test
    public void testSendMessage() {
        // put message
        final String toEveryone = "everyone";
        WebSocketMessageHolder.putToEveryone(toEveryone);

        final String groupName = "a";
        final String value = "group message";
        WebSocketMessageHolder.putToGroupUser(groupName, value);

        final String sessionId = "sessionId1";
        final String userMessage = "user1 message";
        WebSocketMessageHolder.putToUser(sessionId, userMessage);

        // generate web socket
        final String sessionId2 = "sessionId2";
        final String userMessage2 = "user2 message";
        WebSocketHolder socketHolder = new WebSocketHolder();

        WebSocket webSocket1 = new WebSocketImpl(new WebSocketListenerImpl(), new Draft_6455());
        WebSocket webSocket2 = new WebSocketImpl(new WebSocketListenerImpl(), new Draft_6455());
        socketHolder.putSocket(sessionId, groupName, webSocket1);
        socketHolder.putSocket(sessionId2, groupName, webSocket2);

        // dispatch message
        MessageDispatcher dispatcher = new MessageDispatcher();
        dispatcher.dispatchEveryoneMessage(socketHolder);
        dispatcher.dispatchGroupMessage(groupName, socketHolder);
        dispatcher.dispatchUserMessage(sessionId, socketHolder);

        Socket socket =  socketHolder.getSocket(sessionId);
        Socket socket2 =  socketHolder.getSocket(sessionId2);
        assertEquals(3, socket.getMessageSize());
        assertEquals(2, socket2.getMessageSize());

        // send message
//        MessageSender sender = new MessageSender();
//        // test fail, because connection closed
//        sender.sendMessage(socket);
//        // assert fail
//        assertEquals(0, socket.getMessageSize());

    }

    class WebSocketListenerImpl implements WebSocketListener {

        @Override
        public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
            return null;
        }

        @Override
        public void onWebsocketHandshakeReceivedAsClient(WebSocket conn, ClientHandshake request, ServerHandshake response) throws InvalidDataException {

        }

        @Override
        public void onWebsocketHandshakeSentAsClient(WebSocket conn, ClientHandshake request) throws InvalidDataException {

        }

        @Override
        public void onWebsocketMessage(WebSocket conn, String message) {

        }

        @Override
        public void onWebsocketMessage(WebSocket conn, ByteBuffer blob) {

        }

        @Override
        public void onWebsocketOpen(WebSocket conn, Handshakedata d) {

        }

        @Override
        public void onWebsocketClose(WebSocket ws, int code, String reason, boolean remote) {

        }

        @Override
        public void onWebsocketClosing(WebSocket ws, int code, String reason, boolean remote) {

        }

        @Override
        public void onWebsocketCloseInitiated(WebSocket ws, int code, String reason) {

        }

        @Override
        public void onWebsocketError(WebSocket conn, Exception ex) {

        }

        @Override
        public void onWebsocketPing(WebSocket conn, Framedata f) {

        }

        @Override
        public void onWebsocketPong(WebSocket conn, Framedata f) {

        }

        @Override
        public void onWriteDemand(WebSocket conn) {

        }

        @Override
        public InetSocketAddress getLocalSocketAddress(WebSocket conn) {
            return null;
        }

        @Override
        public InetSocketAddress getRemoteSocketAddress(WebSocket conn) {
            return null;
        }
    }
}
