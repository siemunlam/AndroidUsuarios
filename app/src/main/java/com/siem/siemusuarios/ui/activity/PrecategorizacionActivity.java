package com.siem.siemusuarios.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.adapter.MotivosAdapter;
import com.siem.siemusuarios.databinding.ActivityPrecategorizacionBinding;
import com.siem.siemusuarios.model.api.Motivo;
import com.siem.siemusuarios.model.api.ResponseMotivos;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerEndItem;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerItem;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.RetrofitClient;
import com.siem.siemusuarios.utils.Utils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrecategorizacionActivity extends ActivateGpsActivity {

    private static final int LOCATION_PERMISSIONS_REQUEST = 1000;
    private ActivityPrecategorizacionBinding mBinding;
    private MotivosAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_precategorizacion);
        setToolbar(false);

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
                    ActivityCompat.requestPermissions(PrecategorizacionActivity.this, Constants.permissions, LOCATION_PERMISSIONS_REQUEST);
                }
            }
        });

        mBinding.customEdittextUbicacion.setEdittextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startActivityWithTransition(PrecategorizacionActivity.this, new Intent(PrecategorizacionActivity.this, ObtenerDireccionActivity.class));
            }
        });
        getMotivosPrecategorizacion();
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

                return true;

            case R.id.menuLlamar:

                return true;

            case R.id.menuPerfiles:
                Utils.startActivityWithTransition(this, new Intent(PrecategorizacionActivity.this, PerfilesActivity.class));
                return true;

            case R.id.menuConsultarAuxilio:

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
        mBinding.customEdittextUbicacion.newLocation(location);
    }

    public void getMotivosPrecategorizacion() {
        Call<ResponseMotivos> response = RetrofitClient.getServerClient().getMotivosPrecategorizacion();
        response.enqueue(new Callback<ResponseMotivos>() {
            @Override
            public void onResponse(Call<ResponseMotivos> call, Response<ResponseMotivos> response) {
                switch(response.code()){
                    case Constants.CODE_SERVER_OK:
                        ResponseMotivos responseMotivos = response.body();
                        HashMap<String, List<String>> motivos = responseMotivos.getListMotivos();
                        if(motivos != null){
                            for (Map.Entry<String, List<String>> entry : motivos.entrySet()) {
                                Motivo motivo = new Motivo(entry.getKey());
                                List<String> listOptions = entry.getValue();
                                motivo.setListOptions(listOptions);
                                mAdapter.addMotivo(motivo);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                    default:
                        error();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseMotivos> call, Throwable t) {
                error();
            }

            private void error() {
                finish();
            }
        });
    }
}
