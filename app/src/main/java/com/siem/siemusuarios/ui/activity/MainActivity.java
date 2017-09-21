package com.siem.siemusuarios.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivityMainBinding;

public class MainActivity extends ToolbarActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setToolbar(false);
    }
}
