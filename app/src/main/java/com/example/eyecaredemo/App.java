package com.example.eyecaredemo;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SPUtils.init(this);
    }
}
