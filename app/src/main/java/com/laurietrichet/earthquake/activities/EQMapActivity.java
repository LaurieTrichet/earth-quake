package com.laurietrichet.earthquake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.laurietrichet.earthquake.R;
import com.laurietrichet.earthquake.data.DataAccessors;
import com.laurietrichet.earthquake.data.IDataAccessor;
import com.laurietrichet.earthquake.fragments.EarthQuakeMapFragment;
import com.laurietrichet.earthquake.model.EarthQuake;

import java.util.List;

/**
 * Display the earth quakes on a map
 */
public class EQMapActivity extends ActionBarActivity{

    public static final String EARTH_QUAKE = "EARTH_QUAKE";
    private static final String EARTH_QUAKE_FRAGMENT = "EARTH_QUAKE_FRAGMENT";

    /**
     * to display while data is charging
     */
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        EarthQuakeMapFragment fragment = getMapFragment ();

        if ( getIntent().hasExtra(EARTH_QUAKE)){
            mProgressBar.setVisibility(View.GONE);
            EarthQuake earthQuake = getIntent().getExtras().getParcelable(EARTH_QUAKE);
            fragment.setEarthQuakeMarker(earthQuake);
        }
    }

    private void getData (){
        mProgressBar.setVisibility(View.VISIBLE);
        IDataAccessor  dataAccessor =
                DataAccessors.getAccessor(this, DataAccessors.EARTH_QUAKE_DATA_ACCESSOR_KEY);
        dataAccessor.getAll(mDataAccessorListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (getIntent().getExtras() == null) {
            getMenuInflater().inflate(R.menu.activity_map, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_view) {
            Intent intent = new Intent(getApplicationContext(), EQHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras() == null){
            getData ();
        } else{
            mProgressBar.setVisibility(View.GONE);
        }
    }

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (getIntent().getExtras() == null){
            outState.putParcelable(EARTH_QUAKE, getIntent().getExtras().getParcelable());
        }
        super.onSaveInstanceState(outState);
    }*/

    private final IDataAccessor.DataAccessorListener <List<EarthQuake>> mDataAccessorListener =
            new IDataAccessor.DataAccessorListener<List<EarthQuake>>(){

                @Override
                public void onSuccess(List<EarthQuake> earthQuakeList) {
                    mProgressBar.setVisibility(View.GONE);
                    updateMarkers(earthQuakeList);
                }

                @Override
                public void onError(Error error) {
                    mProgressBar.setVisibility(View.GONE);
                    String errorMessage = getString(R.string.item_fragment_data_loading_error);
                    Toast.makeText(EQMapActivity.this,errorMessage,Toast.LENGTH_SHORT).show();
                }
            };

    private void updateMarkers (List<EarthQuake> earthQuakeList){
        EarthQuakeMapFragment fragment = getMapFragment ();

        if (fragment != null){
            fragment.clearMarkers();
            fragment.setEarthQuakeMarkers(earthQuakeList);
        }
    }

    private EarthQuakeMapFragment getMapFragment (){
        return (EarthQuakeMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
    }
}
