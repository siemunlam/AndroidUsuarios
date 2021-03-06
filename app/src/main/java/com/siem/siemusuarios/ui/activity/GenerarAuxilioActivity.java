package com.siem.siemusuarios.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.siem.siemusuarios.R;
import com.siem.siemusuarios.SeleccionarUpdateContactoStrategy;
import com.siem.siemusuarios.databinding.ActivityGenerarAuxilioBinding;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.model.api.ResponseGenerarAuxilio;
import com.siem.siemusuarios.model.api.ResponseSuscribirse;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.model.app.Perfil;
import com.siem.siemusuarios.ui.custom.CustomEditableTextview;
import com.siem.siemusuarios.ui.custom.CustomFragmentDialog;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.PreferencesHelper;
import com.siem.siemusuarios.utils.RetrofitClient;
import com.siem.siemusuarios.utils.Utils;

import java.util.Map;

import it.sephiroth.android.library.tooltip.Tooltip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.siem.siemusuarios.utils.Constants.PLACE_AUTOCOMPLETE_REQUEST_CODE;

/**
 * Created by lucas on 10/18/17.
 */

public class GenerarAuxilioActivity extends ToolbarActivity implements
        DialogInterface.OnClickListener {

    private static final int UPDATE_PERFIL = 1055;
    private static final int TIME_TOOLTIP_DURATION = 10000;

    private Auxilio mAuxilio;
    private ActivityGenerarAuxilioBinding mBinding;
    private Typeface mBoldTypeface;
    private Typeface mTypeface;
    private Tooltip.TooltipView mTooltipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_generar_auxilio);
        setToolbar(true);

        mAuxilio = getAuxilio(savedInstanceState);
        mBoldTypeface = Typeface.createFromAsset(getAssets(), Constants.PRIMARY_FONT_BOLD);
        mTypeface = Typeface.createFromAsset(getAssets(), Constants.PRIMARY_FONT);

        setUI();
        showMotivos();

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

        mBinding.ubicacion.setOnImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startPlaceAutocomplete(GenerarAuxilioActivity.this);
            }
        });

        mBinding.nombre.setOnImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPerfil();
            }
        });

        mBinding.contacto.setOnImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPerfil();
            }
        });

        mBinding.referencia.setOnImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mTooltipView != null)
                    mTooltipView.hide();
                mTooltipView = Tooltip.make(GenerarAuxilioActivity.this,
                        new Tooltip.Builder(101)
                                .anchor(mBinding.referencia.getImage(), Tooltip.Gravity.BOTTOM)
                                .closePolicy(
                                        new Tooltip.ClosePolicy()
                                                .insidePolicy(true, false)
                                                .outsidePolicy(true, false), TIME_TOOLTIP_DURATION)
                                .activateDelay(800)
                                .showDelay(300)
                                .text(getString(R.string.exampleReferencia))
                                .maxWidth(750)
                                .withArrow(true)
                                .withOverlay(true)
                                .typeface(mBoldTypeface)
                                .floatingAnimation(Tooltip.AnimationBuilder.DEFAULT)
                                .withStyleId(R.style.TooltipStyle)
                                .build()
                );
                mTooltipView.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng latlng = place.getLatLng();
                mAuxilio.setUbicacion(place.getAddress().toString());
                mAuxilio.setLatitud(String.valueOf(latlng.latitude));
                mAuxilio.setLongitud(String.valueOf(latlng.longitude));
                setDatos();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Toast.makeText(GenerarAuxilioActivity.this, getString(R.string.errorPlaceApi, status.getStatusCode()), Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == UPDATE_PERFIL && resultCode == RESULT_OK){
            Perfil perfil = (Perfil)data.getSerializableExtra(Constants.KEY_PERFIL);
            mAuxilio.setPerfil(perfil);
            setDatos();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(Constants.KEY_AUXILIO, getAuxilio(savedInstanceState));
    }

    private void deleteMotivo(String key) {
        if (mAuxilio.getMotivos().size() > 1) {
            mAuxilio.getMotivos().remove(key);
            showMotivos();
        }else{
            Toast.makeText(this, getString(R.string.minimumMotivos), Toast.LENGTH_LONG).show();
        }
    }

    private void setUI() {
        mBinding.titleAuxilioGenerar.setTypeface(mBoldTypeface);
        mBinding.edittextObservaciones.setTypeface(mTypeface);
        setDatos();
    }

    private void setDatos(){
        mBinding.ubicacion.setTextSpanned(getString(R.string.ubicacionDetalle, mAuxilio.getUbicacion()));
        mBinding.nombre.setTextSpanned(getString(R.string.nombreDetalle, mAuxilio.getNombre(getString(R.string.noEspecificado))));
        mBinding.contacto.setTextSpanned(getString(R.string.contactoDetalle, mAuxilio.getContacto(getString(R.string.noEspecificado))));
    }

    private void showMotivos() {
        mBinding.contentMotivos.removeAllViews();
        for (final Map.Entry<String, String> entry : mAuxilio.getMotivos().entrySet()) {
            CustomEditableTextview editableTextview = new CustomEditableTextview(this);
            editableTextview.setTextSpanned(getString(R.string.motivosDetalle, entry.getKey(), entry.getValue()));
            //editableTextview.setDrawable(R.drawable.ic_delete);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, (int) getResources().getDimension(R.dimen.halfDefaultMargin), 0, 0);
            /*editableTextview.setOnImageClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteMotivo(entry.getKey());
                }
            });*/
            mBinding.contentMotivos.addView(editableTextview, layoutParams);
        }
    }

    private void editPerfil() {
        Intent intent = new Intent(this, SeleccionarContactoActivity.class);
        intent.putExtra(Constants.KEY_SELECCIONAR_CONTACTO_STRATEGY, new SeleccionarUpdateContactoStrategy());
        Utils.startActivityWithTransitionForResult(this, intent, UPDATE_PERFIL);
    }

    private Auxilio getAuxilio(Bundle savedInstanceState) {
        if(mAuxilio == null){
            if(savedInstanceState != null && savedInstanceState.containsKey(Constants.KEY_AUXILIO))
                return (Auxilio)savedInstanceState.getSerializable(Constants.KEY_AUXILIO);
            else
                return (Auxilio)getIntent().getSerializableExtra(Constants.KEY_AUXILIO);
        }else{
            return mAuxilio;
        }
    }

    /**
     * OnClickListener
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        setTouchable(false);
        String jsonMotivos = new Gson().toJson(mAuxilio.getMotivos());
        Call<ResponseGenerarAuxilio> response = RetrofitClient.getServerClient().generarAuxilio(
                mAuxilio.getContacto(null),
                mAuxilio.getUbicacion(),
                mBinding.referencia.getText(),
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
                        ResponseGenerarAuxilio responseGenerarAuxilio = response.body();
                        if(responseGenerarAuxilio != null){
                            suscribirse(responseGenerarAuxilio.getCodigoSuscripcion());
                        }
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

    private void suscribirse(String codigoSuscripcion) {
        mAuxilio.setCodigo(codigoSuscripcion);
        PreferencesHelper preferencesHelper = PreferencesHelper.getInstance();
        Call<ResponseSuscribirse> response = RetrofitClient.getServerClient().suscribirse(codigoSuscripcion, preferencesHelper.getFirebaseToken());
        response.enqueue(new Callback<ResponseSuscribirse>() {
            @Override
            public void onResponse(Call<ResponseSuscribirse> call, Response<ResponseSuscribirse> response) {
                switch(response.code()){
                    case Constants.CODE_SERVER_OK:
                    case Constants.CODE_SERVER_OK_201:
                        ResponseSuscribirse responseSuscribirse = response.body();
                        if(responseSuscribirse != null){
                            mAuxilio.setEstado(responseSuscribirse.getStatus());
                        }
                        goToConsultarAuxilio();
                        break;
                    default:
                        goToConsultarAuxilio();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseSuscribirse> call, Throwable t) {
                goToConsultarAuxilio();
            }
        });
    }

    private void goToConsultarAuxilio() {
        if(mAuxilio.getEstado() == null || mAuxilio.getEstado().isEmpty())
            mAuxilio.setEstado(getString(R.string.estadoPendiente));
        Toast.makeText(this, getString(R.string.auxilioGeneradoCorrectamente, mAuxilio.getCodigo()), Toast.LENGTH_LONG).show();
        DBWrapper.saveAuxilio(this, mAuxilio);
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void error(){
        setTouchable(true);
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show();
    }

    private void setTouchable(boolean touchable) {
        mBinding.edittextObservaciones.setEnabled(touchable);
        mBinding.referencia.setEnabled(touchable);
        mBinding.progressContent.setVisibility(touchable ? View.GONE : View.VISIBLE);
        if(touchable){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }else{
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}
