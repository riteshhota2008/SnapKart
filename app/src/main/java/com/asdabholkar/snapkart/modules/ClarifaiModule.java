package com.asdabholkar.snapkart.modules;

import com.asdabholkar.snapkart.BuildConfig;
import com.asdabholkar.snapkart.R;

import javax.inject.Singleton;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class ClarifaiModule {

    @Provides
    @Singleton
    ClarifaiClient provideClarifaiClient() {
        ClarifaiClient client = new
                ClarifaiBuilder(BuildConfig.CLARIFAI_ID, BuildConfig.CLARIFAI_SECRET)
                .client(new OkHttpClient.Builder().build())
                .buildSync();
        return client;
    }
}
