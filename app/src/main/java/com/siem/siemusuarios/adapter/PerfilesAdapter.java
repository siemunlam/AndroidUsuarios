package com.siem.siemusuarios.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.siem.siemusuarios.databinding.FilaMotivosBinding;
import com.siem.siemusuarios.model.Perfil;

import java.util.List;

public class PerfilesAdapter extends RecyclerView.Adapter<PerfilesAdapter.PerfilesViewHolder> {

    private List<Perfil> mListDatos;

    public PerfilesAdapter(List<Perfil> datos){
        mListDatos = datos;
    }

    @Override
    public PerfilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FilaMotivosBinding filaMotivosBinding = FilaMotivosBinding.inflate(layoutInflater, parent, false);
        return new PerfilesViewHolder(filaMotivosBinding);
    }

    @Override
    public void onBindViewHolder(final PerfilesViewHolder holder, int position) {
        Perfil perfil = mListDatos.get(holder.getAdapterPosition());
        holder.bind(perfil);
    }

    @Override
    public int getItemCount() {
        return mListDatos != null ? mListDatos.size() : 0;
    }

    public class PerfilesViewHolder extends RecyclerView.ViewHolder{

        private final FilaMotivosBinding mBinding;

        public PerfilesViewHolder(FilaMotivosBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Perfil perfil) {
            //mBinding.textMotivo.setText(motivo.getDescripcion());
        }
    }

}
