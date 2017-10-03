package com.siem.siemusuarios.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivitySplashBinding;

/**
 * Created by Lucas on 2/10/17.
 */

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding mBinding;

    //TODO: Terminar splash
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }
}
