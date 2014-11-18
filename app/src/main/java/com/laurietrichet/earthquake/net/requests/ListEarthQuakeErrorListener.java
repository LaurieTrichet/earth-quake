package com.laurietrichet.earthquake.net.requests;

import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.laurietrichet.earthquake.model.EarthQuake;
import com.laurietrichet.earthquake.net.VolleyHelper;
import com.laurietrichet.earthquake.net.parsers.EarthQuakeParser;

import java.util.List;

import static com.laurietrichet.earthquake.net.client.AbstractWebServiceClient.WebServiceClientListener;
import static com.laurietrichet.earthquake.net.requests.ListEarthQuakeRequest.URL_EARTHQUAKE;

/**
 * A custom error listener for ListEarthQuakeErrorListener to manage the lack of server cache
 */
class ListEarthQuakeErrorListener implements Response.ErrorListener {

    private final static String TAG = ListEarthQuakeErrorListener.class.getName();

    private final WebServiceClientListener mListener;

    ListEarthQuakeErrorListener (final WebServiceClientListener listener){
        mListener = listener;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Cache.Entry entry = null;
        try {
            entry = VolleyHelper.INSTANCE.getCache().get(URL_EARTHQUAKE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
        }
        if (entry != null){
            List<EarthQuake> arrayEarthQuake;
            try {
                arrayEarthQuake = EarthQuakeParser.parse(
                        new String(entry.data,
                                HttpHeaderParser.parseCharset(entry.responseHeaders)));
                mListener.onSuccess(arrayEarthQuake);
            } catch (Exception e) {
                e.printStackTrace();
                mListener.onError(new Error(volleyError.getLocalizedMessage()));
            }
        } else {
            mListener.onError(new Error(volleyError.getLocalizedMessage()));
        }
    }
}
