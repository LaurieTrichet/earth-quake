package com.laurietrichet.earthquake.data;

import android.content.Context;

import com.laurietrichet.earthquake.model.EarthQuake;
import com.laurietrichet.earthquake.net.client.EQWebServiceClient;
import com.laurietrichet.earthquake.net.client.IWebServiceClient;

import java.util.List;

import static com.laurietrichet.earthquake.net.client.IWebServiceClient.WebServiceClientListener;

/**
 * Created by laurie on 29/10/2014.
 */
public class EarthQuakeDataAccessor implements IDataAccessor {

    private Context mContext;

    private DataAccessorListener <List<EarthQuake>> mDataAccessorListener;

    private IWebServiceClient mClient ;

    private WebServiceClientListener <List<EarthQuake>> mListener =
            new WebServiceClientListener<List<EarthQuake>>() {
        @Override
        public void onSuccess(List<EarthQuake> obj) {
            mDataAccessorListener.onSuccess(obj);
        }

        @Override
        public void onError(Error error) {
            mDataAccessorListener.onError(error);
        }
    };

    /**package*/ EarthQuakeDataAccessor (Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void getAll (DataAccessorListener listener){
        mClient = new EQWebServiceClient(mContext);
        mDataAccessorListener = listener;
        mClient.get(mListener);
    }
}
