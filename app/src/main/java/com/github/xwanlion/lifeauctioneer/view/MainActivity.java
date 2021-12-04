package com.github.xwanlion.lifeauctioneer.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.github.xwanlion.lifeauctioneer.R;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //controller = Navigation.findNavController(this, R.id.activityListFragment);
        //return controller.navigateUp();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        ChangeMenuTextColor(menu);
        return true;
    }

    private void ChangeMenuTextColor(Menu menu) {
        if (menu == null || menu.size() == 0) return;
        for (int index = 0; index<menu.size(); index++) {
            MenuItem item = menu.getItem(index);
            SpannableString s = new SpannableString(item.getTitle());
            int color = ContextCompat.getColor(this.getApplicationContext(), R.color.colorPrimary);
            s.setSpan(new ForegroundColorSpan(color), 0, s.length(), 0);
            item.setTitle(s);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        switch (item_id) {
            case R.id.frg_menu_abouts:
                Intent intent = new Intent(this, MenuAboutActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
