package com.github.xwanlion.lifeauctioneer.view;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.internal.inject.InstrumentationContext;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.github.xwanlion.lifeauctioneer.R;
import com.github.xwanlion.lifeauctioneer.conf.System;
import com.github.xwanlion.lifeauctioneer.model.app.Auctioneer;
import com.github.xwanlion.lifeauctioneer.model.app.Auctions;
import com.github.xwanlion.lifeauctioneer.model.app.MoneyCreationWay;
import com.github.xwanlion.lifeauctioneer.util.Logger;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

@RunWith(AndroidJUnit4.class)
public class MoneySettingFragmentTest extends ABaseTester {
    private static Auctions newAuctions = new Auctions();
    private static Auctions oldAuctions = new Auctions();
    private static Auctioneer newAuctioneer = new Auctioneer();
    private static Auctioneer oldAuctioneer = new Auctioneer();

    {
        oldAuctions.setId(4);
        oldAuctions.setAuctioneerId(1);
        oldAuctions.setAuctioneerName("auct");
        oldAuctions.setDate(1618971140205L);
        oldAuctions.setDate(-1);
        oldAuctions.setMoneyCreationWay(1);
        oldAuctions.setMoney(6000);
        oldAuctions.setAmountPerAge(101);

        oldAuctioneer.setId(1);
        oldAuctioneer.setUsername("auct");
        oldAuctioneer.setPassword("psw");
    }

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(MainActivity.class);

    // will not set default value, as auction's id is not greater than zero.
    @Test
    public void moneySetting_newAuction_newAuctioneer_defaultToFixedMoneyCreationWay_true() {
        launchFragment(mActivityRule, R.id.moneySettingFragment, this.newAuctions, this.newAuctioneer);

        this.checkIfDefault();

    }

    @Test
    public void moneySetting_newAuction_newAuctioneer_setToByAgeThenDefaultToFixedMoneyCreationWay_true() {
        launchFragment(mActivityRule, R.id.moneySettingFragment, this.newAuctions, this.newAuctioneer);

        this.checkIfDefault();

        Espresso.onView(ViewMatchers.withId(R.id.rdb_get_money_by_age))
                .perform(ViewActions.click());

        this.checkIfByAge();

        Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                .perform(ViewActions.click());

        this.checkIfDefault();

    }

