package com.laurietrichet.earthquake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.laurietrichet.earthquake.R;
import com.laurietrichet.earthquake.fragments.EarthQuakeMapFragment;
import com.laurietrichet.earthquake.fragments.ItemFragment;
import com.laurietrichet.earthquake.model.EarthQuake;


public class HomeActivity extends ActionBarActivity
            implements ItemFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            ItemFragment fragment =
                    (ItemFragment) getSupportFragmentManager().findFragmentById(R.id.listFragment);
            if (fragment==null || ! fragment.isInLayout()) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new ItemFragment())
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)){
            case ConnectionResult.SUCCESS:
                break;
            default:
                break;
        }
    }

    @Override
    public void onFragmentInteraction(EarthQuake earthQuake) {
        EarthQuakeMapFragment fragment =
                (EarthQuakeMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        if (fragment==null || ! fragment.isInLayout()) {
            Intent intent = new Intent(getApplicationContext(), EarthQuakeMapActivity.class);
            intent.putExtra(EarthQuakeMapActivity.EARTH_QUAKE, earthQuake);
            startActivity(intent);
        } else {
            fragment.setEarthQuakeMarker(earthQuake);
        }
    }
}
