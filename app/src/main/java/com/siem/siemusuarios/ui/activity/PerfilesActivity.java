package com.siem.siemusuarios.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.adapter.PerfilesAdapter;
import com.siem.siemusuarios.databinding.ActivityPerfilesBinding;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerEndItem;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerItem;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.Utils;

/**
 * Created by Lucas on 25/9/17.
 */

public class PerfilesActivity extends ToolbarActivity {

    private Typeface mTypeface;
    private ActivityPerfilesBinding mBinding;
    private PerfilesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_perfiles);
        setToolbar(true);

        mTypeface = Typeface.createFromAsset(getAssets(), Constants.PRIMARY_FONT);
        mAdapter = new PerfilesAdapter(null);
        mBinding.recyclerview.setAdapter(mAdapter);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerEndItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));

        mBinding.textviewMsgNoPerfiles.setTypeface(mTypeface);
    }

    @Override
    public void onResume(){
        super.onResume();
        controlatePerfiles();
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
                Utils.startActivityWithTransition(PerfilesActivity.this, new Intent(PerfilesActivity.this, AgregarPerfilActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void controlatePerfiles() {
        mAdapter.setListDatos(DBWrapper.getAllPerfiles(this));
        if(mAdapter.haveDate()){
            mBinding.recyclerview.setVisibility(View.VISIBLE);
            mBinding.contentNoPerfiles.setVisibility(View.GONE);
        }else{
            mBinding.recyclerview.setVisibility(View.GONE);
            mBinding.contentNoPerfiles.setVisibility(View.VISIBLE);
        }
    }
}
