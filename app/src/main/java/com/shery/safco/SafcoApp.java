package com.shery.safco;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by shery on 11/21/2016.
 */

public class SafcoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .addCustomStyle(AppCompatTextView.class, android.R.attr.textViewStyle)
                .setFontAttrId(R.attr.fontPath).build());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        /*MultiDex.install(this);*/
    }
}
