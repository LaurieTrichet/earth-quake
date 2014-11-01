package com.laurietrichet.earthquake.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.laurietrichet.earthquake.net.requests.EarthQuakeRequest;

/**
 * Singleton to wrap {@link com.android.volley.toolbox.Volley} library
 */
public enum  VolleyHelper {

    INSTANCE;

    private RequestQueue mRequestQueue = null;

    /**
     * Initialize and return the {@link com.android.volley.RequestQueue} to add volley request
     * @param context requested from the volley library
     * @param request request to add to the queue
     */
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
