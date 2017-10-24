package com.siem.siemusuarios.ui.activity;

import android.database.ContentObserver;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.adapter.AuxiliosAdapter;
import com.siem.siemusuarios.databinding.ActivityConsultarAuxilioBinding;
import com.siem.siemusuarios.db.DBContract;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.interfaces.EdittextDialogListener;
import com.siem.siemusuarios.model.api.ResponseSuscribirse;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerEndItem;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerItem;
import com.siem.siemusuarios.ui.custom.CustomFragmentDialog;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.PreferencesHelper;
import com.siem.siemusuarios.utils.RetrofitClient;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lucas on 10/23/17.
 */

public class ConsultarAuxilioActivity extends ToolbarActivity implements
        EdittextDialogListener {

    private ActivityConsultarAuxilioBinding mBinding;
    private AuxiliosAdapter mAdapter;
    private ContentObserver mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_consultar_auxilio);
        setToolbar(false);

        mAdapter = new AuxiliosAdapter(null);
        mBinding.recyclerview.setAdapter(mAdapter);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerEndItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));

        mObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
            public void onChange(boolean selfChange) {
                setNewData();
            }
        };
    }

    @Override
    protected void onResume(){
        super.onResume();
        registerContentObserver();
        setNewData();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterContentObserver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_consultar_auxilio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCargarCodigoAuxilio:
                new CustomFragmentDialog().getEditTextDialog(
                        ConsultarAuxilioActivity.this,
                        getString(R.string.ingresarCodigo),
                        getString(R.string.aceptar),
                        null,
                        this,
                        true
                ).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void registerContentObserver() {
        getContentResolver().registerContentObserver(DBContract.Auxilios.CONTENT_URI, true, mObserver);
    }

    private void unregisterContentObserver() {
        if (mObserver != null)
            getContentResolver().unregisterContentObserver(mObserver);
    }

    private void setNewData() {
        mAdapter.setListDatos(DBWrapper.getAuxilios(this));
    }

    /**
     * EdittextDialogListener
     */
    @Override
    public void edittextChanged(String text) {
        final String upperText = text.toUpperCase();
        PreferencesHelper preferencesHelper = PreferencesHelper.getInstance();
        Call<ResponseSuscribirse> response = RetrofitClient.getServerClient().suscribirse(upperText, preferencesHelper.getFirebaseToken());
        response.enqueue(new Callback<ResponseSuscribirse>() {
            @Override
            public void onResponse(Call<ResponseSuscribirse> call, Response<ResponseSuscribirse> response) {
                switch(response.code()){
                    case Constants.CODE_SERVER_OK:
                    case Constants.CODE_SERVER_OK_201:
                        ResponseSuscribirse responseSuscribirse = response.body();
                        if(responseSuscribirse != null){
                            Auxilio auxilio = new Auxilio();
                            auxilio.setCodigo(upperText);
                            auxilio.setEstado(responseSuscribirse.getStatus());
                            auxilio.setFecha(String.valueOf(new Date().getTime()));
                            DBWrapper.saveAuxilio(ConsultarAuxilioActivity.this, auxilio);
                            Toast.makeText(ConsultarAuxilioActivity.this, getString(R.string.suscribirseCorrectamente, upperText), Toast.LENGTH_LONG).show();
                        }
                        break;
                    case Constants.CODE_SERVER_ERROR:
                        Toast.makeText(ConsultarAuxilioActivity.this, getString(R.string.errorCodigoAuxilio), Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(ConsultarAuxilioActivity.this, getString(R.string.errorNoSuscribirse), Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseSuscribirse> call, Throwable t) {
                Toast.makeText(ConsultarAuxilioActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
            }
        });
    }
}
