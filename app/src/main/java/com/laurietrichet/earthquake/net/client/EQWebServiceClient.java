package com.laurietrichet.earthquake.net.client;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.laurietrichet.earthquake.model.EarthQuake;
import com.laurietrichet.earthquake.net.requests.EarthQuakeRequest;
import com.laurietrichet.earthquake.net.VolleyHelper;

import java.util.List;

/**
 * Created by laurie on 26/10/2014.
 * IWebServiceClient implementation to deal with @link EarthQuake objects
 */
public class EQWebServiceClient implements IWebServiceClient {

    private static final String URL_EARTHQUAKE = "http://www.seismi.org/api/eqs/";

    private final Context mContext;

    public EQWebServiceClient(Context context){
        mContext = context;
    }

    @Override
    public void get(final WebServiceClientListener listener) {

        Response.Listener <List<EarthQuake>> mListener = new Response.Listener<List<EarthQuake>>() {
            @Override
            public void onResponse(List<EarthQuake> earthQuakes) {
                listener.onSuccess(earthQuakes);
            }
        };

        Response.ErrorListener mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onError(new Error(volleyError.getLocalizedMessage()));
            }
        };

        EarthQuakeRequest request = new EarthQuakeRequest(Request.Method.GET, URL_EARTHQUAKE, null,
                mListener, mErrorListener);

        VolleyHelper.INSTANCE.addToRequestQueue(mContext, request);
    }
}