    @Test
    public void moneySetting_newAuction_newAuctioneer_mustSelectACreationWay_true() {
        launchFragment(mActivityRule, R.id.moneySettingFragment, this.newAuctions, this.newAuctioneer);

        this.checkIfDefault();

        RadioButton radioButtonFixedAmount = mActivityRule.getActivity().findViewById(R.id.rdb_fix_number_money);
        radioButtonFixedAmount.setChecked(false);

        RadioButton radioButtonByAge = mActivityRule.getActivity().findViewById(R.id.rdb_get_money_by_age);
        radioButtonByAge.setChecked(false);

        Espresso.onView(ViewMatchers.withId(R.id.btn_money_setting_next_step))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.lbl_get_fix_money)));

    }

    @Test
    public void moneySetting_newAuction_newAuctioneer_mustFillMoneyIfFixedAmount_true() {
        launchFragment(mActivityRule, R.id.moneySettingFragment, this.newAuctions, this.newAuctioneer);

        this.checkIfDefault();

        Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.txt_fix_number_money))
                .perform(ViewActions.typeText(""));

        Espresso.onView(ViewMatchers.withId(R.id.btn_money_setting_next_step))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.lbl_get_fix_money)));

    }

    @Test
    public void moneySetting_newAuction_newAuctioneer_FixedAmountMustGreaterThanZero_value0_false() {
        launchFragment(mActivityRule, R.id.moneySettingFragment, this.newAuctions, this.newAuctioneer);

        this.checkIfDefault();

        Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.txt_fix_number_money))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText(String.valueOf(0)), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_money_setting_next_step))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.lbl_get_fix_money)));

    }

    @Test
    public void moneySetting_newAuction_newAuctioneer_FixedAmountMustGreaterThanZero_value__1_false() {
        launchFragment(mActivityRule, R.id.moneySettingFragment, this.newAuctions, this.newAuctioneer);

        this.checkIfDefault();

        Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.txt_fix_number_money))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText("-1"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_money_setting_next_step))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.lbl_get_fix_money)));

    }

    @Test
    public void moneySetting_newAuction_newAuctioneer_ByAgeAmountMustGreaterThanZero_value__1_false() {
        launchFragment(mActivityRule, R.id.moneySettingFragment, this.newAuctions, this.newAuctioneer);

        this.checkIfDefault();

        Espresso.onView(ViewMatchers.withId(R.id.rdb_get_money_by_age))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.txt_get_money_by_age))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText("-1"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_money_setting_next_step))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.lbl_get_fix_money)));

    }

    @Test
    public void moneySetting_newAuction_newAuctioneer_ByAgeAmountMustGreaterThanZero_value0_false() {
        launchFragment(mActivityRule, R.id.moneySettingFragment, this.newAuctions, this.newAuctioneer);

        this.checkIfDefault();

        Espresso.onView(ViewMatchers.withId(R.id.rdb_get_money_by_age))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.txt_get_money_by_age))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText("0"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_money_setting_next_step))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                .check(ViewAssertions.matches(ViewMatchers.withText(R.string.lbl_get_fix_money)));

    }

    // auctioneer id can not be null
    @Test
    public void moneySetting_newAuction_newAuctioneer_submitByAgeNotSuccess_true() {
        launchFragment(mActivityRule, R.id.moneySettingFragment, this.newAuctions, this.newAuctioneer);

        this.checkIfDefault();

        Espresso.onView(ViewMatchers.withId(R.id.rdb_get_money_by_age))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.txt_get_money_by_age))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText("100"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_money_setting_next_step))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

    @Test
    public void moneySetting_oldAuction_oldAuctioneer_submitByAgeSuccess_true() {
        launchFragment(mActivityRule, R.id.moneySettingFragment, oldAuctions, oldAuctioneer);

        this.checkIfEdit();

        Espresso.onView(ViewMatchers.withId(R.id.rdb_get_money_by_age))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.txt_get_money_by_age))
                .perform(ViewActions.clearText())
                .perform(ViewActions.typeText("100"), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btn_money_setting_next_step))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.btn_bidder_list_item_next_step))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

    private void checkIfDefault() {
        Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                .check(ViewAssertions.matches(ViewMatchers.isChecked()));

        Espresso.onView(ViewMatchers.withId(R.id.txt_fix_number_money))
                .check(ViewAssertions.matches(ViewMatchers.withText(System.DEFAULT_FIXED_MONEY_AMOUNT + "")));
    }

    private void checkIfEdit() {
        if (oldAuctions.getMoneyCreationWay() == MoneyCreationWay.FIX_AMOUNT) {
            Espresso.onView(ViewMatchers.withId(R.id.rdb_fix_number_money))
                    .check(ViewAssertions.matches(ViewMatchers.isChecked()));

            Espresso.onView(ViewMatchers.withId(R.id.txt_fix_number_money))
                    .check(ViewAssertions.matches(ViewMatchers.withText(String.valueOf(oldAuctions.getMoney()))));
        } else {
            Espresso.onView(ViewMatchers.withId(R.id.rdb_get_money_by_age))
                    .check(ViewAssertions.matches(ViewMatchers.isChecked()));

            Espresso.onView(ViewMatchers.withId(R.id.txt_get_money_by_age))
                    .check(ViewAssertions.matches(ViewMatchers.withText(String.valueOf(oldAuctions.getAmountPerAge()))));
        }
    }

    private void checkIfByAge() {
        Espresso.onView(ViewMatchers.withId(R.id.rdb_get_money_by_age))
                .check(ViewAssertions.matches(ViewMatchers.isChecked()));

        //Context context = InstrumentationRegistry.getContext();
        Context context = ApplicationProvider.getApplicationContext();;
        String text = context.getString(R.string.txt_money_per_age);

        Espresso.onView(ViewMatchers.withId(R.id.txt_get_money_by_age))
                .check(ViewAssertions.matches(ViewMatchers.withText(text)));
    }

}