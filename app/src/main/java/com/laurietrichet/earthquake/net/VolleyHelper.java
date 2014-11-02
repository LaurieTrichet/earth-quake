package com.laurietrichet.earthquake.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

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
     * @return the request given in parameter see method add {@link com.android.volley.RequestQueue}
     */
    public Request addToRequestQueue(Context context,Request request) {
        checkInit (context);
        return mRequestQueue.add(request);
    }

    private void checkInit (Context context){
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }
}
