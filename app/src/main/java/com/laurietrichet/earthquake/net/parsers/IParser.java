package com.laurietrichet.earthquake.net.parsers;

import org.json.JSONObject;

/**
 * Created by laurie on 26/10/2014.
 */
public interface IParser <T>{

    T parse (JSONObject obj);

}
