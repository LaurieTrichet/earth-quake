package com.laurietrichet.earthquake.fragments;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.laurietrichet.earthquake.model.EarthQuake;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link SupportMapFragment} subclass.
 * Activities that contain this fragment must implement the
 *
 */
public class EarthQuakeMapFragment extends SupportMapFragment {

    public static final String EARTH_QUAKE = "EARTH_QUAKE";
    private static final String EARTH_QUAKE_LIST = "EARTH_QUAKE_LIST";

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null){
            readArguments ();
        }
    }

    private void readArguments (){
        if (getArguments().get(EARTH_QUAKE) != null) {
            setEarthQuakeMarker((EarthQuake) getArguments().getParcelable(EARTH_QUAKE));
        } else if (getArguments().get(EARTH_QUAKE_LIST) != null){
            ArrayList<EarthQuake> earthQuakeArrayList =
                    getArguments().getParcelableArrayList(EARTH_QUAKE_LIST);
            setEarthQuakeMarkers(earthQuakeArrayList);
        }
    }

    /**
     * Clear the map and set a earth quake marker on the map
     * @param earthQuake // the EarthQuake object to position on the map
     */
    public void setEarthQuakeMarker(EarthQuake earthQuake) {
        LatLng latLng = new LatLng(earthQuake.getLat(), earthQuake.getLon());
        getMap().stopAnimation();
        getMap().addMarker(createMarker(earthQuake));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
        //getMap().moveCamera(cameraUpdate);
        getMap().animateCamera(cameraUpdate);
    }

    /**
     * Set all the markers on the map from the list of earth quakes given
     * @param earthQuakeList A list providing the data to place on the map
     */
    public void setEarthQuakeMarkers (List <EarthQuake> earthQuakeList){
        for (EarthQuake earthQuake : earthQuakeList){
            getMap().addMarker(createMarker (earthQuake));
        }
    }

    private static MarkerOptions createMarker (EarthQuake earthQuake){
        return new MarkerOptions()
                .position(new LatLng(earthQuake.getLat(), earthQuake.getLon()))
                .title(earthQuake.getRegion())
                .snippet("Magnitude = "+earthQuake.getMagnitude()+
                "; Depth = "+earthQuake.getDepth());
    }

    /**
     * Clear markers from the map
     */
    public void clearMarkers (){
        getMap().clear();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return AnimationUtils.loadAnimation(getActivity(),
                enter?android.R.anim.slide_in_left:android.R.anim.slide_out_right);
    }
}
