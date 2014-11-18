package com.laurietrichet.earthquake.net.parsers;

import com.laurietrichet.earthquake.model.EarthQuake;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Parser for EarthQuake object using {@link org.json.JSONObject}
 */
public class EarthQuakeParser {

    enum EQFields {
        src,
        eqid,
        timedate,
        lat,
        lon,
        magnitude,
        depth,
        region,
        earthquakes
    }

    private EarthQuakeParser () {}

    /**
     * get a json string and return a list of EarthQuake objects.
     * according to webservice input format should be {"earthquakes":[{...}]}
     * @param jsonString String to be parsed
     * @return list of EarthQuake objects.
     * @throws Exception when a json object cannot be parsed
     */
    public static List <EarthQuake> parse (String jsonString) throws Exception{
        JSONObject jsonResponse = new JSONObject(jsonString);
        JSONArray jsonArrayEarthQuakes = jsonResponse.getJSONArray(EQFields.earthquakes.name());
        return parseArray(jsonArrayEarthQuakes);
    }

    private static EarthQuake parseObject (JSONObject obj) throws Exception{
        EarthQuake.Builder builder = new EarthQuake.Builder();
        builder.src(obj.optString(EQFields.src.name(), null))
        .eqid (obj.optString(EQFields.eqid.name(), null))
        .timedate( EarthQuake.getDateFormat().parse( obj.optString(EQFields.timedate.name(), null)))
        .lat ( Float.parseFloat(obj.optString(EQFields.lat.name(), null)))
        .lon ( Float.parseFloat(obj.optString(EQFields.lon.name(), null)))
        .magnitude ( obj.optDouble(EQFields.magnitude.name(), 0.d))
        .depth ( obj.optDouble(EQFields.depth.name(), 0.d))
        .region ( obj.optString(EQFields.region.name(), null));

        return builder.build();
    }

    private static List<EarthQuake> parseArray(JSONArray array) throws Exception {
        List <EarthQuake> arrayEarthQuakes;
        TreeSet<EarthQuake> earthQuakeTreeSet = new TreeSet<EarthQuake>();
        JSONObject jsonEarthQuake;
        EarthQuake earthQuake;

        for (int jsonArrayIndex = 0 ; jsonArrayIndex < array.length();jsonArrayIndex++ ){
            jsonEarthQuake = array.getJSONObject(jsonArrayIndex);
            earthQuake = parseObject(jsonEarthQuake);
            earthQuakeTreeSet.add(earthQuake);
        }
        arrayEarthQuakes = new ArrayList<EarthQuake>(earthQuakeTreeSet);
        return arrayEarthQuakes;
    }
}
