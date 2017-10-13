package com.siem.siemusuarios.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivitySplashBinding;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.model.api.MotivoPrecategorizacion;
import com.siem.siemusuarios.model.api.ResponseMotivos;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.RetrofitClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lucas on 2/10/17.
 */

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding mBinding;

    //TODO: Terminar splash
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        getMotivosPrecategorizacion();
    }

    public void getMotivosPrecategorizacion() {
        Call<ResponseMotivos> response = RetrofitClient.getServerClient().getMotivosPrecategorizacion();
        response.enqueue(new Callback<ResponseMotivos>() {
            @Override
            public void onResponse(Call<ResponseMotivos> call, Response<ResponseMotivos> response) {
                switch(response.code()){
                    case Constants.CODE_SERVER_OK:
                        ResponseMotivos responseMotivos = response.body();
                        savePrecategorizacionMotivos(responseMotivos);
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

            private void savePrecategorizacionMotivos(ResponseMotivos responseMotivos) {
                DBWrapper.cleanPrecategorizaciones(SplashActivity.this);
                HashMap<String, List<String>> motivos = responseMotivos.getListMotivos();
                if(motivos != null){
                    for (Map.Entry<String, List<String>> entry : motivos.entrySet()) {
                        MotivoPrecategorizacion motivo = new MotivoPrecategorizacion(entry.getKey());
                        List<String> listOptions = entry.getValue();
                        motivo.setListOptions(listOptions);
                        DBWrapper.savePrecategorizacion(SplashActivity.this, motivo);
                    }
                }
                goToFirstActivity();
            }

            private void error() {
                finish();
            }
        });
    }

    private void goToFirstActivity() {
        startActivity(new Intent(SplashActivity.this, PrecategorizacionActivity.class));
        finish();
    }

}
