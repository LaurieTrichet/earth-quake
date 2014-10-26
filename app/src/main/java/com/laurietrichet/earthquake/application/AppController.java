package com.laurietrichet.earthquake.application;

import android.app.Application;

import com.laurietrichet.earthquake.utility.VolleyHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by laurie on 26/10/2014.
 * from http://www.androidhive.info/2014/05/android-working-with-volley-library-1/
 */
public class AppController extends Application {

    private static final String TAG = "DEFAULT";

    private static AppController mInstance;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);


    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        VolleyHelper.INSTANCE.init(getApplicationContext());
    }

    public static SimpleDateFormat getDateFormat (){
        return mDateFormat;
    }
}
