package com.laurietrichet.earthquake.data;

/**
 * Created by laurie on 30/10/2014.
 * Describes the accessor to access model object
 */
public interface IDataAccessor {

    /**
     * Defines signature methods that are called when the data request is finished
     * @param <T>
     */
    public interface DataAccessorListener <T>{

        /**
         * give the obj result to process
         * @param obj The object that was requested
         */
        public void onSuccess(T obj);


        /**
         * get the error that has occur during data retrieving
         * @param error The error that has occurred during the request
         */
        public void onError (Error error);
    }

    /**
     * implement here code to retrieve all available objects
     */
    public void getAll (DataAccessorListener listener);


}
