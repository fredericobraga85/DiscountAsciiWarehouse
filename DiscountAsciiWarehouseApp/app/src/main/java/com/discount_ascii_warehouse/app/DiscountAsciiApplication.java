package com.discount_ascii_warehouse.app;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by mariobraga on 6/16/16.
 */
public class DiscountAsciiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
