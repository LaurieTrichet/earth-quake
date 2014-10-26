package com.laurietrichet.earthquake.net.parsers;

import com.laurietrichet.earthquake.model.EarthQuake;

import org.json.JSONObject;

/**
 * Created by laurie on 26/10/2014.
 */
public class EarthQuakeParser implements IParser <EarthQuake> {

    public EarthQuake parse (JSONObject obj){
        //TODO implement
        return new EarthQuake();
    }
}
