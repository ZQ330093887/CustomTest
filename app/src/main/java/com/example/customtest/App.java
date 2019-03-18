package com.example.customtest;

import android.app.Application;

import com.example.customtest.widget.screenadaptation.ScreenAdaptation;

/**
 * Created by ZQiong on 2017/10/17.
 */

public class App extends Application {
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }


    public static App getmInstance() {
        return mInstance;
    }
}
