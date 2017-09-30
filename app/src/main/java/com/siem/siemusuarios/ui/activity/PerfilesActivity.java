package com.siem.siemusuarios.ui.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.adapter.PerfilesAdapter;
import com.siem.siemusuarios.databinding.ActivityPerfilesBinding;
import com.siem.siemusuarios.utils.Constants;

/**
 * Created by Lucas on 25/9/17.
 */

public class PerfilesActivity extends ToolbarActivity {

    private Typeface mTypeface;
    private ActivityPerfilesBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_perfiles);
        setToolbar(false);

        mTypeface = Typeface.createFromAsset(getAssets(), Constants.PRIMARY_FONT);
        mBinding.recyclerview.setAdapter(new PerfilesAdapter(null));
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mBinding.recyclerview.setVisibility(View.GONE);
        mBinding.contentNoPerfiles.setVisibility(View.VISIBLE);
        mBinding.textviewMsgNoPerfiles.setTypeface(mTypeface);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_perfiles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAgregarPerfil:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
