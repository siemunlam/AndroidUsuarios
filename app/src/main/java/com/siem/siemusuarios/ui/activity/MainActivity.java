package com.siem.siemusuarios.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.ActivityMainBinding;

public class MainActivity extends ToolbarActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setToolbar(false);

        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuWhatsapp:

                return true;

            case R.id.menuLlamar:

                return true;

            case R.id.menuPerfiles:

                return true;

            case R.id.menuConsultarAuxilio:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
