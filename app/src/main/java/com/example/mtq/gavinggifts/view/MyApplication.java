package com.example.mtq.gavinggifts.view;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by mtq on 2016/8/21.
 */
public class MyApplication extends Application {
    public static RequestQueue getmQueue() {
        return mQueue;
    }

    public static RequestQueue mQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        mQueue= Volley.newRequestQueue(getApplicationContext());
        mQueue.start();
    }
}
