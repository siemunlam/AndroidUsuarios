package com.siem.siemusuarios.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivitySplashBinding;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.interfaces.NotificationStrategy;
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

import static com.siem.siemusuarios.fcm.MyFirebaseMessagingService.KEY_NOTIFICATION_STRATEGY;

/**
 * Created by Lucas on 2/10/17.
 */

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding mBinding;
    private boolean mIsSavePrecategorizaciones = false;
    private boolean mIsSaveAjustes = false;
    private Typeface mTypeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        mTypeface = Typeface.createFromAsset(getAssets(), Constants.PRIMARY_FONT);
        PreferencesHelper preferencesHelper = PreferencesHelper.getInstance();
        preferencesHelper.setFirebaseToken(FirebaseInstanceId.getInstance().getToken());

        mBinding.buttonGenerarAuxilio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startActivityWithTransitionForResult(SplashActivity.this, new Intent(SplashActivity.this, PrecategorizacionActivity.class), Constants.KEY_AUXILIO_GENERADO);
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

        mBinding.buttonGenerarAuxilio.setTypeface(mTypeface);
        mBinding.buttonConsultarAuxilio.setTypeface(mTypeface);
        mBinding.buttonPerfiles.setTypeface(mTypeface);

        if(getIntent(). hasExtra(KEY_NOTIFICATION_STRATEGY)){
            NotificationStrategy strategy = (NotificationStrategy) getIntent().getSerializableExtra(KEY_NOTIFICATION_STRATEGY);
            strategy.run(this);
        }
        setBackgroundImage();
    }

    @Override
    protected void onResume(){
        super.onResume();

        getMotivosPrecategorizacion();
        getMotivosAjuste();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.KEY_AUXILIO_GENERADO && resultCode == RESULT_OK){
            Utils.startActivityWithTransition(SplashActivity.this, new Intent(SplashActivity.this, ConsultarAuxilioActivity.class));
        }
    }

    private void setBackgroundImage() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background, options);
        Bitmap resizedbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        BitmapDrawable bmpDrawable = new BitmapDrawable(getResources(), resizedbitmap);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mBinding.imageBackground.setBackground(bmpDrawable);
        }else{
            mBinding.imageBackground.setBackgroundDrawable(bmpDrawable);
        }
        bitmap.recycle();
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
