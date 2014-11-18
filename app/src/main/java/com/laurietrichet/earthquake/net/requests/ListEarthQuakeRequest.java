package com.laurietrichet.earthquake.net.requests;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.laurietrichet.earthquake.model.EarthQuake;
import com.laurietrichet.earthquake.net.VolleyHelper;
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
    private final static String TAG = ListEarthQuakeRequest.class.getName();

    static final String URL_EARTHQUAKE = "http://www.seismi.org/api/eqs/";

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
        }, new ListEarthQuakeErrorListener(listener));
        this.setShouldCache(true);
    }

    @Override
    protected Response<List<EarthQuake>> parseNetworkResponse(NetworkResponse networkResponse) {
        List<EarthQuake> arrayEarthQuake;
        String jsonString;
        try {
            jsonString = new String (networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            arrayEarthQuake = EarthQuakeParser.parse(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return error(new VolleyError(e));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return error(new VolleyError(e));
        } catch (Exception e) {
            e.printStackTrace();
            return error(new VolleyError(e));
        }

        //Ignore no-cache cache directive because we want the apps runs online
        return success(arrayEarthQuake,
                VolleyHelper.parseCacheHeaders(networkResponse));
    }
}
