package com.laurietrichet.earthquake.net.parsers;

import com.laurietrichet.earthquake.application.AppController;
import com.laurietrichet.earthquake.model.EarthQuake;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laurie on 26/10/2014.
 */
public class EarthQuakeParser implements IParser <EarthQuake> {

    private EarthQuake mEarthQuake;
    List <EarthQuake> mEarthQuakes;

    public EarthQuakeParser (){
        mEarthQuake = new EarthQuake();
        mEarthQuakes = new ArrayList<EarthQuake>();
    }

    @Override
    public EarthQuake parseObject (JSONObject obj) throws Exception{

        mEarthQuake.src = obj.optString("src", null);
        mEarthQuake.eqid = obj.optString("eqid", null);
        mEarthQuake.timedate = AppController.getDateFormat().parse( obj.optString("timedate", null));
        mEarthQuake.lat = Float.parseFloat(obj.optString("lat", null));
        mEarthQuake.lon = Float.parseFloat(obj.optString("lon", null));
        mEarthQuake.magnitude = obj.optDouble("magnitude", 0.d);
        mEarthQuake.depth = obj.optDouble("depth", 0.d);
        mEarthQuake.region = obj.optString("region", null);

        return mEarthQuake;
    }

    @Override
    public List<EarthQuake> parseArray(JSONArray array) throws Exception {
        JSONObject jsonEarthQuake = null;
        mEarthQuakes.clear();
        for (int jsonArrayIndex = 0 ; jsonArrayIndex < array.length();
             jsonArrayIndex++ ){
            jsonEarthQuake = array.getJSONObject(jsonArrayIndex);

            mEarthQuakes.add(this.parseObject(jsonEarthQuake));
        }

        return mEarthQuakes;
    }
}
