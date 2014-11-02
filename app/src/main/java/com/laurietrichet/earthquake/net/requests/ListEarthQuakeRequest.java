package com.laurietrichet.earthquake.net.requests;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.laurietrichet.earthquake.model.EarthQuake;
import com.laurietrichet.earthquake.net.parsers.EarthQuakeParser;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.android.volley.Response.error;
import static com.android.volley.Response.success;
import static com.laurietrichet.earthquake.net.client.AbstractWebServiceClient.WebServiceClientListener;

/**
 * Subclass of JsonRequest to call earth quake webservice and get a list of EarthQuake objects
 */
public class ListEarthQuakeRequest extends JsonRequest <List<EarthQuake>>{

    private static final String URL_EARTHQUAKE = "http://www.seismi.org/api/eqs/";

    /**
     * Construct a request to be pushed in the Volley request queue for processing
     * @param listener will be called when the request has successfully finished
     */
    public ListEarthQuakeRequest(final WebServiceClientListener listener) {

        super(Method.GET, URL_EARTHQUAKE, null, new Response.Listener<List<EarthQuake>>() {
            @Override
            public void onResponse(List<EarthQuake> earthQuakes) {
                listener.onSuccess(earthQuakes);
            }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    listener.onError(new Error(volleyError.getLocalizedMessage()));
                }
            });
    }

    @Override
    protected Response<List<EarthQuake>> parseNetworkResponse(NetworkResponse networkResponse) {
        List<EarthQuake> arrayEarthQuake;
        String jsonString;
        try {
            jsonString = new String (networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return error(new VolleyError(e));
        }

        try {
            arrayEarthQuake = EarthQuakeParser.parse(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return error(new VolleyError(e));
        } catch (Exception e) {
            e.printStackTrace();
            return error(new VolleyError(e));
        }

        return success(arrayEarthQuake,
                HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

}
