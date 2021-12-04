package com.github.xwanlion.lifeauctioneer.view;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityImageViewTester extends AImageViewTester {

    public MainActivityImageViewTester() {
        super();
    }

    private static String IMAGE_FILE = "/storage/emulated/0/LIFE_AUCTION/20210424103647356";

    @Rule
    public IntentsTestRule testRule = new IntentsTestRule(MainActivity.class);

    public final void launchFragment(int fragmentId, Auctions auctions) {
        launchFragment(testRule, fragmentId, auctions);
    }


    public final Instrumentation.ActivityResult generateImageViewActivityResult(Activity activity, String imageFile) {
        return super.generateImageViewActivityResult(activity, imageFile);

    }

    public final Instrumentation.ActivityResult generateImageViewActivityResult(String imageFile) {
        return super.generateImageViewActivityResult(testRule.getActivity(), imageFile);
    }

    public final Instrumentation.ActivityResult generateImageViewActivityResult() {
        return super.generateImageViewActivityResult(testRule.getActivity(), this.IMAGE_FILE);
    }

    public final Matcher getLoadedImageMatcher(Activity activity, int imageViewId) {
        return super.getLoadedImageMatcher(activity, imageViewId);
    }

    public final Matcher getLoadedImageMatcher(int imageViewId) {
        return super.getLoadedImageMatcher(testRule.getActivity(), imageViewId);
    }

    @Override
    public final boolean hasLoadImage(Activity activity, int imageViewId) {
        return super.hasLoadImage(activity, imageViewId);
    }

    public final boolean hasLoadImage(int imageViewId) {
        return super.hasLoadImage(testRule.getActivity(), imageViewId);
    }

    @Override
    public final void toLoadImage(Activity activity, int imageViewId, String imageFile) {
        super.toLoadImage(activity, imageViewId, imageFile);
    }

    public final void loadImage(int imageViewId, String imageFile) {
        this.toLoadImage(testRule.getActivity(), imageViewId, imageFile);
    }

    public final void loadImage(int imageViewId) {
        this.loadImage(imageViewId, this.IMAGE_FILE);
    }

    @Override
    public final void assertImageLoaded(Activity activity, int imageViewId) {
        super.assertImageLoaded(activity, imageViewId);
    }

    public final void assertImageLoaded(int imageViewId) {
        super.assertImageLoaded(testRule.getActivity(), imageViewId);
    }

    @Override
    protected final void launchFragment(ActivityTestRule rule, int destinationId, Bundle bundle) {
        super.launchFragment(rule, destinationId, bundle);
    }

    protected final void launchFragment(int destinationId, Bundle bundle) {
        super.launchFragment(testRule, destinationId, bundle);
    }

    @Override
    protected final String getString(Activity activity, int resourceId) {
        return super.getString(activity, resourceId);
    }

    protected final String getString(int resourceId) {
        return this.getString(testRule.getActivity(), resourceId);
    }
}