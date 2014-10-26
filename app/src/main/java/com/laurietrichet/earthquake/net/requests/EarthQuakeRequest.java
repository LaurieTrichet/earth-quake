package com.laurietrichet.earthquake.net.requests;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;
import com.laurietrichet.earthquake.model.EarthQuake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by laurie on 26/10/2014.
 */
public class EarthQuakeRequest extends JsonRequest <List<EarthQuake>>{

    public static final String COUNT = "count";
    public static final String EARTHQUAKES = "earthquakes";


    public EarthQuakeRequest(int method, String url, String requestBody,
                             Response.Listener<List<EarthQuake>> listener,
                             Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<List<EarthQuake>> parseNetworkResponse(NetworkResponse networkResponse) {

        String jsonString = new String (networkResponse.data);
        //TODO unit test the number of jsonobject
        JSONObject jsonResponse = null;
        try {
            jsonResponse = new JSONObject(jsonString);
            JSONArray jsonArrayEarthQuakes = jsonResponse.getJSONArray(EARTHQUAKES);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int nbEarthQuakes = jsonResponse.optInt(COUNT, 0);





        return null;
    }

}
