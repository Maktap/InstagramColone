package com.iot.instagramcolone;

import android.app.Application;

import com.parse.Parse;

public class ParseClassStarter  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("nLCx1LsAesBKfxEKgSerMNBckSojMxEUxg4rD2Sg")
                .clientKey("NdbB4oSo7mPdOJIHDo1vvLwBsGOOptlxU09pUQRg")
                .server("https://parseapi.back4app.com/")
                .build());

    }
}
