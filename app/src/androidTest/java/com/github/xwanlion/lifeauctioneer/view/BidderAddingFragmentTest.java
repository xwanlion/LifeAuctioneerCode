package com.github.xwanlion.lifeauctioneer.view;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BidderAddingFragmentTest extends ABaseTester {
    private static Auctions newAuctions = new Auctions();
    private static Auctions oldAuctions = new Auctions();

    {
        oldAuctions.setId(4);
        oldAuctions.setAuctioneerId(1);
        oldAuctions.setAuctioneerName("auct");
        oldAuctions.setDate(1618971140205L);
        oldAuctions.setState(-1);
        oldAuctions.setMoneyCreationWay(1);
        oldAuctions.setMoney(6000);
        oldAuctions.setAmountPerAge(101);
    }

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void bidderAdding_oldAuction_defaultToNull_true() {
        launchFragment(mActivityRule, R.id.bidderAddingFragment, oldAuctions);

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_bidder_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_bidder_password))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));

    }

    @Test
    public void bidderAdding_oldAuction_bidderNameCanNotBeNull_true() {
        launchFragment(mActivityRule, R.id.bidderAddingFragment, oldAuctions);

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_bidder_name))
                .check(ViewAssertions.matches(ViewMatchers.withText("")));

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_bidder_password))
                .perform(ViewActions.typeText("psw"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_submit_bidder))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_bidder_name))
                .check(ViewAssertions.matches(ViewMatchers.withHint(R.string.hint_new_bidder_name)));

    }

    @Test
    public void bidderAdding_oldAuction_passwordCanBeNull_true() {
        launchFragment(mActivityRule, R.id.bidderAddingFragment, oldAuctions);

        // txt_bidder_list_tips
        Espresso.onView(ViewMatchers.withId(R.id.txt_new_bidder_name))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText("bidder"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_bidder_password))
                .perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_submit_bidder))
                .perform(ViewActions.click());

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(ViewMatchers.withId(R.id.txt_bidder_list_tips))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.txt_bidder_list_tips)));

    }

    @Test
    public void bidderAdding_newAuction_saveFailed_true() {
        launchFragment(mActivityRule, R.id.bidderAddingFragment, newAuctions);

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_bidder_name))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText("bidder"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_bidder_password))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText("psw"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_submit_bidder))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.txt_new_bidder_name))
                .check(ViewAssertions.matches(ViewMatchers.withHint(R.string.hint_new_bidder_name)));

    }

}