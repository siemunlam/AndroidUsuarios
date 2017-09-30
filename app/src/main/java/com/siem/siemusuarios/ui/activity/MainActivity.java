package com.siem.siemusuarios.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.adapter.MotivosAdapter;
import com.siem.siemusuarios.databinding.ActivityMainBinding;
import com.siem.siemusuarios.model.api.Motivo;
import com.siem.siemusuarios.model.api.ResponseMotivos;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerEndItem;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerItem;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.RetrofitClient;
import com.siem.siemusuarios.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends ToolbarActivity {

    private ActivityMainBinding mBinding;
    private MotivosAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setToolbar(false);

        mAdapter = new MotivosAdapter(new ArrayList<Motivo>());
        mBinding.recyclerview.setAdapter(mAdapter);
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));
        mBinding.recyclerview.addItemDecoration(new CustomDecorationDividerEndItem(ContextCompat.getDrawable(this, R.drawable.custom_dividerrecyclerview)));

        getMotivosPrecategorizacion();
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
                Utils.startActivityWithTransition(this, new Intent(MainActivity.this, PerfilesActivity.class));
                return true;

            case R.id.menuConsultarAuxilio:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getMotivosPrecategorizacion() {
        Call<ResponseMotivos> response = RetrofitClient.getServerClient().getMotivosPrecategorizacion();
        response.enqueue(new Callback<ResponseMotivos>() {
            @Override
            public void onResponse(Call<ResponseMotivos> call, Response<ResponseMotivos> response) {
                switch(response.code()){
                    case Constants.CODE_SERVER_OK:
                        ResponseMotivos responseMotivos = response.body();
                        HashMap<String, List<String>> motivos = responseMotivos.getListMotivos();
                        if(motivos != null){
                            for (Map.Entry<String, List<String>> entry : motivos.entrySet()) {
                                Motivo motivo = new Motivo(entry.getKey());
                                List<String> listOptions = entry.getValue();
                                motivo.setListOptions(listOptions);
                                mAdapter.addMotivo(motivo);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                    default:
                        error();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseMotivos> call, Throwable t) {
                error();
            }

            private void error() {
                finish();
            }
        });
    }
}
