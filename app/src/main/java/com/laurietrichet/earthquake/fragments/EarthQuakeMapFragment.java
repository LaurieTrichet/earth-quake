package com.laurietrichet.earthquake.fragments;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.laurietrichet.earthquake.model.EarthQuake;

/**
 * A simple {@link SupportMapFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EarthQuakeMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EarthQuakeMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class EarthQuakeMapFragment extends SupportMapFragment {

    public static final String EARTH_QUAKE = "EARTH_QUAKE";

    private OnFragmentInteractionListener mListener;

    private GoogleMap mMap;
    /**
     * return a new instance of EarthQuakeMapFragment and put earthQuake in argument bundle
     * @param earthQuake
     * @return EarthQuakeMapFragment
     */
    public static EarthQuakeMapFragment newInstance (EarthQuake earthQuake){
        EarthQuakeMapFragment fragment = new EarthQuakeMapFragment();
        Bundle args = new Bundle();
        args.putParcelable(EARTH_QUAKE, earthQuake);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            if (getArguments() != null){
                setUpMapIfNeeded((EarthQuake) getArguments().getParcelable(EARTH_QUAKE));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null){
            setUpMapIfNeeded((EarthQuake) getArguments().getParcelable(EARTH_QUAKE));
        }
    }

    /**
     * Clear the map and set a earth quake marker on the map
     * @param earthQuake
     */
    public void setEarthQuakeMarker(EarthQuake earthQuake) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(earthQuake.getLat(), earthQuake.getLon()))
                .title(earthQuake.getRegion()));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void setUpMapIfNeeded(EarthQuake earthQuake) {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            mMap = getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                // The Map is verified. It is now safe to manipulate the map.
                setEarthQuakeMarker(earthQuake);
            }
        }
    }

}
