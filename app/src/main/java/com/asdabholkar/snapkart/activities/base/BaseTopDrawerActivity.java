package com.asdabholkar.snapkart.activities.base;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;

public class BaseTopDrawerActivity extends BaseDrawerActivity {

    @Override
    public void setupToolbar() {
        super.setupToolbar();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void setupDrawer() {
        super.setupDrawer();

        setDrawerIndicatorEnabled();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!isTaskRoot()) {
            //overridePendingTransition(R.anim.activity_fade_enter_sg, R.anim.activity_fade_exit_sg);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggleDrawer(item) || super.onOptionsItemSelected(item);
    }
}