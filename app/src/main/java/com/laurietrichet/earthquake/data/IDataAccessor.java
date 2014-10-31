package com.laurietrichet.earthquake.data;

/**
 * Created by laurie on 30/10/2014.
 * Describes the object to access model object
 */
public interface IDataAccessor {

    /**
     * Data will be retrieved by this listener
     * @param <T>
     */
    public interface DataAccessorListener <T>{

        /**
         * give the obj result to process
         * @param obj
         */
        public void onSuccess(T obj);


        /**
         * get the error that has occur during data retrieving
         * @param error
         */
        public void onError (Error error);
    }

    /**
     * implement here code to retrieve all available objects
     */
    public void getAll (DataAccessorListener listener);


}
