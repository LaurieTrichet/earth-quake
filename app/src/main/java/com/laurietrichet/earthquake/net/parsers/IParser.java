package com.laurietrichet.earthquake.net.parsers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by laurie on 26/10/2014.
 */
public interface IParser <T>{

    /**
     * Parse one object from JSON object representing T object
     * @param obj
     * @return T class
     */
    T parseObject (JSONObject obj) throws Exception;

    /**
     * Parse an array of JSON objects representing T object
     * @param array
     * @return List of T class object
     */
    List<T> parseArray (JSONArray array) throws Exception;

}
