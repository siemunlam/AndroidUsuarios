package com.siem.siemusuarios.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.FilaMotivosBinding;
import com.siem.siemusuarios.interfaces.RadioButtonDialogListener;
import com.siem.siemusuarios.model.api.Motivo;
import com.siem.siemusuarios.ui.custom.CustomFragmentDialog;
import com.siem.siemusuarios.utils.Constants;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MotivosAdapter extends RecyclerView.Adapter<MotivosAdapter.MotivosViewHolder> {

    private List<Motivo> mListDatos;

    public MotivosAdapter(List<Motivo> datos){
        mListDatos = datos;
        sortData();
    }

    @Override
    public MotivosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FilaMotivosBinding filaMotivosBinding = FilaMotivosBinding.inflate(layoutInflater, parent, false);
        return new MotivosViewHolder(filaMotivosBinding);
    }

    @Override
    public void onBindViewHolder(final MotivosViewHolder holder, int position) {
        Motivo motivo = mListDatos.get(holder.getAdapterPosition());
        holder.bind(motivo);
    }

    @Override
    public int getItemCount() {
        return mListDatos != null ? mListDatos.size() : 0;
    }

    public void setListDatos(List<Motivo> listDatos) {
        mListDatos = listDatos;
        sortData();
        notifyDataSetChanged();
    }

    private void sortData() {
        Collections.sort(mListDatos, new Comparator<Motivo>() {
            @Override
            public int compare(Motivo first, Motivo second) {
                return first.getDescripcion().compareTo(second.getDescripcion());
            }
        });
    }

    public void setListDatos(HashMap<String, Integer> motivos){
        for (Motivo motivo : mListDatos) {
            if(motivos.containsKey(motivo.getDescripcion())){
                motivo.setPositionOptionSelected(motivos.get(motivo.getDescripcion()));
            }
        }
        notifyDataSetChanged();
    }

    public boolean hasData(){
        for (Motivo motivo : mListDatos) {
            if(motivo.isSelected())
                return true;
        }

        return false;
    }

    public HashMap<String, String> getMotivos(){
        HashMap<String, String> motivos = new HashMap<>();
        for (Motivo motivo : mListDatos) {
            if(motivo.isSelected()){
                motivos.put(motivo.getDescripcion(), motivo.getSelected());
            }
        }

        return motivos;
    }

    public HashMap<String, Integer> getMotivosSaveState(){
        HashMap<String, Integer> motivos = new HashMap<>();
        for (Motivo motivo : mListDatos) {
            if(motivo.isSelected()){
                motivos.put(motivo.getDescripcion(), motivo.getPositionOptionSelected());
            }
        }

        return motivos;
    }


    public class MotivosViewHolder extends RecyclerView.ViewHolder
            implements RadioButtonDialogListener {

        private FilaMotivosBinding mBinding;
        private Motivo mMotivo;
        private Typeface mTypeface;

        public MotivosViewHolder(FilaMotivosBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(Motivo motivo) {
            mMotivo = motivo;

            mBinding.textMotivo.setText(motivo.getDescripcion());

            mBinding.contentRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new CustomFragmentDialog().getRadioButtonsEstadoDialog(
                            mBinding.contentRow.getContext(),
                            mBinding.contentRow.getContext().getString(R.string.aceptar),
                            mMotivo.getListOptions(),
                            true,
                            MotivosViewHolder.this
                    ).show();
                }
            });

            mBinding.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPositionOptionSelected(null);
                }
            });

            if(mMotivo.isSelected()){
                mBinding.imgDelete.setVisibility(View.VISIBLE);
                mTypeface = Typeface.createFromAsset(mBinding.contentRow.getContext().getAssets(), Constants.PRIMARY_FONT_BOLD);
            }else{
                mBinding.imgDelete.setVisibility(View.GONE);
                mTypeface = Typeface.createFromAsset(mBinding.contentRow.getContext().getAssets(), Constants.PRIMARY_FONT);
            }
            mBinding.textMotivo.setTypeface(mTypeface);
        }

        @Override
        public void radioButtonSelected(int positionItem) {
            setPositionOptionSelected(positionItem);
        }

        private void setPositionOptionSelected(Integer position) {
            mMotivo.setPositionOptionSelected(position);
            notifyDataSetChanged();
        }
    }

}
