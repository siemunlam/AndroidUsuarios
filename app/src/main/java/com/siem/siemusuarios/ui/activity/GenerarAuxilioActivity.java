package com.siem.siemusuarios.ui.activity;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivityGenerarAuxilioBinding;
import com.siem.siemusuarios.model.api.ResponseGenerarAuxilio;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.ui.custom.CustomEditableTextview;
import com.siem.siemusuarios.ui.custom.CustomFragmentDialog;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.RetrofitClient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lucas on 10/18/17.
 */

public class GenerarAuxilioActivity extends ToolbarActivity implements
        DialogInterface.OnClickListener {

    private Auxilio mAuxilio;
    private ActivityGenerarAuxilioBinding mBinding;
    private Typeface mBoldTypeface;
    private Typeface mTypeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_generar_auxilio);
        setToolbar(true);

        mAuxilio = getAuxilio(savedInstanceState);
        mBoldTypeface = Typeface.createFromAsset(getAssets(), Constants.PRIMARY_FONT_BOLD);
        mTypeface = Typeface.createFromAsset(getAssets(), Constants.PRIMARY_FONT);

        setDatos();
        for (Map.Entry<String, String> entry : mAuxilio.getMotivos().entrySet()) {
            CustomEditableTextview editableTextview = new CustomEditableTextview(this);
            editableTextview.setText(getString(R.string.motivosDetalle, entry.getKey(), entry.getValue()));
            editableTextview.setDrawable(R.drawable.ic_delete);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, (int) getResources().getDimension(R.dimen.halfDefaultMargin), 0, 0);
            mBinding.contentMotivos.addView(editableTextview, layoutParams);
        }

        mBinding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CustomFragmentDialog().getTextViewDialog(
                        GenerarAuxilioActivity.this,
                        getString(R.string.deseaGenerarAuxilio),
                        getString(android.R.string.yes),
                        GenerarAuxilioActivity.this,
                        getString(android.R.string.no),
                        null,
                        true).show();
            }
        });
    }

    private void setDatos() {
        mBinding.titleAuxilioGenerar.setTypeface(mBoldTypeface);
        mBinding.edittextReferencia.setTypeface(mTypeface);
        mBinding.edittextObservaciones.setTypeface(mTypeface);

        mBinding.ubicacion.setText(getString(R.string.ubicacionDetalle, mAuxilio.getUbicacion()));
        mBinding.nombre.setText(getString(R.string.nombreDetalle, mAuxilio.getNombre(getString(R.string.noEspecificado))));
        mBinding.contacto.setText(getString(R.string.contactoDetalle, mAuxilio.getContacto(getString(R.string.noEspecificado))));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(Constants.KEY_AUXILIO, getAuxilio(savedInstanceState));
    }

    private Auxilio getAuxilio(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.containsKey(Constants.KEY_AUXILIO))
            return (Auxilio)savedInstanceState.getSerializable(Constants.KEY_AUXILIO);
        else
            return (Auxilio)getIntent().getSerializableExtra(Constants.KEY_AUXILIO);
    }

    /**
     * OnClickListener
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        String jsonMotivos = new Gson().toJson(mAuxilio.getMotivos());
        Call<ResponseGenerarAuxilio> response = RetrofitClient.getServerClient().generarAuxilio(
                mAuxilio.getContacto(null),
                mAuxilio.getUbicacion(),
                mBinding.edittextReferencia.getText().toString(),
                mAuxilio.getLatitud(),
                mAuxilio.getLongitud(),
                jsonMotivos,
                mAuxilio.getOrigen(),
                mAuxilio.getNombre(null),
                mAuxilio.getSexo(this, null),
                mBinding.edittextObservaciones.getText().toString()
        );
        response.enqueue(new Callback<ResponseGenerarAuxilio>() {
            @Override
            public void onResponse(Call<ResponseGenerarAuxilio> call, Response<ResponseGenerarAuxilio> response) {
                switch(response.code()){
                    case Constants.CODE_SERVER_OK:
                    case Constants.CODE_SERVER_OK_201:
                        //TODO: Generar
                        Toast.makeText(GenerarAuxilioActivity.this, "GENERADO.....", Toast.LENGTH_LONG).show();
                        //ResponseGenerarAuxilio responseGenerarAuxilio = response.body();
                        //savePrecategorizacionMotivos(responseMotivos);
                        break;
                    default:
                        error();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseGenerarAuxilio> call, Throwable t) {
                error();
            }
        });
    }

    private void error(){
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show();
    }
}
