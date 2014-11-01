package com.laurietrichet.earthquake.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.laurietrichet.earthquake.net.VolleyHelper;

import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by laurie on 26/10/2014.
 */
public class EarthQuake implements Parcelable, Comparable <EarthQuake>{

    private String src;
    private String eqid ;
    private Date timedate ;
    private Float lat;
    private Float lon ;
    private Double magnitude ;
    private Double depth;
    private String region;

    private EarthQuake(){};

    private EarthQuake(Parcel in) {
        src = in.readString();
        eqid = in.readString();
        try {
            timedate = VolleyHelper.getDateFormat().parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lat = Float.parseFloat(in.readString());
        lon = Float.parseFloat(in.readString());
        magnitude = in.readDouble();
        depth = in.readDouble();
        region = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(src);
        dest.writeString(eqid);
        dest.writeString(VolleyHelper.getDateFormat().format(timedate));
        dest.writeString("" +lat);
        dest.writeString("" +lon);
        dest.writeDouble(magnitude);
        dest.writeDouble(depth);
        dest.writeString(region);
    }

    public static final Parcelable.Creator<EarthQuake> CREATOR
            = new Parcelable.Creator<EarthQuake>() {
        public EarthQuake createFromParcel(Parcel in) {
            return new EarthQuake(in);
        }

        public EarthQuake[] newArray(int size) {
            return new EarthQuake[size];
        }
    };

    private EarthQuake (Builder builder){
        src = builder.src;
        eqid = builder.eqid;
        timedate = builder.timedate;
        lat = builder.lat;
        lon = builder.lon;
        magnitude = builder.magnitude;
        depth = builder.depth;
        region = builder.region;
    }

    @Override
    public int compareTo(EarthQuake another) {
        return Double.compare(this.magnitude, another.magnitude);
    }

    private static Comparator <EarthQuake> mDateComparator = new Comparator<EarthQuake>() {
        @Override
        public int compare(EarthQuake lhs, EarthQuake rhs) {
            return (lhs.timedate.compareTo(rhs.timedate));
        }
    };

    private static Comparator <EarthQuake> mEqidComparator = new Comparator<EarthQuake>() {
        @Override
        public int compare(EarthQuake lhs, EarthQuake rhs) {
            return (lhs.eqid.compareTo(rhs.eqid));
        }
    };

    public static Comparator<EarthQuake> getEqidComparator() {
        return mEqidComparator;
    }

    /**
     * Return a comparator to sort a List <EarthQuake>
     * @return Comparator<EarthQuake>
     */
    public static Comparator<EarthQuake> getDateComparator() {
        return mDateComparator;
    }

    /**
     * Builder class used to create a EarthQuake object
     */
    public static class Builder {
        private String src;
        private String eqid ;
        private Date timedate ;
        private Float lat;
        private Float lon ;
        private Double magnitude ;
        private Double depth;
        private String region;

        public Builder (){}

        public Builder src(String src) {
            this.src = src;
            return this;
        }

        public Builder eqid(String eqid) {
            this.eqid = eqid;
            return this;
        }

        public Builder timedate(Date timedate) {
            this.timedate = timedate;
            return this;
        }

        public Builder lat(Float lat) {
            this.lat = lat;
            return this;
        }

        public Builder lon(Float lon) {
            this.lon = lon;
            return this;
        }

        public Builder magnitude(Double magnitude) {
            this.magnitude = magnitude;
            return this;
        }

        public Builder depth(Double depth) {
            this.depth = depth;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public EarthQuake build() {
            return new EarthQuake(this);
        }
    }

    public String getSrc() {
        return src;
    }

    public String getEqid() {
        return eqid;
    }

    public Date getTimedate() {
        return timedate;
    }

    public Float getLat() {
        return lat;
    }

    public Float getLon() {
        return lon;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public Double getDepth() {
        return depth;
    }

    public String getRegion() {
        return region;
    }

    /**
     *
     * @return formatted datetime
     */
    public String getTimedateStr() {
        return VolleyHelper.getDateFormat().format(timedate);
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{").append("src : "+src)
                .append(" eqid : "+eqid)
                .append(" timedate : "+VolleyHelper.getDateFormat().format(timedate))
                .append(" (lat, lon) : ("+lat+";"+lon+")")
                .append(" magnitude : "+magnitude)
                .append(" depth : "+depth)
                .append(" region : "+region);
        return stringBuilder.toString();
    }
}
