package com.laurietrichet.earthquake.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.laurietrichet.earthquake.R;
import com.laurietrichet.earthquake.data.DataAccessors;
import com.laurietrichet.earthquake.data.IDataAccessor;
import com.laurietrichet.earthquake.fragments.EarthQuakeMapFragment;
import com.laurietrichet.earthquake.fragments.ItemFragment;
import com.laurietrichet.earthquake.model.EarthQuake;

import java.util.Collections;
import java.util.List;

import static com.laurietrichet.earthquake.data.DataAccessors.EARTH_QUAKE_DATA_ACCESSOR_KEY;


public class EQHomeActivity extends ActionBarActivity
        implements ItemFragment.OnFragmentInteractionListener {

    private enum SortingOrder {DATE_DESC, MAG_DESC}

    private final SortingOrder sortingOrder = SortingOrder.DATE_DESC;

    /**
     * to display while data is charging
     */
    private ProgressBar mProgressBar;
    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
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
        if (id == R.id.action_map) {
            if (! checkGooglePlayServicesAvailable ()){
                displayGooglePlayServicesUnavailableText();
            }
            Intent intent = new Intent(getApplicationContext(), EQMapActivity.class);
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
        getData();
    }

    @Override
    public void onFragmentInteraction(EarthQuake earthQuake) {

        EarthQuakeMapFragment fragment =
                (EarthQuakeMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map_fragment);

        /*if the google play services are not available the framework
        provide a view to the user to update the services*/
        if (checkGooglePlayServicesAvailable()) {
            //if fragment exists in the layout that means the app is running on a table

            if (fragment == null || !fragment.isInLayout()) {
                Intent intent = new Intent(getApplicationContext(), EQMapActivity.class);
                intent.putExtra(EQMapActivity.EARTH_QUAKE, earthQuake);
                startActivity(intent);
            } else {
                fragment.clearMarkers();
                fragment.setEarthQuakeMarker(earthQuake);
            }
        } else {
            displayGooglePlayServicesUnavailableText();
            if (fragment == null || !fragment.isInLayout()) {
                Intent intent = new Intent(getApplicationContext(), EQMapActivity.class);
                startActivity(intent);
            }
        }
    }

    private boolean checkGooglePlayServicesAvailable (){
        int connectionResult = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        return connectionResult == ConnectionResult.SUCCESS;
    }

    private void displayGooglePlayServicesUnavailableText(){
        Toast.makeText(this,
                getString(R.string.google_play_sevices_not_available), Toast.LENGTH_SHORT)
                .show();
    }

    private final IDataAccessor.DataAccessorListener <List<EarthQuake>> mDataAccessorListener =
            new IDataAccessor.DataAccessorListener<List<EarthQuake>>(){

                @Override
                public void onSuccess(List<EarthQuake> earthQuakeList) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    ItemFragment itemFragment = (ItemFragment)
                            getSupportFragmentManager().findFragmentById(R.id.list_fragment);
                    earthQuakeList= sort(sortingOrder, earthQuakeList);
                    if (itemFragment != null){
                        itemFragment.updateData(earthQuakeList);
                    }
                }

                @Override
                public void onError(Error error) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    String errorMessage = getString(R.string.item_fragment_data_loading_error);
                    Toast.makeText(EQHomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            };

    private static List<EarthQuake> sort(SortingOrder sortingOrder, List<EarthQuake> earthQuakeList) {

        if (sortingOrder == SortingOrder.DATE_DESC ){
            Collections.sort(earthQuakeList, EarthQuake.getDateComparator());
            Collections.reverse(earthQuakeList);
        } else {
            Collections.sort(earthQuakeList);
            Collections.reverse(earthQuakeList);
        }
        return earthQuakeList;
    }

    private void getData (){
        mProgressBar.setVisibility(View.VISIBLE);
        IDataAccessor dataAccessor = DataAccessors.getAccessor(this,
                EARTH_QUAKE_DATA_ACCESSOR_KEY);
        dataAccessor.getAll(mDataAccessorListener);
    }
}
