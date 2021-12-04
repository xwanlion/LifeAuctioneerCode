package com.github.xwanlion.lifeauctioneer.socket;

import com.github.xwanlion.lifeauctioneer.socket.msg.MessageDispatcher;
import com.github.xwanlion.lifeauctioneer.socket.user.WebSocketHolder;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;
import com.github.xwanlion.lifeauctioneer.util.Logger;
import com.github.xwanlion.lifeauctioneer.util.MessageUtils;
import com.github.xwanlion.lifeauctioneer.view.MainActivity;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SocketServer extends WebSocketServer {
    private  static int socketPort = 0;
    private  static SocketServer socketServer = null;
    private static final String SOCKET_TAG = "DEFAULT";

    private WebSocketHolder socketHolder;
    private MessageDispatcher dispatcher;

    private static ExecutorService executor = new ThreadPoolExecutor(10, 10,
            60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue(10));

    private SocketServer(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        socketPort = port;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        Logger.i("web socket hand shake");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Logger.d("close socket:" + code + " reason:" + reason);
        socketHolder.resetSocket(conn);
    }


    @Override
    public void onMessage(WebSocket con, String message) {
        // Logger.i("web socket receive message:" + message);
        SocketMessage msg = JsonUtils.parseJson(message, SocketMessage.class);
        if (msg == null) {
            MessageUtils.responseError(con, "ERR_INVALID_MESSAGE_FORMAT");
            return;
        }

        this.identifyUser(con, msg);
    }

    private void identifyUser(WebSocket con, SocketMessage msg) {
        if (msg.getMsgType() == null || msg.getMsgType() != 0) return;

        String sessionId = msg.getSessionId();
        if (sessionId == null || sessionId.length() == 0) {
            Logger.i("ERR_SESSION_ID_CAN_NOT_BE_NULL");
            MessageUtils.responseError(con, "ERR_SESSION_ID_CAN_NOT_BE_NULL");
            return;
        }

        socketHolder.putSocket(sessionId, SOCKET_TAG, con);
        this.dispatchAndSendMessage();
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        Logger.i("Socket Receiver Exception:" + ex.toString());
    }

    @Override
    public void onStart() {
        dispatcher = new MessageDispatcher();
        socketHolder =  new WebSocketHolder();
//        timer.schedule(timerTask, 10000, 500);
    }

    @Override
    public void start() {
        Logger.i("SocketServer.start:" + socketPort);
        super.setConnectionLostTimeout(0);
        super.start();
        Logger.i("Start Up WebSocket Successful at port:" + socketPort);
    }

    public static boolean start(int port) {
        if (socketPort > 0) return false;
        if (port < 0) throw new RuntimeException("ERR_WEB_SOCKET_PORT_CANNOT_LESS_THAN_ZERO");

        try {
            socketServer = new SocketServer(port);
            socketServer.start();
            return true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void stop() throws IOException, InterruptedException {
        super.stop();
        dispatcher = null;
        socketHolder =  null;
        socketServer = null;
        socketPort = 0;
        Logger.i("Stop WebSocket Successful");
    }

    public static void stops() {
        try {
            socketServer.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isInRunning = false;
    private void dispatchAndSendMessage() {
        if (isInRunning == true) return;
        isInRunning = true;

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    dispatcher.dispatchEveryoneMessage(socketHolder);
                    socketHolder.sendMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    isInRunning = false;
                }
            }
        });

    }

}
