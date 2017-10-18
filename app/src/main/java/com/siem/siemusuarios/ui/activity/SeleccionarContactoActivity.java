package com.siem.siemusuarios.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.adapter.ContactoAdapter;
import com.siem.siemusuarios.databinding.ActivitySeleccionarContactoBinding;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.model.app.Perfil;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerEndItem;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerItem;

import java.util.ArrayList;

/**
 * Created by lucas on 10/17/17.
 */

public class SeleccionarContactoActivity extends ToolbarActivity {

    private ActivitySeleccionarContactoBinding mBinding;
    private ContactoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_seleccionar_contacto);
        setToolbar(true);

        mAdapter = new ContactoAdapter(new ArrayList<Perfil>());
        mBinding.recyclerview.setAdapter(mAdapter);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerEndItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));

        mAdapter.setListDatos(DBWrapper.getAllPerfiles(this));
    }

}
