package com.siem.siemusuarios.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.siem.siemusuarios.databinding.FilaPerfilesBinding;
import com.siem.siemusuarios.model.app.Perfil;

import java.util.List;

public class PerfilesAdapter extends RecyclerView.Adapter<PerfilesAdapter.PerfilesViewHolder> {

    private static final String FORMAT_NOMBRE_APELLIDO = "%1$s %2$s";

    private List<Perfil> mListDatos;

    public PerfilesAdapter(List<Perfil> datos){
        mListDatos = datos;
    }

    @Override
    public PerfilesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FilaPerfilesBinding filaPerfilesBinding = FilaPerfilesBinding.inflate(layoutInflater, parent, false);
        return new PerfilesViewHolder(filaPerfilesBinding);
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

    public void setListDatos(List<Perfil> listDatos) {
        mListDatos = listDatos;
        notifyDataSetChanged();
    }

    public boolean haveDate() {
        return mListDatos != null && mListDatos.size() > 0;
    }

    public class PerfilesViewHolder extends RecyclerView.ViewHolder{

        private final FilaPerfilesBinding mBinding;

        public PerfilesViewHolder(FilaPerfilesBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Perfil perfil) {
            mBinding.textNombreApellido.setText(String.format(FORMAT_NOMBRE_APELLIDO, perfil.getNombre(), perfil.getApellido()));
        }
    }

}
