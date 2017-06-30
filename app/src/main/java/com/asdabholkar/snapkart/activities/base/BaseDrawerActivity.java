package com.asdabholkar.snapkart.activities.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.asdabholkar.snapkart.R;

import butterknife.ButterKnife;

public class BaseDrawerActivity extends BaseActivity {

    private static final String TAG = BaseDrawerActivity.class.getName();
    private static final int DRAWER_CLOSE_DELAY = 250;

    private Handler handler;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
    }

    public void setupDrawer() {
        toolbar = ButterKnife.findById(this, R.id.toolbar);
        drawerLayout = ButterKnife.findById(this, R.id.drawer_layout);
        navigationView = ButterKnife.findById(this, R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onDrawerItemSelected(item.getItemId());
                return false;
            }
        });
    }

    public void setDrawerIndicatorEnabled() {
        toolbar.setNavigationIcon(R.drawable.drawer);
        toolbar.setNavigationContentDescription(R.string.cd_open_drawer);
    }

    public void setDrawerSelectedItem(@IdRes int itemId) {
        navigationView.setCheckedItem(itemId);
    }

    private void onDrawerItemSelected(@IdRes int itemId) {
        Intent launchIntent = null;

        switch (itemId) {

        }
    }

    private void launchDrawerItem(final Intent intent) {
        if (intent != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    // overridePendingTransition(R.anim.activity_fade_enter_sg, R.anim.activity_fade_exit_sg);
                }
            }, DRAWER_CLOSE_DELAY);
        }
    }

    public boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(navigationView);
    }

    public void openDrawer() {
        drawerLayout.openDrawer(navigationView);
    }

    public void closeDrawer() {
        drawerLayout.closeDrawer(navigationView);
    }

    public boolean toggleDrawer(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerVisible(navigationView)) {
                closeDrawer();
            } else {
                openDrawer();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpen()) {
            closeDrawer();
            return;
        }
        super.onBackPressed();
    }
}
