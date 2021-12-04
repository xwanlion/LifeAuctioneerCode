/*
 * Copyright 2018 Zhenjie Yan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.xwanlion.lifeauctioneer.util;

import com.github.xwanlion.lifeauctioneer.model.app.UserType;
import com.github.xwanlion.lifeauctioneer.socket.MessageType;
import com.github.xwanlion.lifeauctioneer.socket.SocketMessage;

import org.java_websocket.WebSocket;


public class MessageUtils {

    public final static String SYSTEM_USER_ID = "sys";

    public static final long SYSTEM_MESSAGE_ID  = 0L;

    public static void responseError(WebSocket socket, String text) {
        SocketMessage message = new SocketMessage();
        message.createId();
        message.setMsgType(MessageType.RESPONSE_TEXT);
        message.setSessionId(MessageUtils.SYSTEM_USER_ID);
        message.setUserType(UserType.SYSTEM);
        message.setText(text);
        socket.send(message.toJson());
    }

}