package com.github.xwanlion.lifeauctioneer.socket.msg;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.xwanlion.lifeauctioneer.util.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MessageHolderTester {
    @Test
    public void testPutAndGetMessage() {
        MessageHolder holder = new MessageHolder();
        final String groupName = "all";
        holder.putMessage(groupName, "all");
        String str = holder.getMessage(groupName);
        Logger.d("str:" + str); ;
        assertEquals (true, str.indexOf("all") > 0);
    }
}
