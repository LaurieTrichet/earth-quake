package com.laurietrichet.earthquake.adapters;

import android.content.Context;
import android.os.Looper;
import android.support.v7.appcompat.BuildConfig;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.laurietrichet.earthquake.R;
import com.laurietrichet.earthquake.model.EarthQuake;
import com.laurietrichet.earthquake.net.VolleyHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laurie on 30/10/2014.
 */
public class EarthQuakeListAdapter extends BaseAdapter{

    private final Context mContext;
    private List<EarthQuake> mEarthQuakeList;

    private static class Holder{
        private TextView date;
        private TextView region;
        private TextView magnitude;
        private TextView depth;
    }

    public EarthQuakeListAdapter (Context context){
        mContext = context;
        mEarthQuakeList = new ArrayList<EarthQuake>();
    }

    @Override
    public int getCount() {
        return mEarthQuakeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mEarthQuakeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        EarthQuake earthQuake = (EarthQuake) getItem(position);

        if (convertView == null){
            try{
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.earthquake_item, parent, false);
            } catch (InflateException e){
                e.printStackTrace();
            }

            holder = new Holder();

            holder.date = (TextView)convertView.findViewById(R.id.textViewTimeDate);
            holder.magnitude = (TextView)convertView.findViewById(R.id.textViewMagnitude);
            holder.depth = (TextView)convertView.findViewById(R.id.textViewDepth);
            holder.region = (TextView)convertView.findViewById(R.id.textViewRegion);
        } else {
            holder = (Holder)convertView.getTag();
        }

        holder.date.setText(VolleyHelper.getDateFormat().format(earthQuake.getTimedate()));
        holder.magnitude.setText("Magnitude : "+earthQuake.getMagnitude());
        holder.depth.setText("Depth : "+earthQuake.getDepth());
        holder.region.setText(earthQuake.getRegion());

        convertView.setTag(holder);
        return convertView;
    }

    public void updateEarthQuakes(List<EarthQuake> earthQuakeList){
        //check multithreading safety
        if (BuildConfig.DEBUG) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method should be called from the Main Thread");
            }
        }

        this.mEarthQuakeList = earthQuakeList;
        notifyDataSetChanged();
    }
}
