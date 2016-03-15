package com.schoenherr.bumper;

import android.app.Application;
import android.content.Context;

/**
 * Created by Joe on 3/15/2016.
 */
public class Bumper extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        Bumper.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Bumper.context;
    }
}
