package com.laurietrichet.earthquake.net.parsers;

import com.laurietrichet.earthquake.model.EarthQuake;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Parser for EarthQuake object using {@link org.json.JSONObject}
 */
public class EarthQuakeParser {

    public static final String SRC = "src";
    public static final String EQID = "eqid";
    public static final String TIMEDATE = "timedate";
    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String MAGNITUDE = "magnitude";
    public static final String DEPTH = "depth";
    public static final String REGION = "region";

    public static final String COUNT = "count";
    public static final String EARTHQUAKES = "earthquakes";

    private EarthQuakeParser () {};

    /**
     * get a json string and return a list of EarthQuake objects.
     * according to webservice input format should be {"earthquakes":[{...}]}
     * @param jsonString String to be parsed
     * @return list of EarthQuake objects.
     * @throws Exception when a json object cannot be parsed
     */
    public static List <EarthQuake> parse (String jsonString) throws Exception{
        JSONObject jsonResponse = new JSONObject(jsonString);
        JSONArray jsonArrayEarthQuakes = jsonResponse.getJSONArray(EARTHQUAKES);
        List <EarthQuake> arrayEarthQuakes = parseArray(jsonArrayEarthQuakes);
        return arrayEarthQuakes;
    }

    private static EarthQuake parseObject (JSONObject obj) throws Exception{
        EarthQuake.Builder builder = new EarthQuake.Builder();
        builder.src(obj.optString(SRC, null))
        .eqid (obj.optString(EQID, null))
        .timedate( EarthQuake.getDateFormat().parse( obj.optString(TIMEDATE, null)))
        .lat ( Float.parseFloat(obj.optString(LAT, null)))
        .lon ( Float.parseFloat(obj.optString(LON, null)))
        .magnitude ( obj.optDouble(MAGNITUDE, 0.d))
        .depth ( obj.optDouble(DEPTH, 0.d))
        .region ( obj.optString(REGION, null));

        return builder.build();
    }

    private static List<EarthQuake> parseArray(JSONArray array) throws Exception {
        List <EarthQuake> arrayEarthQuakes = new ArrayList<EarthQuake>();
        JSONObject jsonEarthQuake;
        EarthQuake earthQuake;
        arrayEarthQuakes.clear();
        int indexForInsertion = 0;
        for (int jsonArrayIndex = 0 ; jsonArrayIndex < array.length();
             jsonArrayIndex++ ){
            jsonEarthQuake = array.getJSONObject(jsonArrayIndex);
            earthQuake = parseObject(jsonEarthQuake);
            //control for multiple occurrences
            indexForInsertion = Collections.binarySearch(
                    arrayEarthQuakes,
                    earthQuake,
                    EarthQuake.getEqidComparator());
            if ( 0 > indexForInsertion){
                arrayEarthQuakes.add(-indexForInsertion-1, earthQuake);
            }
        }
        return arrayEarthQuakes;
    }
}
