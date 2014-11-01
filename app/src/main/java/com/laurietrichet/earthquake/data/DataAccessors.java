package com.laurietrichet.earthquake.data;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Controls the data accessors's creation.
 * See {@link com.laurietrichet.earthquake.data.IDataAccessor} to know how access to data.
 */
public class DataAccessors {

    public static final String EARTH_QUAKE_DATA_ACCESSOR_KEY = "EARTH_QUAKE_DATA_ACCESSOR";

    private static final Map<String,IDataAccessor> mAccessorList =
            new HashMap<String, IDataAccessor>();

    private DataAccessors (){}

    /**
     * Return a < ? implements {@link IDataAccessor}> for the Key given in parameter
     * @param context context to pass to the < ? implements {@link IDataAccessor}> for creation
     * @param key the key to get the < ? implements {@link IDataAccessor}>
     * @return < ? implements {@link IDataAccessor}> that access data
     */
    public static IDataAccessor getAccessor (Context context, String key){
        if (! mAccessorList.containsKey(key)){
            if (key.equals(EARTH_QUAKE_DATA_ACCESSOR_KEY)){
                mAccessorList.put(EARTH_QUAKE_DATA_ACCESSOR_KEY, new EarthQuakeDataAccessor(context));
            }
        }
        return mAccessorList.get(key);
    }
}
