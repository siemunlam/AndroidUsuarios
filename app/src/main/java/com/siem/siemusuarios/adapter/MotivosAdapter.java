package com.siem.siemusuarios.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.siem.siemusuarios.databinding.FilaMotivosBinding;
import com.siem.siemusuarios.model.Motivos;

import java.util.List;

public class MotivosAdapter extends RecyclerView.Adapter<MotivosAdapter.MotivosViewHolder> {

    private List<Motivos> mListDatos;

    public MotivosAdapter(List<Motivos> datos){
        mListDatos = datos;
    }

    @Override
    public MotivosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FilaMotivosBinding filaMotivosBinding = FilaMotivosBinding.inflate(layoutInflater, parent, false);
        return new MotivosViewHolder(filaMotivosBinding);
    }

    @Override
    public void onBindViewHolder(final MotivosViewHolder holder, int position) {
        Motivos motivo = mListDatos.get(holder.getAdapterPosition());
        holder.bind(motivo);
    }

    @Override
    public int getItemCount() {
        return mListDatos != null ? mListDatos.size() : 0;
    }

    public class MotivosViewHolder extends RecyclerView.ViewHolder{

        private final FilaMotivosBinding mBinding;

        public MotivosViewHolder(FilaMotivosBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Motivos motivo) {
            mBinding.textMotivo.setText(motivo.getDescripcion());
        }
    }

}
