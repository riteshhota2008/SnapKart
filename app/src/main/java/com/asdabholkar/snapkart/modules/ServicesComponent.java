package com.asdabholkar.snapkart.modules;

import com.asdabholkar.snapkart.activities.home.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        ClarifaiModule.class
})

public interface ServicesComponent {
    void inject(HomeActivity homeActivity);
}
