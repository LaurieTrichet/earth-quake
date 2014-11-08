package com.laurietrichet.earthquake.net.requests;

import android.content.res.AssetManager;
import android.test.InstrumentationTestCase;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.laurietrichet.earthquake.model.EarthQuake;
import com.laurietrichet.earthquake.net.client.AbstractWebServiceClient;
import com.laurietrichet.earthquake.FileUtility;

import junit.framework.Assert;

import java.io.InputStream;
import java.util.List;

public class ListEarthQuakeRequestTest extends InstrumentationTestCase {

    private String mGoodJsonString;
    private String mBadJsonString;

    private NetworkResponse mGoodNetworkResponse;
    private NetworkResponse mBadNetworkResponse;

    private ListEarthQuakeRequest mRequest;

    public void setUp() throws Exception {
        super.setUp();
        AssetManager assetMgr = this.getInstrumentation().getContext().getAssets();
        InputStream stream = assetMgr.open("eqs.json");
        mGoodJsonString = FileUtility.file2String(stream);

        stream = assetMgr.open("eqs_bad.json");
        mBadJsonString = FileUtility.file2String(stream);

        mGoodNetworkResponse = new NetworkResponse(mGoodJsonString.getBytes());
        mBadNetworkResponse = new NetworkResponse(mBadJsonString.getBytes());
    }

    public void tearDown() throws Exception {
        mGoodJsonString = null;
        mBadJsonString = null;
        mGoodNetworkResponse = null;
        mBadNetworkResponse = null;
    }

    public void testParseNetworkResponse() throws Exception {
        AbstractWebServiceClient.WebServiceClientListener <List<EarthQuake>> mGoodListener =
                new AbstractWebServiceClient.WebServiceClientListener<List<EarthQuake>>() {
                    @Override
                    public void onSuccess(List<EarthQuake> earthQuakeList) {
                        Assert.assertNotNull(earthQuakeList);
                        Assert.assertFalse(earthQuakeList.isEmpty());
                    }

                    @Override
                    public void onError(Error error) {
                        Assert.assertNull(error);
                    }
                };

        mRequest = new ListEarthQuakeRequest(mGoodListener);
        Response response = mRequest.parseNetworkResponse(mGoodNetworkResponse);
        Assert.assertTrue(response.isSuccess());


        AbstractWebServiceClient.WebServiceClientListener <List<EarthQuake>> mBadListener =
                new AbstractWebServiceClient.WebServiceClientListener<List<EarthQuake>>() {
            @Override
            public void onSuccess(List<EarthQuake> obj) {
                Assert.assertNull(obj);
            }

            @Override
            public void onError(Error error) {
                Assert.assertNotNull(error);
            }
        };

        mRequest = new ListEarthQuakeRequest(mBadListener);
        response = mRequest.parseNetworkResponse(mBadNetworkResponse);
        Assert.assertFalse(response.isSuccess());
    }
}