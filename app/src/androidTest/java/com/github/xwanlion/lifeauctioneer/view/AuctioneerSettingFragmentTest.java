package com.github.xwanlion.lifeauctioneer.view;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.github.xwanlion.lifeauctioneer.BeanManager;
import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AuctioneerSettingFragmentTest extends ABaseTester {
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
    public ActivityTestRule mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void auctioneerSetting_newAuction_auctioneerAndPasswordIsNull_true() {
        launchFragment(mActivityRule, R.id.hostManSettingFragment, this.newAuctions);

        Espresso.onView(ViewMatchers.withId(R.id.txt_auction_host_man))
                .check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.withText(""))));

        Espresso.onView(ViewMatchers.withId(R.id.txt_auction_host_password))
                .check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.withText(""))));

    }

    @Test
    public void auctioneerSetting_oldAuction_auctioneerAndPasswordAutoFill_true() {
        launchFragment(mActivityRule, R.id.hostManSettingFragment, this.oldAuctions);

        Espresso.onView(ViewMatchers.withId(R.id.txt_auction_host_man))
                .check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.withText(oldAuctions.getAuctioneerName()))));

        Auctioneer auctioneer = BeanManager.auctioneerService.getAuctioneer(oldAuctions.getAuctioneerId());
        Espresso.onView(ViewMatchers.withId(R.id.txt_auction_host_password))
                .check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.withText(auctioneer.getPassword()))));

    }

    @Test
    public void auctioneerSetting_auctioneerCanNotBeNull_true() {
        launchFragment(mActivityRule, R.id.hostManSettingFragment, this.newAuctions);

        Espresso.onView(ViewMatchers.withId(R.id.txt_auction_host_man))
                .check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.withText(""))));

        Espresso.onView(ViewMatchers.withId(R.id.btn_host_man_setting_next_step))
                .perform(ViewActions.click());

        // txt_auction_host_man
        Espresso.onView(ViewMatchers.withId(R.id.txt_auction_host_man))
                .check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.withHint(R.string.hits_auction_host_man))));
    }

    @Test
    public void auctioneerSetting_PasswordCanBeNull_true() {
        launchFragment(mActivityRule, R.id.hostManSettingFragment, this.newAuctions);

        String auctioneer = "auctioneer";
        Espresso.onView(ViewMatchers.withId(R.id.txt_auction_host_man))
                .perform(ViewActions.typeText(auctioneer));

        Espresso.onView(ViewMatchers.withId(R.id.txt_auction_host_man))
                .check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.withText(auctioneer))));

        Espresso.onView(ViewMatchers.withId(R.id.txt_auction_host_password))
                .check(ViewAssertions.matches(Matchers.allOf(ViewMatchers.withText(""))))
                .perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_host_man_setting_next_step))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.txt_fix_number_money))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}