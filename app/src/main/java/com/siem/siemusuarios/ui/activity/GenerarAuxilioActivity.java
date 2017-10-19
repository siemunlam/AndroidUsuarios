package com.siem.siemusuarios.ui.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivityGenerarAuxilioBinding;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.utils.Constants;

/**
 * Created by lucas on 10/18/17.
 */

public class GenerarAuxilioActivity extends ToolbarActivity {

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
    }

    private void setDatos() {
        mBinding.titleAuxilioGenerar.setTypeface(mBoldTypeface);
        mBinding.edittextReferencia.setTypeface(mTypeface);
        mBinding.edittextObservaciones.setTypeface(mTypeface);

        mBinding.ubicacion.setText(getString(R.string.ubicacionDetalle, mAuxilio.getUbicacion()));
        mBinding.nombre.setText(getString(R.string.nombreDetalle, mAuxilio.getNombre(this)));
        mBinding.contacto.setText(getString(R.string.contactoDetalle, mAuxilio.getContacto(this)));
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

}
