package com.example.thehungrybunch20;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class MyCustomApplication extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
