package com.jess.arms.base;

import android.app.Application;
import android.content.Context;

import com.jess.arms.base.delegate.AppDelegate;

public class MzSDK {
    public static AppDelegate getAppDelegate() {
        return mAppDelegate;
    }

    private static AppDelegate mAppDelegate;

    public static void init(Application base){
        if (mAppDelegate == null) {
            mAppDelegate = new AppDelegate(base);
        }
        mAppDelegate.attachBaseContext(base);
        mAppDelegate.onCreate(base);
    }

    public static void onCreate(Application base){
        if (mAppDelegate != null) {
            mAppDelegate.onCreate(base);
        }
    }

    public static void onTerminate(Application base) {
        if (mAppDelegate != null) {
            mAppDelegate.onTerminate(base);
        }
    }
}
