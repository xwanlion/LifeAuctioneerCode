package com.github.xwanlion.lifeauctioneer;

import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;

import com.github.xwanlion.lifeauctioneer.model.app.BidderExpectingLot;
import com.github.xwanlion.lifeauctioneer.socket.Socket;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import cn.hutool.json.JSONUtil;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
//    @Test
//    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//    }

//    @Test
//    public void stringToList() {
//        String json = "[{\"lotId\":1,\"expectingPrice\":1520}]";
//        byte[] decoded = Base64.decode(json, Base64.DEFAULT);
//        Logger.i("decode:" + decoded);
//        Logger.i("decode:" + new String(decoded));
//        assertEquals(4, 2 + 2);
//    }

//    @Test
//    public void testHashTable() {
//        Map<String, String> hashtable = new Hashtable<>();
//        hashtable.put("a", "aa");
//        hashtable.put("b", "bb");
//        hashtable.put("c", "cc");
//        hashtable.put("d", "dd");
//        hashtable.put("e", "ee");
//
//        Iterator<Map.Entry<String, String>> itr = hashtable.entrySet().iterator();
//        while (itr.hasNext()) {
//            Map.Entry<String, String> entry = itr.next();
//            if (entry.getKey().equals("c")) {
//                hashtable.remove(entry.getKey());
//            }
//        }
//    }

    @Test
    public void testConcurrentHashQueue() {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue();
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");

        String str = JSONUtil.toJsonStr(queue);
        System.out.println(str);
        System.out.println(queue.size());
        queue.clear();
        System.out.println(queue.size());

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getLayoutInflater().setFactory(new android.view.LayoutInflater.Factory(){
//            public View onCreateView(String name,Context context,AttributeSet attrs){
//                try{
//                    LayoutInflater inflater = getLayoutInflater();
//                    final View view = inflater.createView(name, null, attrs);
//                    if(view instanceof TextView){
//                        ((TextView)view).setTextColor(Color.RED);
//                    }
//                    return view;
//                }
//                catch(InflateException e){
//                    e.printStackTrace();
//                }
//                catch(ClassNotFoundException e){
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        });
//        menu.add(Menu.NONE, MENU_REVET, 0, R.string.revert).setEnabled(true)
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//        menu.add(Menu.NONE, MENU_SAVE, 0, R.string.done).setEnabled(true).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//        return super.onCreateOptionsMenu(menu);
//
//    }

}