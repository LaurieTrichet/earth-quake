package com.laurietrichet.earthquake.net.client;

import android.test.InstrumentationTestCase;

import com.laurietrichet.earthquake.model.EarthQuake;

import junit.framework.Assert;

import java.util.List;

public class EQWebServiceClientTest extends InstrumentationTestCase {


    private AbstractWebServiceClient.WebServiceClientListener <List<EarthQuake>> mListener;

    private AbstractWebServiceClient<List<EarthQuake>> mWebServiceClient;

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testGet() throws Exception {
        mListener = new AbstractWebServiceClient.WebServiceClientListener<List<EarthQuake>>() {
            @Override
            public void onSuccess(List<EarthQuake> obj) {
                Assert.assertNotNull(obj);
            }

            @Override
            public void onError(Error error) {
                Assert.assertNull(error);
            }
        };
        mWebServiceClient = new EQWebServiceClient(getInstrumentation().getContext());
        Assert.assertEquals(0,
                mWebServiceClient.get(EQWebServiceClient.ENTRY_POINT_LIST_EQ,null,mListener));
    }
}