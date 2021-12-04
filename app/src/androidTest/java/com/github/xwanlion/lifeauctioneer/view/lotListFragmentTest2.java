package com.github.xwanlion.lifeauctioneer.view;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.Lot;
import com.github.xwanlion.lifeauctioneer.util.AndroidPermissionHelper;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class lotListFragmentTest2 extends ABaseTester {
    private static Auctions newAuctions = new Auctions();
    private static Auctions oldAuctions = new Auctions();

    {
        oldAuctions.setId(4);
        oldAuctions.setAuctioneerId(1);
        oldAuctions.setAuctioneerName("auct");
        oldAuctions.setDate(1618971140205L);
        oldAuctions.setDate(-1);
        oldAuctions.setMoneyCreationWay(1);
        oldAuctions.setMoney(6000);
        oldAuctions.setAmountPerAge(101);
    }

    @Rule
    public IntentsTestRule mActivityRule = new IntentsTestRule(MainActivity.class, true, false);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE);


    @Test
    public void lotList_oldAuction_addLot_true() {
        launchFragment(mActivityRule, R.id.auctionLotListFragment, oldAuctions);

        Espresso.onView(ViewMatchers.withId(R.id.btn_recycle_view_add_item))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.btn_submit_lot_item))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        String lotName = "lot3";
        Espresso.onView(ViewMatchers.withId(R.id.txt_new_auction_item_name))
                .perform(ViewActions.typeText(lotName), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_auction_item_start_price))
                .perform(ViewActions.typeText("210"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_auction_item_increment))
                .perform(ViewActions.typeText("10"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_auction_item_desc))
                .perform(ViewActions.typeText("desc"), ViewActions.closeSoftKeyboard());

        // select Image
        //Espresso.onView(ViewMatchers.withId(R.id.img_new_auction_item)).perform(ViewActions.click());
        Instrumentation.ActivityResult imgGalleryResult = createImageGallerySetResultStub();
        Matcher<Intent> matcher = hasAction(Intent.ACTION_CHOOSER);
        //intending(matcher).respondWith(imgGalleryResult);
        intending(not(isInternal())).respondWith(imgGalleryResult);

        Espresso.onView(ViewMatchers.withId(R.id.img_new_auction_item)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.img_new_auction_item)).check(ViewAssertions.matches(hasImageSet()));

        Espresso.onView(ViewMatchers.withId(R.id.btn_submit_lot_item))
                .perform(ViewActions.click());

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.btn_lot_list_next_step))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.lbl_auction_lot_list_item_name), ViewMatchers.withText(lotName)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

//    private Instrumentation.ActivityResult createImageGallerySetResultStub() {
//        File dir = mActivityRule.getActivity().getExternalCacheDir();
//        //File file = new File("file:///storage/emulated/0/everyHider/20200708141717463.png");
//        AndroidPermissionHelper.requireExternalStoragePermissionIfNeeded(mActivityRule.getActivity());
//        File file = new File("/storage/emulated/0/LIFE_AUCTION/20210424103647356");
//        assertEquals(true, file.exists());
//        Uri uri = Uri.fromFile(file);
//        Parcelable myParcelable = (Parcelable) uri;
//
//        ArrayList<Parcelable> parcels = new ArrayList<>();
//        parcels.add(myParcelable);
//
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList(Intent.EXTRA_STREAM, parcels);
//
//        Intent resultData = new Intent();
//        resultData.putExtras(bundle);
//
//        return new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
//    }

    private Instrumentation.ActivityResult createImageGallerySetResultStub() {
        File dir = mActivityRule.getActivity().getExternalCacheDir();
        //File file = new File("file:///storage/emulated/0/everyHider/20200708141717463.png");
        AndroidPermissionHelper.requireExternalStoragePermissionIfNeeded(mActivityRule.getActivity());
        File file = new File("/storage/emulated/0/LIFE_AUCTION/20210424103647356");
        assertEquals(true, file.exists());
        Uri uri = Uri.fromFile(file);

        Intent resultData = new Intent();
        resultData.setData(uri);

        return new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
    }

    private Matcher hasImageSet() {
        return new Matcher() {
            @Override
            public boolean matches(Object item) {
                ImageView view = mActivityRule.getActivity().findViewById(R.id.img_new_auction_item);
                return view.getDrawable() != null;
            }

            @Override
            public void describeMismatch(Object item, Description mismatchDescription) {

            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

}