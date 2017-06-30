package com.asdabholkar.snapkart.activities.base;

import android.support.v7.app.ActionBar;

public class BaseTopActivity extends BaseActivity {

    @Override
    public void setupToolbar() {
        super.setupToolbar();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}