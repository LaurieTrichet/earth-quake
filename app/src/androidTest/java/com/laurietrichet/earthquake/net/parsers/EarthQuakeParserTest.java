package com.laurietrichet.earthquake.net.parsers;

import android.content.res.AssetManager;
import android.test.InstrumentationTestCase;

import com.laurietrichet.earthquake.FileUtility;
import com.laurietrichet.earthquake.model.EarthQuake;

import junit.framework.Assert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.laurietrichet.earthquake.net.parsers.EarthQuakeParser.DEPTH;
import static com.laurietrichet.earthquake.net.parsers.EarthQuakeParser.EARTHQUAKES;
import static com.laurietrichet.earthquake.net.parsers.EarthQuakeParser.EQID;
import static com.laurietrichet.earthquake.net.parsers.EarthQuakeParser.LAT;
import static com.laurietrichet.earthquake.net.parsers.EarthQuakeParser.LON;
import static com.laurietrichet.earthquake.net.parsers.EarthQuakeParser.MAGNITUDE;
import static com.laurietrichet.earthquake.net.parsers.EarthQuakeParser.REGION;
import static com.laurietrichet.earthquake.net.parsers.EarthQuakeParser.SRC;
import static com.laurietrichet.earthquake.net.parsers.EarthQuakeParser.TIMEDATE;

public class EarthQuakeParserTest extends InstrumentationTestCase {

    private String json;

    public void setUp() throws Exception {
        super.setUp();
        AssetManager assetMgr = this.getInstrumentation().getContext().getAssets();
        InputStream stream = assetMgr.open("eqs.json");
        json = FileUtility.file2String(stream);
    }

    public void tearDown() throws Exception {
        json = null;
    }

    public void testParse() throws Exception {
        Assert.assertTrue(json != null);
        JSONObject jsonResponse = new JSONObject (json);
        List <EarthQuake> arrayEarthQuakes = EarthQuakeParser.parse(json);
        Assert.assertTrue(arrayEarthQuakes.size() == 18);
        int index = 0;
        JSONArray jsonArray = jsonResponse.getJSONArray(EARTHQUAKES);
        JSONObject jsonItem = jsonArray.getJSONObject(index);
//{"src":"us","eqid":"c000is61","timedate":"2013-07-29 22:22:48","lat":"7.6413","lon":"93.6871",
// "magnitude":"4.6","depth":"40.90","region":"Nicobar Islands, India region"}
        EarthQuake item = new EarthQuake.Builder ().eqid(jsonItem.getString(EQID)).build();

        int pos = Collections.binarySearch(arrayEarthQuakes,item,EarthQuake.getEqidComparator());
        Assert.assertTrue(pos >= 0);

        item = arrayEarthQuakes.get(pos);

        Assert.assertTrue(item.getSrc().equals(jsonItem.getString(SRC)));
        Assert.assertTrue(item.getEqid().equals(jsonItem.getString(EQID)));
        Date dateFromJson = EarthQuake.getDateFormat().parse(jsonItem.optString(TIMEDATE, null));
        Assert.assertTrue(item.getTimedate().equals(dateFromJson));
        Assert.assertTrue(item.getRegion().equals(jsonItem.getString(REGION)));
        Assert.assertTrue(item.getLat().equals(Float.parseFloat(jsonItem.optString(LAT, null))));
        Assert.assertTrue(item.getLon().equals(Float.parseFloat(jsonItem.optString(LON, null))));
        Assert.assertTrue(item.getDepth() == jsonItem.getDouble(DEPTH));
        Assert.assertTrue(item.getMagnitude() == jsonItem.getDouble(MAGNITUDE));
    }
}