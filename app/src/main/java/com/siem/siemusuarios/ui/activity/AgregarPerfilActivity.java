package com.siem.siemusuarios.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivityAgregarPerfilBinding;

/**
 * Created by Lucas on 29/9/17.
 */

public class AgregarPerfilActivity extends ToolbarActivity {

    private ActivityAgregarPerfilBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_agregar_perfil);
        setToolbar(true);

    }

}
