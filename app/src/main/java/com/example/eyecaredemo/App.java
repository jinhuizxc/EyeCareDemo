package com.example.eyecaredemo;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        SPUtils.init(this);

    }

    public static Context getAppContext() {
        return context;
    }
}
