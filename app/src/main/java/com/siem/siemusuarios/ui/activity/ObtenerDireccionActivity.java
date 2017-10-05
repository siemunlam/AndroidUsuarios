package com.siem.siemusuarios.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivityObtenerDireccionBinding;

/**
 * Created by Lucas on 4/10/17.
 */

public class ObtenerDireccionActivity extends ToolbarActivity {

    private ActivityObtenerDireccionBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_obtener_direccion);
    }

}
