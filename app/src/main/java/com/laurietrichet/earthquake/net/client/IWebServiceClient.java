package com.laurietrichet.earthquake.net.client;

/**
 * Describe webservice client
 *
 */
public interface IWebServiceClient<T>{

    /**
     * called when the web service has processed a response
     * @param <T>
     */
    public interface WebServiceClientListener <T>{

        /**
         * called when the request has succeed
         * @param obj result object to process
         */
        public void onSuccess(T obj);

        /**
         * called when an error occurred during process
         * @param error error to process
         */
        public void onError (Error error);
    }

    /**
     * Implement here the call to webservice
     */
    public void get (WebServiceClientListener<T> listener);
}
