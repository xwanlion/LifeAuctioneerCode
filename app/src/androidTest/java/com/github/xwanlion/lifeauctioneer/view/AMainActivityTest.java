package com.github.xwanlion.lifeauctioneer.view;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.github.xwanlion.lifeauctioneer.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AMainActivityTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(MainActivity.class);;

    @Test
    public void mainActivity_init_UIAppear() {
        Espresso.onView(ViewMatchers.withId(R.id.btn_recycle_view_add_item))
            .check(ViewAssertions.matches(ViewMatchers.withContentDescription("Add")));
    }

    public void testSample() {
        //// 不做任何输入，直接点击登录
        //onView(allOf(withId(R.id.bt_login), isDisplayed())).perform(click());
        //onView(allOf(withId(R.id.tv_login_result), isDisplayed())).check(matches(withText("用户名为空")));

        //// 用户名是空，点击登录
        //onView(allOf(withId(R.id.et_name), isDisplayed())).perform(replaceText(names[0]), closeSoftKeyboard());
        //onView(allOf(withId(R.id.bt_login), isDisplayed())).perform(click());
        //onView(allOf(withId(R.id.tv_login_result), isDisplayed())).check(matches(withText("用户名为空")));

        //// 用户名格式错误，点击登录
        //onView(allOf(withId(R.id.et_name), isDisplayed())).perform(replaceText(names[1]), closeSoftKeyboard());
        //onView(allOf(withId(R.id.bt_login), isDisplayed())).perform(click());
        //onView(allOf(withId(R.id.tv_login_result), isDisplayed())).check(matches(withText("用户名格式错误")));

        // 用户名和密码都正确，点击登录
        //onView(allOf(withId(R.id.et_name), isDisplayed())).perform(replaceText(names[2]), closeSoftKeyboard());
        //onView(allOf(withId(R.id.et_pwd), isDisplayed())).perform(replaceText(pwds[2]), closeSoftKeyboard());
        //onView(allOf(withId(R.id.bt_login), isDisplayed())).perform(click());
        //onView(allOf(withId(R.id.tv_login_result), isDisplayed())).check(matches(withText("登录成功")));

    }
}
