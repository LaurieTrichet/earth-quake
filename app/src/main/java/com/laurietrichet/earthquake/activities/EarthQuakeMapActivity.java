package com.laurietrichet.earthquake.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.laurietrichet.earthquake.R;
import com.laurietrichet.earthquake.fragments.EarthQuakeMapFragment;
import com.laurietrichet.earthquake.model.EarthQuake;

/**
 * Created by laurie on 31/10/2014.
 */
public class EarthQuakeMapActivity extends ActionBarActivity
        implements EarthQuakeMapFragment.OnFragmentInteractionListener{

    public static final String EARTH_QUAKE = "EARTH_QUAKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        if (savedInstanceState == null) {
            EarthQuake earthQuake = (EarthQuake) getIntent().getExtras().getParcelable(EARTH_QUAKE);
            EarthQuakeMapFragment fragment = EarthQuakeMapFragment.newInstance(earthQuake);

            getSupportFragmentManager().beginTransaction()
                     .add(R.id.container, fragment)
                     .commit();


        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
