package com.laurietrichet.earthquake.activities.utilities;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.laurietrichet.earthquake.R;

/**
 * Google Play services utility class
 */
public class GooglePlayServiceUtility {
    public static boolean checkGooglePlayServicesAvailable (final Context context){
        int connectionResult = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        return connectionResult == ConnectionResult.SUCCESS;
    }

    public static void displayGooglePlayServicesUnavailableText(final Context context){
        Toast.makeText(context,
                context.getString(R.string.google_play_sevices_not_available), Toast.LENGTH_SHORT)
                .show();
    }
}
