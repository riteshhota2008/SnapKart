package com.asdabholkar.snapkart.modules;

import android.app.Application;

import com.asdabholkar.snapkart.application.SnapKartApp;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final SnapKartApp application;

    public AppModule(SnapKartApp application) {
        this.application = application;
    }

    @Provides
    SnapKartApp providesSnapKartApp() {
        return application;
    }

    @Provides
    Application providesApplication() {
        return application;
    }
}
