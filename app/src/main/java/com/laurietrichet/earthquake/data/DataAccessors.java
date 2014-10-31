package com.laurietrichet.earthquake.data;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by laurie on 30/10/2014.
 */
public enum DataAccessors {

    INSTANCE;

    public static final String EARTH_QUAKE_DATA_ACCESSOR_KEY = "EARTH_QUAKE_DATA_ACCESSOR";

    private static final Map<String,IDataAccessor> accessorList =
            new HashMap<String, IDataAccessor>();

    public static IDataAccessor getAccessor (Context context, String key){
        if (! accessorList.containsKey(key)){
            if (key.equals(EARTH_QUAKE_DATA_ACCESSOR_KEY)){
                accessorList.put(EARTH_QUAKE_DATA_ACCESSOR_KEY, new EarthQuakeDataAccessor(context));
            }
        }
        return accessorList.get(key);
    }
}
