package com.siem.siemusuarios.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivitySplashBinding;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.model.api.Motivo;
import com.siem.siemusuarios.model.api.ResponseMotivos;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.PreferencesHelper;
import com.siem.siemusuarios.utils.RetrofitClient;
import com.siem.siemusuarios.utils.Utils;

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
    //private Handler mHandler;
    //private Runnable mRunnable;
    private boolean mIsSavePrecategorizaciones = false;
    private boolean mIsSaveAjustes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        PreferencesHelper preferencesHelper = PreferencesHelper.getInstance();
        preferencesHelper.setFirebaseToken(FirebaseInstanceId.getInstance().getToken());

        mBinding.buttonGenerarAuxilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startActivityWithTransition(SplashActivity.this, new Intent(SplashActivity.this, PrecategorizacionActivity.class));
            }
        });

        mBinding.buttonConsultarAuxilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startActivityWithTransition(SplashActivity.this, new Intent(SplashActivity.this, ConsultarAuxilioActivity.class));
            }
        });

        mBinding.buttonPerfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startActivityWithTransition(SplashActivity.this, new Intent(SplashActivity.this, PerfilesActivity.class));
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        getMotivosPrecategorizacion();
        getMotivosAjuste();

        /*mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                goToFirstActivity();
            }
        };
        mHandler.postDelayed(mRunnable, 5000);*/
    }

    @Override
    protected void onPause(){
        super.onPause();
        /*if(mHandler != null)
            mHandler.removeCallbacks(mRunnable);*/
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
                        Motivo motivo = new Motivo(entry.getKey());
                        List<String> listOptions = entry.getValue();
                        motivo.setListOptions(listOptions);
                        DBWrapper.savePrecategorizacion(SplashActivity.this, motivo);
                    }
                }
                mIsSavePrecategorizaciones = true;
                isFinished();
            }
        });
    }

    public void getMotivosAjuste() {
        Call<ResponseMotivos> response = RetrofitClient.getServerClient().getMotivosAjuste();
        response.enqueue(new Callback<ResponseMotivos>() {
            @Override
            public void onResponse(Call<ResponseMotivos> call, Response<ResponseMotivos> response) {
                switch(response.code()){
                    case Constants.CODE_SERVER_OK:
                        ResponseMotivos responseMotivos = response.body();
                        saveAjusteMotivos(responseMotivos);
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

            private void saveAjusteMotivos(ResponseMotivos responseMotivos) {
                DBWrapper.cleanAjuste(SplashActivity.this);
                HashMap<String, List<String>> motivos = responseMotivos.getListMotivos();
                if(motivos != null){
                    for (Map.Entry<String, List<String>> entry : motivos.entrySet()) {
                        Motivo ajuste = new Motivo(entry.getKey());
                        List<String> listOptions = entry.getValue();
                        ajuste.setListOptions(listOptions);
                        DBWrapper.saveAjuste(SplashActivity.this, ajuste);
                    }
                }
                mIsSaveAjustes = true;
                isFinished();
            }
        });
    }

    private void error() {
        //finish();
    }

    private void isFinished() {
        if(mIsSavePrecategorizaciones && mIsSaveAjustes){
            showButtons();
        }
    }

    private void showButtons() {
        mBinding.progress.setVisibility(View.GONE);
        mBinding.contentButtons.setVisibility(View.VISIBLE);
    }

}
