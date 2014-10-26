package com.laurietrichet.earthquake.utility;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by laurie on 26/10/2014.
 */
public enum  VolleyHelper {

    INSTANCE;

    private RequestQueue requestQueue;

    public void init(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
