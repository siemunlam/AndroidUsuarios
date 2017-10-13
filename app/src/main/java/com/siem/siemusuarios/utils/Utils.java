package com.siem.siemusuarios.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.text.TextUtils;

import com.siem.siemusuarios.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Lucas on 20/9/17.
 */

public class Utils {
    public static void addStartTransitionAnimation(Activity activity){
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void addFinishTransitionAnimation(Activity activity){
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public static void addDefaultFinishTransitionAnimation(Activity activity){
        activity.overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
    }

    public static void startActivityWithTransition(Activity activity, Intent intent) {
        activity.startActivity(intent);
        addStartTransitionAnimation(activity);
    }

    public static boolean isGPSOn(final Activity activity) {
        LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static String getStringAddress(Context mContext, double latitude, double longitude) throws IOException {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        final List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        if(addresses.size() > 0 && addresses.get(0).getCountryCode() != null){
            Address mAddress = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<>();
            for(int i = 0; i <= mAddress.getMaxAddressLineIndex(); i++) {
                addressFragments.add(mAddress.getAddressLine(i));
            }
            return TextUtils.join(", ", addressFragments);
        }else
            return String.format(Constants.FORMAT_NO_DIRECTION, Constants.df.format(latitude), Constants.df.format(longitude));
    }
}
