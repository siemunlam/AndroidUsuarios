package com.siem.siemusuarios.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.adapter.MotivosAdapter;
import com.siem.siemusuarios.databinding.ActivityAjusteBinding;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.model.api.Motivo;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerEndItem;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerItem;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lucas on 15/10/17.
 */

public class AjusteActivity extends ToolbarActivity {

    private ActivityAjusteBinding mBinding;
    private MotivosAdapter mAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_ajuste);
        setToolbar(true);

        mAdapter = new MotivosAdapter(new ArrayList<Motivo>(), null);
        mBinding.recyclerview.setAdapter(mAdapter);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerEndItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));

        mAdapter.setListDatos(DBWrapper.getAllAjuste(this));

        mBinding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AjusteActivity.this, SeleccionarContactoActivity.class);
                Auxilio auxilio = getAuxilio(savedInstanceState);
                auxilio.addMotivos(mAdapter.getMotivos());
                intent.putExtra(Constants.KEY_AUXILIO, auxilio);
                Utils.startActivityWithTransition(AjusteActivity.this, intent);
            }
        });

        if (savedInstanceState != null) {
            if(savedInstanceState.containsKey(Constants.KEY_MOTIVOS)){
                HashMap<String, Integer> motivos = (HashMap<String, Integer>) savedInstanceState.getSerializable(Constants.KEY_MOTIVOS);
                mAdapter.setListDatos(motivos);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(Constants.KEY_AUXILIO, getAuxilio(savedInstanceState));
        savedInstanceState.putSerializable(Constants.KEY_MOTIVOS, mAdapter.getMotivosSaveState());
    }

    private Auxilio getAuxilio(Bundle savedInstanceState) {
        if(savedInstanceState != null && savedInstanceState.containsKey(Constants.KEY_AUXILIO))
            return (Auxilio)savedInstanceState.getSerializable(Constants.KEY_AUXILIO);
        else
            return (Auxilio)getIntent().getSerializableExtra(Constants.KEY_AUXILIO);
    }
}
