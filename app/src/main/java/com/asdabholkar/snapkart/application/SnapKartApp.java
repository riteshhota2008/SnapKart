package com.asdabholkar.snapkart.application;

import android.app.Activity;
import android.app.Application;

import com.asdabholkar.snapkart.R;
import com.asdabholkar.snapkart.modules.AppModule;
import com.asdabholkar.snapkart.modules.DaggerServicesComponent;
import com.asdabholkar.snapkart.modules.ServicesComponent;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class SnapKartApp extends Application {

    private ServicesComponent servicesComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.font_medium))
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        servicesComponent = DaggerServicesComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static SnapKartApp from(Activity activity) {
        return (SnapKartApp) activity.getApplication();
    }

    public ServicesComponent getServicesComponent() {
        return servicesComponent;
    }
}
