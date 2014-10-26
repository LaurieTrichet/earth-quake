package com.laurietrichet.earthquake.net.requests;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.laurietrichet.earthquake.model.EarthQuake;
import com.laurietrichet.earthquake.net.parsers.EarthQuakeParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.android.volley.Response.*;

/**
 * Created by laurie on 26/10/2014.
 */
public class EarthQuakeRequest extends JsonRequest <List<EarthQuake>>{

    public static final String COUNT = "count";
    public static final String EARTHQUAKES = "earthquakes";


    public EarthQuakeRequest(int method, String url, String requestBody,
                             Response.Listener<List<EarthQuake>> listener,
                             ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<List<EarthQuake>> parseNetworkResponse(NetworkResponse networkResponse) {
        List<EarthQuake> arrayEarthQuake = null;
        EarthQuakeParser parser = new EarthQuakeParser();
        String jsonString = null;
        //TODO unit test the number of jsonobject

        JSONObject jsonResponse = null;
        JSONArray jsonArrayEarthQuakes = null;
        try {
            jsonString = new String (networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            jsonResponse = new JSONObject(jsonString);
            jsonArrayEarthQuakes = jsonResponse.getJSONArray(EARTHQUAKES);
            int nbEarthQuakes = jsonResponse.optInt(COUNT, 0);
            arrayEarthQuake = parser.parseArray(jsonArrayEarthQuakes);
        } catch (JSONException e) {
            e.printStackTrace();
            return error(new ParseError(e));
        } catch (Exception e) {
            e.printStackTrace();
            return error(new ParseError(e));
        }

        return success(arrayEarthQuake,
                HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

}
