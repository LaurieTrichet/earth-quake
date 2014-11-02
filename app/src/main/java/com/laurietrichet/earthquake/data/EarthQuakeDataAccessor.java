package com.laurietrichet.earthquake.data;

import android.content.Context;

import com.laurietrichet.earthquake.model.EarthQuake;
import com.laurietrichet.earthquake.net.client.AbstractWebServiceClient;
import com.laurietrichet.earthquake.net.client.EQWebServiceClient;

import java.util.List;

import static com.laurietrichet.earthquake.net.client.AbstractWebServiceClient.WebServiceClientListener;

/**
 * Implementation of {@link com.laurietrichet.earthquake.data.IDataAccessor} for EarthQuake object
 */
public class EarthQuakeDataAccessor implements IDataAccessor {

    private AbstractWebServiceClient mClient ;

    /**package*/ EarthQuakeDataAccessor (Context context) {
        mClient = new EQWebServiceClient(context.getApplicationContext());
    }

    @Override
    public void getAll (final DataAccessorListener dataAccessorListener){
        WebServiceClientListener <List<EarthQuake>> webServiceClientListener =
                new WebServiceClientListener<List<EarthQuake>>() {
                    @Override
                    public void onSuccess(List<EarthQuake> obj) {
                        dataAccessorListener.onSuccess(obj);
                    }

                    @Override
                    public void onError(Error error) {
                        dataAccessorListener.onError(error);
                    }
                };
        mClient.get(EQWebServiceClient.ENTRY_POINT_LIST_EQ, null, webServiceClientListener);
    }
}
