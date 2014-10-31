package com.laurietrichet.earthquake.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.laurietrichet.earthquake.R;
import com.laurietrichet.earthquake.adapters.EarthQuakeListAdapter;
import com.laurietrichet.earthquake.data.DataAccessors;
import com.laurietrichet.earthquake.data.IDataAccessor;
import com.laurietrichet.earthquake.model.EarthQuake;

import java.util.List;

import static com.laurietrichet.earthquake.data.DataAccessors.EARTH_QUAKE_DATA_ACCESSOR_KEY;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the
 * {@link com.laurietrichet.earthquake.fragments.ItemFragment.OnFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    /**
     * list of items
     */

    private OnFragmentInteractionListener mListener;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private EarthQuakeListAdapter mAdapter;

    /**
     * to display while data is charging
     */
    private ProgressBar mProgressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    private enum Status {UNLOADED,LOADED};

    private Status mLoadingStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingStatus = Status.UNLOADED;
        mAdapter = new EarthQuakeListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        // Set the adapter
        /*
      The fragment's ListView/GridView.
     */
        AbsListView mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(((EarthQuake)mAdapter.getItem(position)));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mLoadingStatus == Status.UNLOADED){
            getData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
        public void onFragmentInteraction(EarthQuake earthQuake);
    }

    private IDataAccessor.DataAccessorListener <List<EarthQuake>> mDataAccessorListener =
            new IDataAccessor.DataAccessorListener<List<EarthQuake>>(){

                @Override
                public void onSuccess(List<EarthQuake> earthQuakeList) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    mAdapter.updateEarthQuakes(earthQuakeList);
                    mLoadingStatus = Status.LOADED;
                }

                @Override
                public void onError(Error error) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    String errorMessage = (error.getLocalizedMessage() == null)?
                            getString(R.string.item_fragment_data_loading_error):
                            error.getLocalizedMessage();
                    showShortToast(errorMessage);
                }
            };

    private void getData (){
        mProgressBar.setVisibility(View.VISIBLE);
        IDataAccessor dataAccessor = DataAccessors.getAccessor(getActivity(),
                EARTH_QUAKE_DATA_ACCESSOR_KEY);
        dataAccessor.getAll(mDataAccessorListener);
    }

    private void showShortToast (String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
