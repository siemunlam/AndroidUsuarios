package com.siem.siemusuarios.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.siem.siemusuarios.R;
import com.siem.siemusuarios.adapter.MotivosAdapter;
import com.siem.siemusuarios.databinding.ActivityPrecategorizacionBinding;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.model.api.Motivo;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerEndItem;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerItem;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import static com.siem.siemusuarios.utils.Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE;

public class PrecategorizacionActivity extends ActivateGpsActivity{

    private static final int LOCATION_PERMISSIONS_REQUEST = 1000;
    private ActivityPrecategorizacionBinding mBinding;
    private MotivosAdapter mAdapter;
    private Typeface mTypeface;

    //TODO: SaveInstanceState

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_precategorizacion);
        setToolbar(false);

        mTypeface = Typeface.createFromAsset(getAssets(), Constants.PRIMARY_FONT_BOLD);
        mAdapter = new MotivosAdapter(new ArrayList<Motivo>());
        mBinding.recyclerview.setAdapter(mAdapter);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerEndItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));

        mBinding.customEdittextUbicacion.setUbicacionOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(PrecategorizacionActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(PrecategorizacionActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }else{
                    ActivityCompat.requestPermissions(PrecategorizacionActivity.this, Constants.locations_permissions, LOCATION_PERMISSIONS_REQUEST);
                }
            }
        });

        mBinding.customEdittextUbicacion.setEdittextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startPlaceAutocomplete(PrecategorizacionActivity.this);
            }
        });

        mBinding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBinding.customEdittextUbicacion.haveLocation() &&
                        mAdapter.haveData()){
                    Auxilio auxilio = new Auxilio();
                    auxilio.setLatitud(String.valueOf(mBinding.customEdittextUbicacion.getLatitude()));
                    auxilio.setLongitud(String.valueOf(mBinding.customEdittextUbicacion.getLongitude()));
                    auxilio.setUbicacion(mBinding.customEdittextUbicacion.getText());
                    auxilio.addMotivos(mAdapter.getMotivos());
                    Intent intent = new Intent(PrecategorizacionActivity.this, AjusteActivity.class);
                    intent.putExtra(Constants.KEY_AUXILIO, auxilio);
                    Utils.startActivityWithTransition(PrecategorizacionActivity.this, intent);
                }else{

                }
            }
        });

        mAdapter.setListDatos(DBWrapper.getAllPrecategorizaciones(this));
        mBinding.titleUbicacion.setTypeface(mTypeface);
        mBinding.titleSintomas.setTypeface(mTypeface);

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(Constants.KEY_DIRECCION) && savedInstanceState.containsKey(Constants.KEY_LAT) && savedInstanceState.containsKey(Constants.KEY_LNG)){
                String direccion = savedInstanceState.getString(Constants.KEY_DIRECCION);
                Double lat = savedInstanceState.getDouble(Constants.KEY_LAT);
                Double lng = savedInstanceState.getDouble(Constants.KEY_LNG);
                mBinding.customEdittextUbicacion.setText(direccion, lat, lng);
            }
            if(savedInstanceState.containsKey(Constants.KEY_MOTIVOS)){
                HashMap<String, Integer> motivos = (HashMap<String, Integer>) savedInstanceState.getSerializable(Constants.KEY_MOTIVOS);
                mAdapter.setListDatos(motivos);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        if(mBinding.customEdittextUbicacion.haveData()){
            savedInstanceState.putString(Constants.KEY_DIRECCION, mBinding.customEdittextUbicacion.getText());
            savedInstanceState.putDouble(Constants.KEY_LAT, mBinding.customEdittextUbicacion.getLatitude());
            savedInstanceState.putDouble(Constants.KEY_LNG, mBinding.customEdittextUbicacion.getLongitude());
        }
        savedInstanceState.putSerializable(Constants.KEY_MOTIVOS, mAdapter.getMotivosSaveState());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuWhatsapp:
                Utils.sendMessageWhatsapp(this);
                return true;

            case R.id.menuLlamar:
                Utils.dialSiem(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case LOCATION_PERMISSIONS_REQUEST:
                if(ActivityCompat.checkSelfPermission(PrecategorizacionActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(PrecategorizacionActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }else{
                    Toast.makeText(PrecategorizacionActivity.this, getString(R.string.noPermissionLocationGranted), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void newLocation(Location location) {
        if(mBinding.customEdittextUbicacion.getText().isEmpty())
            mBinding.customEdittextUbicacion.newLocation(location);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng latlng = place.getLatLng();
                mBinding.customEdittextUbicacion.setText(place.getAddress().toString(), latlng.latitude, latlng.longitude);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Toast.makeText(PrecategorizacionActivity.this, getString(R.string.errorPlaceApi, status.getStatusCode()), Toast.LENGTH_LONG).show();
            }
        }
    }
}
