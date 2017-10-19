package com.siem.siemusuarios.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.adapter.ContactoAdapter;
import com.siem.siemusuarios.databinding.ActivitySeleccionarContactoBinding;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.interfaces.SeleccionarContactoListener;
import com.siem.siemusuarios.interfaces.SeleccionarContactoStrategy;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.model.app.Perfil;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerEndItem;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerItem;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.Utils;

import java.util.ArrayList;

/**
 * Created by lucas on 10/17/17.
 */

public class SeleccionarContactoActivity extends ToolbarActivity implements
        SeleccionarContactoListener{

    private ActivitySeleccionarContactoBinding mBinding;
    private SeleccionarContactoStrategy mStrategy;
    private ContactoAdapter mAdapter;
    private Typeface mTypeface;
    private Bundle mSavedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_seleccionar_contacto);
        setToolbar(true);

        mSavedInstanceState = savedInstanceState;
        mTypeface = Typeface.createFromAsset(getAssets(), Constants.PRIMARY_FONT);
        if(getIntent().hasExtra(Constants.KEY_SELECCIONAR_CONTACTO_STRATEGY))
            mStrategy = (SeleccionarContactoStrategy) getIntent().getSerializableExtra(Constants.KEY_SELECCIONAR_CONTACTO_STRATEGY);
        mAdapter = new ContactoAdapter(new ArrayList<Perfil>(), this);
        mBinding.recyclerview.setAdapter(mAdapter);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerEndItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));

        mBinding.agregarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startActivityWithTransition(SeleccionarContactoActivity.this, new Intent(SeleccionarContactoActivity.this, AgregarPerfilActivity.class));
            }
        });

        mBinding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeleccionarContactoActivity.this, GenerarAuxilioActivity.class);
                Auxilio auxilio = getAuxilio();
                intent.putExtra(Constants.KEY_AUXILIO, auxilio);
                Utils.startActivityWithTransition(SeleccionarContactoActivity.this, intent);
            }
        });

        mBinding.title.setTypeface(mTypeface);
        mBinding.titleAgregarNuevoPerfil.setTypeface(mTypeface);
    }

    @Override
    public void onResume(){
        super.onResume();
        mAdapter.setListDatos(DBWrapper.getAllPerfiles(this));
    }

    private Auxilio getAuxilio() {
        return (Auxilio)getIntent().getSerializableExtra(Constants.KEY_AUXILIO);
    }

    /**
     * SeleccionarContactoListener
     */
    @Override
    public void contactoSeleccionado(Perfil perfil) {
        if(mStrategy != null)
            mStrategy.contactoSeleccionado(this, perfil, getAuxilio());
    }
}
