package com.laurietrichet.earthquake.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Earth quake model object
 * implements {@link android.os.Parcelable}
 * implements {@link java.lang.Comparable}
 *
 * Use the {@link com.laurietrichet.earthquake.model.EarthQuake.Builder} object to construct a new object
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

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

    private EarthQuake() throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Forbidden Constructor");
    }

    private EarthQuake(Parcel in) {
        src = in.readString();
        eqid = in.readString();
        try {
            timedate = getDateFormat().parse(in.readString());
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
        dest.writeString(getDateFormat().format(timedate));
        dest.writeString(String.valueOf(lat));
        dest.writeString(String.valueOf(lon));
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
    public int compareTo(@NonNull EarthQuake another) {
        return (this.eqid.compareTo(another.eqid));
    }

    public final static Comparator <EarthQuake> DATE_COMPARATOR = new Comparator<EarthQuake>() {
        @Override
        public int compare(final EarthQuake lhs, final EarthQuake rhs) {
            return (lhs.timedate.compareTo(rhs.timedate));
        }
    };

    public final static Comparator <EarthQuake> MAGNITUDE_COMPARATOR = new Comparator<EarthQuake>() {
        @Override
        public int compare(EarthQuake lhs, EarthQuake rhs) {
            return Double.compare(lhs.magnitude, rhs.magnitude);
        }
    };

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
     * Utility function to get the Date formatter to data received from the service
     * @return SimpleDateFormat a formatter for the web service date format.
     */
    public static SimpleDateFormat getDateFormat (){
        return mDateFormat;
    }

    @Override
    public String toString() {

        return "{" + "src : " + src +
                " eqid : " + eqid +
                " timedate : " + getDateFormat().format(timedate) +
                " (lat, lon) : (" + lat + ";" + lon + ")" +
                " magnitude : " + magnitude +
                " depth : " + depth +
                " region : " + region;
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof EarthQuake)){
            return false;
        }
        EarthQuake earthQuake = (EarthQuake) o;
        if ( earthQuake == this) return true;
        return (eqid == null)? earthQuake.eqid == null : earthQuake.eqid.equals(this.eqid) ;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + eqid.hashCode();
        return result;
    }
}
