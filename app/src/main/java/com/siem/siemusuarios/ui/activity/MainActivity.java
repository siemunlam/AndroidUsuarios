package com.siem.siemusuarios.ui.activity;

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
import com.siem.siemusuarios.model.Motivos;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerEndItem;
import com.siem.siemusuarios.ui.custom.CustomDecorationDividerItem;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

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


        Motivos motivo1 = new Motivos("pepe");
        Motivos motivo2 = new Motivos("adrian");
        Motivos motivo3 = new Motivos("juan");
        List<Motivos> listMotivos = new ArrayList<>();
        listMotivos.add(motivo1);
        listMotivos.add(motivo2);
        listMotivos.add(motivo3);
        mAdapter = new MotivosAdapter(listMotivos);
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

                return true;

            case R.id.menuConsultarAuxilio:

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getMotivosPrecategorizacion() {
        Call<Object> response = RetrofitClient.getServerClient().getMotivosPrecategorizacion();
        response.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                switch(response.code()){
                    case Constants.CODE_SERVER_OK:
                        //TODO: Terminar
                        break;
                    default:
                        error();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                error();
            }

            private void error() {
                finish();
            }
        });
    }
}
