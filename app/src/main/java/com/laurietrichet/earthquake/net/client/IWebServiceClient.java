package com.laurietrichet.earthquake.net.client;

/**
 * Describe webservice client
 *
 */
public interface IWebServiceClient<T>{

    /**
     * listen to result from webservice
     * @param <T>
     */
    public interface WebServiceClientListener <T>{

        /**
         * result to process
         * @param obj
         */
        public void onSuccess(T obj);

        /**
         * error occurred during process
         * @param error
         */
        public void onError (Error error);
    }

    /**
     * Implement here the call to webservice
     */
    public void get (WebServiceClientListener<T> listener);
}
