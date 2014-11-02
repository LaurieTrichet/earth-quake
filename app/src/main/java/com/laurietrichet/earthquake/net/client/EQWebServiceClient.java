package com.laurietrichet.earthquake.net.client;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.laurietrichet.earthquake.model.EarthQuake;
import com.laurietrichet.earthquake.net.VolleyHelper;
import com.laurietrichet.earthquake.net.requests.ListEarthQuakeRequest;

import java.util.List;

/**
 * IWebServiceClient implementation to get {@link com.laurietrichet.earthquake.model.EarthQuake} objects.
 * Use {@link com.android.volley.toolbox.Volley} library
 */
public class EQWebServiceClient extends AbstractWebServiceClient<List<EarthQuake>> {

    private final Context mContext;

    public static final String ENTRY_POINT_LIST_EQ = "ENTRY_POINT_LIST_EQ";

    public EQWebServiceClient(Context context){
        mContext = context;
    }

    @Override
    protected Request createRequest(final String entryPoint,
                                    final Bundle params,
                                    final WebServiceClientListener listener) {
        if (entryPoint.equals(ENTRY_POINT_LIST_EQ)){
            return new ListEarthQuakeRequest( listener);
        }
        return null;
    }

    @Override
    protected int send (Request request){
        return (request == VolleyHelper.INSTANCE.addToRequestQueue(mContext, request))? 0 : -1;
    }
}
