package com.laurietrichet.earthquake.net.requests;

import android.content.res.AssetManager;
import android.test.InstrumentationTestCase;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.laurietrichet.earthquake.model.EarthQuake;
import com.laurietrichet.earthquake.test.FileUtility;

import junit.framework.Assert;

import java.io.InputStream;
import java.util.List;

public class ListEarthQuakeRequestTest extends InstrumentationTestCase {

    private String mGoodJsonString;
    private String mBadJsonString;

    private NetworkResponse mGoodNetworkResponse;
    private NetworkResponse mBadNetworkResponse;

    private Response.Listener <List<EarthQuake>> mListener;
    private Response.ErrorListener mErrorListener;

    private Response.Listener <List<EarthQuake>> mBadListener;
    private Response.ErrorListener mBadErrorListener;

    private ListEarthQuakeRequest mRequest;

    public void setUp() throws Exception {
        super.setUp();
        AssetManager assetMgr = this.getInstrumentation().getContext().getAssets();
        InputStream stream = assetMgr.open("eqs.json");
        mGoodJsonString = FileUtility.file2String(stream);

        stream = assetMgr.open("eqs_bad.json");
        mGoodJsonString = FileUtility.file2String(stream);

        mGoodNetworkResponse = new NetworkResponse(mGoodJsonString.getBytes());
        mBadNetworkResponse = new NetworkResponse(mBadJsonString.getBytes());
    }

    public void tearDown() throws Exception {

    }

    public void testParseNetworkResponse() throws Exception {
        mListener = new Response.Listener<List<EarthQuake>>() {
            @Override
            public void onResponse(List<EarthQuake> earthQuakeList) {
                Assert.assertNotNull(earthQuakeList);
                Assert.assertFalse(earthQuakeList.isEmpty());
            }
        };

        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Assert.assertNull(volleyError);
            }
        };
        mRequest = new ListEarthQuakeRequest(mListener, mErrorListener);
        Response response = mRequest.parseNetworkResponse(mGoodNetworkResponse);
        Assert.assertTrue(response.isSuccess());



        mBadListener = new Response.Listener<List<EarthQuake>>() {
            @Override
            public void onResponse(List<EarthQuake> earthQuakeList) {
                Assert.assertNull(earthQuakeList);
            }
        };

        mBadErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Assert.assertNotNull(volleyError);
            }
        };

        mRequest = new ListEarthQuakeRequest(mBadListener, mBadErrorListener);
        response = mRequest.parseNetworkResponse(mBadNetworkResponse);
        Assert.assertFalse(response.isSuccess());
    }
}