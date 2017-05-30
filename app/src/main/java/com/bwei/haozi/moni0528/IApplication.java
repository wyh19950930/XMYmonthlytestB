package com.bwei.haozi.moni0528;

import android.app.Application;

import org.xutils.x;

/**
 * Created by haozi on 2017/5/28.
 */

public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
    }
}
