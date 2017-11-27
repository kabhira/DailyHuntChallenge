package com.akhare.dailyhuntchallenge.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by akhare on 10/18/15.
 */
public class CustomApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext(){
        return mContext;
    }
}
