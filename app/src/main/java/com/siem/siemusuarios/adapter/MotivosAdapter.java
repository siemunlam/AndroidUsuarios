package com.siem.siemusuarios.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.FilaMotivosBinding;
import com.siem.siemusuarios.interfaces.RadioButtonSelectedListener;
import com.siem.siemusuarios.model.api.MotivoPrecategorizacion;
import com.siem.siemusuarios.ui.custom.CustomFragmentDialog;

import java.util.List;

public class MotivosAdapter extends RecyclerView.Adapter<MotivosAdapter.MotivosViewHolder> {

    private List<MotivoPrecategorizacion> mListDatos;

    public MotivosAdapter(List<MotivoPrecategorizacion> datos){
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
        MotivoPrecategorizacion motivo = mListDatos.get(holder.getAdapterPosition());
        holder.bind(motivo);
    }

    @Override
    public int getItemCount() {
        return mListDatos != null ? mListDatos.size() : 0;
    }

    public void addMotivo(MotivoPrecategorizacion motivo){
        mListDatos.add(motivo);
    }

    public void setListDatos(List<MotivoPrecategorizacion> listDatos) {
        mListDatos = listDatos;
        notifyDataSetChanged();
    }


    public class MotivosViewHolder extends RecyclerView.ViewHolder
            implements RadioButtonSelectedListener{

        //TODO: Change letter to San francisco
        private FilaMotivosBinding mBinding;
        private MotivoPrecategorizacion mMotivo;

        public MotivosViewHolder(FilaMotivosBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(MotivoPrecategorizacion motivo) {
            mMotivo = motivo;

            mBinding.textMotivo.setText(motivo.getDescripcion());
            if(mMotivo.getPositionOptionSelected() != null)
                mBinding.contentRow.setBackgroundColor(Color.RED);

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
        }

        @Override
        public void radioButtonSelected(int positionItem) {
            mMotivo.setPositionOptionSelected(positionItem);
            notifyDataSetChanged();
        }
    }

}
