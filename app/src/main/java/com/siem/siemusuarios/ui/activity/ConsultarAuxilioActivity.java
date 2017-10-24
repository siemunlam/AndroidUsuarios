package com.siem.siemusuarios.ui.activity;

import android.database.ContentObserver;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.adapter.AuxiliosAdapter;
import com.siem.siemusuarios.databinding.ActivityConsultarAuxilioBinding;
import com.siem.siemusuarios.db.DBContract;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerEndItem;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerItem;

/**
 * Created by lucas on 10/23/17.
 */

public class ConsultarAuxilioActivity extends ToolbarActivity {

    private ActivityConsultarAuxilioBinding mBinding;
    private AuxiliosAdapter mAdapter;
    private ContentObserver mObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_consultar_auxilio);
        setToolbar(false);

        mAdapter = new AuxiliosAdapter(null);
        mBinding.recyclerview.setAdapter(mAdapter);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerEndItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));

        mObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
            public void onChange(boolean selfChange) {
                setNewData();
            }
        };
    }

    @Override
    protected void onResume(){
        super.onResume();
        registerContentObserver();
        setNewData();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterContentObserver();
    }

    private void registerContentObserver() {
        getContentResolver().registerContentObserver(DBContract.Auxilios.CONTENT_URI, true, mObserver);
    }

    private void unregisterContentObserver() {
        if (mObserver != null)
            getContentResolver().unregisterContentObserver(mObserver);
    }

    private void setNewData() {
        mAdapter.setListDatos(DBWrapper.getAuxilios(this));
    }
}
