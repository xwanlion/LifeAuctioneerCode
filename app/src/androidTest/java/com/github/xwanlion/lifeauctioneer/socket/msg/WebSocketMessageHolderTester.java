package com.github.xwanlion.lifeauctioneer.socket.msg;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.xwanlion.lifeauctioneer.util.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class WebSocketMessageHolderTester {
    @Test
    public void testPutAndGetMessage() {
        String user = "user";
        String sessionId = "sessionId";
        WebSocketMessageHolder.putToUser(sessionId, user);
        String userMessage = WebSocketMessageHolder.getUserMessage(sessionId);
        assertEquals(true, userMessage.indexOf(user) > 0);

        String groupName = "group";
        String groupMessage = "group message";
        WebSocketMessageHolder.putToGroupUser(groupName, groupMessage);
        String gMessage = WebSocketMessageHolder.getGroupMessage(groupName);
        assertEquals(true, gMessage.indexOf(groupMessage) > 0);

        String everyoneMessage = "everyone";
        WebSocketMessageHolder.putToEveryone(everyoneMessage);
        String everyoneMsg = WebSocketMessageHolder.getEveryoneMessage();
        assertEquals(true, everyoneMsg.indexOf(everyoneMessage) > 0);
    }
}
