package com.laurietrichet.earthquake.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.laurietrichet.earthquake.net.requests.EarthQuakeRequest;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by laurie on 26/10/2014.
 */
public enum  VolleyHelper {

    INSTANCE;

    private RequestQueue mRequestQueue = null;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

    public RequestQueue getRequestQueue(Context context) {
        checkInit (context);
        return mRequestQueue;
    }

    public static SimpleDateFormat getDateFormat (){
        return mDateFormat;
    }

    public void addToRequestQueue(Context context,EarthQuakeRequest request) {
        checkInit (context);
        mRequestQueue.add(request);
    }

    private void checkInit (Context context){
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }
}
