package com.siem.siemusuarios.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivityConsultarAuxilioBinding;

/**
 * Created by lucas on 10/23/17.
 */

public class ConsultarAuxilioActivity extends ToolbarActivity {

    private ActivityConsultarAuxilioBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_consultar_auxilio);
        setToolbar(true);

    }

}
