package com.siem.siemusuarios.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.FilaMotivosBinding;
import com.siem.siemusuarios.interfaces.DeterminateNextListener;
import com.siem.siemusuarios.interfaces.RadioButtonSelectedListener;
import com.siem.siemusuarios.model.api.MotivoPrecategorizacion;
import com.siem.siemusuarios.ui.custom.CustomFragmentDialog;
import com.siem.siemusuarios.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.List;

public class MotivosAdapter extends RecyclerView.Adapter<MotivosAdapter.MotivosViewHolder> {

    private List<MotivoPrecategorizacion> mListDatos;
    private WeakReference<DeterminateNextListener> mListener;

    public MotivosAdapter(List<MotivoPrecategorizacion> datos, DeterminateNextListener listener){
        mListDatos = datos;
        mListener = new WeakReference<>(listener);
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

    public boolean haveData(){
        for (MotivoPrecategorizacion motivo : mListDatos) {
            if(motivo.getPositionOptionSelected() != null)
                return true;
        }

        return false;
    }


    public class MotivosViewHolder extends RecyclerView.ViewHolder
            implements RadioButtonSelectedListener{

        private FilaMotivosBinding mBinding;
        private MotivoPrecategorizacion mMotivo;
        private Typeface mTypeface;

        public MotivosViewHolder(FilaMotivosBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(MotivoPrecategorizacion motivo) {
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

            if(mMotivo.getPositionOptionSelected() != null){
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
            if(mListener != null && mListener.get() != null)
                mListener.get().determinateNext();
        }
    }

}
