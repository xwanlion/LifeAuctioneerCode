package com.github.xwanlion.lifeauctioneer.view;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.util.AndroidPermissionHelper;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.runner.RunWith;

import java.io.File;

import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AImageViewTester extends ABaseTester {

    public AImageViewTester() {
        super();
    }

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.READ_EXTERNAL_STORAGE);


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

    public void toLoadImage(Activity activity, int imageViewId, String imageFile) {
        Instrumentation.ActivityResult imgGalleryResult = generateImageViewActivityResult(activity, imageFile);
        intending(not(isInternal())).respondWith(imgGalleryResult);
        Espresso.onView(ViewMatchers.withId(imageViewId)).perform(ViewActions.click());
    }

    public void assertImageLoaded(Activity activity, int imageViewId) {
        Matcher matcher = this.getLoadedImageMatcher(activity, imageViewId);
        Espresso.onView(ViewMatchers.withId(imageViewId)).check(ViewAssertions.matches(matcher));
    }

    public Instrumentation.ActivityResult generateImageViewActivityResult(Activity activity, String imageFile) {
        if (activity == null) throw new RuntimeException("ERR_ACTIVITY_CAN_NOT_BE_NULL");

        AndroidPermissionHelper.requireExternalStoragePermissionIfNeeded(activity);
        File file = new File(imageFile);
        if (file.isFile() == false) throw new RuntimeException("ERR_IMAGE_FILE_CAN_NOT_BE_NULL");

        Intent resultData = new Intent();
        resultData.setData(Uri.fromFile(file));

        return new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

    }

    public Matcher getLoadedImageMatcher(Activity activity, int imageViewId) {
        return new Matcher() {
            @Override
            public boolean matches(Object item) {
                ImageView view = activity.findViewById(imageViewId);
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

    public boolean hasLoadImage(Activity activity, int imageViewId) {
        ImageView view = activity.findViewById(imageViewId);
        return view.getDrawable() != null;
    }

}