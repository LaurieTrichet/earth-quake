package com.laurietrichet.earthquake.net.client;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.laurietrichet.earthquake.model.EarthQuake;
import com.laurietrichet.earthquake.net.requests.EarthQuakeRequest;

import java.util.List;

/**
 * Created by laurie on 26/10/2014.
 */
public class EarthQuakeLWS implements ILightWebServiceClient {

    private static final String URL_EARTHQUAKE = "http://www.seismi.org/api/eqs/";

    public EarthQuakeLWS (){

    }

    private Response.Listener <List<EarthQuake>> mListener = new Response.Listener<List<EarthQuake>>() {
        @Override
        public void onResponse(List<EarthQuake> earthQuakes) {
            //TODO manage to pass the info
        }
    };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            //TODO manage to pass the error
        }
    };

    @Override
    public void get (String url){

        EarthQuakeRequest request = new EarthQuakeRequest(Request.Method.GET, URL_EARTHQUAKE, null,
                mListener, mErrorListener);
    }

}
