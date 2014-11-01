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

import static com.android.volley.Response.ErrorListener;
import static com.android.volley.Response.error;
import static com.android.volley.Response.success;

/**
 * Subclass of JsonRequest to call earth quake webservice and get a list of EarthQuake objects
 */
public class EarthQuakeRequest extends JsonRequest <List<EarthQuake>>{

    /**
     * Construct a request to be pushed in the Volley request queue for processing
     * @param method GET, POST, PUT, DELETE
     * @param url url of the web service to reach
     * @param requestBody body for POST, PUT methods
     * @param listener will be called when the request has successfully finished
     * @param errorListener will be called when the request has failed
     */
    public EarthQuakeRequest(int method, String url, String requestBody,
                             Response.Listener<List<EarthQuake>> listener,
                             ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
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
