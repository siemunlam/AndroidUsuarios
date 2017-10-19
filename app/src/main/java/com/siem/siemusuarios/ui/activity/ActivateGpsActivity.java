package com.siem.siemusuarios.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.siem.siemusuarios.R;
import com.siem.siemusuarios.utils.Utils;

/**
 * Created by Lucas on 7/8/17.
 */

public class ActivateGpsActivity extends ToolbarActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final int REQUEST_LOCATION = 1221;

    protected static final long MIN_TIME_UPDATES = 1000;          // mS
    protected static final long MIN_DISTANCE_UPDATES = 0;         // meters
    private GoogleApiClient mGoogleApiClient;
    private PendingResult<LocationSettingsResult> mPendingResult;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest.Builder mLocationSettingsRequest;

    /**
     * Locations
     */
    protected LocationManager mLocationManager;
    protected MyLocationListener mLocationListener;

    protected void getLocation(){
        if(!Utils.isGPSOn(this)){
            mGoogleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            mGoogleApiClient.connect();
            mLocationSetting();
        }else{
            startLocationManager();
        }
    }

    private void startLocationManager() {
        stopLocationManager();
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location == null){
            mLocationListener = new MyLocationListener();
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_UPDATES, MIN_DISTANCE_UPDATES, mLocationListener);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_UPDATES, MIN_DISTANCE_UPDATES, mLocationListener);
            Toast.makeText(this, getString(R.string.getLocation), Toast.LENGTH_LONG).show();
        }else{
            newLocation(location);
            stopLocationManager();
        }
    }

    protected void stopLocationManager() {
        if (mLocationManager != null && mLocationListener != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    public void mLocationSetting() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        mResult();
    }

    public void mResult() {
        mPendingResult = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, mLocationSettingsRequest.build());
        mPendingResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        startLocationManager();
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(ActivateGpsActivity.this, REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {
                            requestActivateGPS();
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        requestActivateGPS();
                        break;
                }
            }
        });
    }

    private void requestActivateGPS() {
        Toast.makeText(ActivateGpsActivity.this, getString(R.string.activateGPS), Toast.LENGTH_LONG).show();
    }

    protected void newLocation(Location location) {
        //Implements in subclass
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationManager();
                        break;

                    case Activity.RESULT_CANCELED:
                        requestActivateGPS();
                        break;

                    default:
                        break;
                }
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            newLocation(location);
            stopLocationManager();
        }

        @Override
        public void onProviderDisabled(String provider) {
            // required for interface, not used
        }

        @Override
        public void onProviderEnabled(String provider) {
            // required for interface, not used
        }

        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            // required for interface, not used
        }
    }
}
