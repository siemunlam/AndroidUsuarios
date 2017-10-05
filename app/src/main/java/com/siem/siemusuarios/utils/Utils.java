package com.siem.siemusuarios.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.siem.siemusuarios.R;

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
}
