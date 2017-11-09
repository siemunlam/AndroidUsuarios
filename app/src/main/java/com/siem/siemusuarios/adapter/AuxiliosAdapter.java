package com.siem.siemusuarios.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.databinding.FilaAuxiliosBinding;
import com.siem.siemusuarios.interfaces.SwipeItemDeleteListener;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.model.app.Perfil;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.swipe.ItemTouchHelperAdapter;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

public class AuxiliosAdapter extends RecyclerView.Adapter<AuxiliosAdapter.AuxiliosViewHolder> implements
        ItemTouchHelperAdapter {

    //TODO: Pantalla cuando no hay auxilios en el historial
    private List<Auxilio> mListDatos;
    private WeakReference<SwipeItemDeleteListener> mListener;

    public AuxiliosAdapter(List<Auxilio> datos, SwipeItemDeleteListener swipeItemDeleteListener){
        mListDatos = datos;
        mListener = new WeakReference<>(swipeItemDeleteListener);
    }

    @Override
    public AuxiliosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FilaAuxiliosBinding filaAuxiliosBinding = FilaAuxiliosBinding.inflate(layoutInflater, parent, false);
        return new AuxiliosViewHolder(filaAuxiliosBinding);
    }

    @Override
    public void onBindViewHolder(final AuxiliosViewHolder holder, int position) {
        Auxilio auxilio = mListDatos.get(holder.getAdapterPosition());
        holder.bind(auxilio);
    }

    @Override
    public int getItemCount() {
        return mListDatos != null ? mListDatos.size() : 0;
    }

    public void setListDatos(List<Auxilio> listDatos) {
        mListDatos = listDatos;
        notifyDataSetChanged();
    }

    @Override
    public void onItemDismiss(int position) {
        Auxilio auxilio = mListDatos.remove(position);
        notifyItemRemoved(position);
        if(mListener != null && mListener.get() != null)
            mListener.get().deleteItem(auxilio);
    }


    public class AuxiliosViewHolder extends RecyclerView.ViewHolder{

        private FilaAuxiliosBinding mBinding;
        private Typeface mTypeface;

        public AuxiliosViewHolder(FilaAuxiliosBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mTypeface = Typeface.createFromAsset(mBinding.contentRow.getContext().getAssets(), Constants.PRIMARY_FONT);
        }

        public void bind(Auxilio auxilio) {
            mBinding.textCodigo.setTypeface(mTypeface);
            mBinding.textEstado.setTypeface(mTypeface);
            mBinding.textFecha.setTypeface(mTypeface);
            mBinding.textCodigo.setText(auxilio.getCodigo());
            mBinding.textEstado.setText(mBinding.contentRow.getContext().getString(R.string.estadoDescripcion, auxilio.getEstado()));
            Date date = new Date(Long.parseLong(auxilio.getFecha()));
            mBinding.textFecha.setText(Constants.DATE_FORMAT_SHOW.format(date));
        }
    }

}
