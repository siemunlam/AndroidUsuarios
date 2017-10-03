package com.siem.siemusuarios.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siem.siemusuarios.databinding.FilaPerfilesBinding;
import com.siem.siemusuarios.interfaces.SwipePerfilDeleteListener;
import com.siem.siemusuarios.model.app.Perfil;
import com.siem.siemusuarios.ui.activity.AgregarPerfilActivity;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.Utils;
import com.siem.siemusuarios.utils.swipe.ItemTouchHelperAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

public class PerfilesAdapter extends RecyclerView.Adapter<PerfilesAdapter.PerfilesViewHolder> implements
        ItemTouchHelperAdapter {

    private static final String FORMAT_NOMBRE_APELLIDO = "%1$s %2$s";

    private WeakReference<Activity> mActivity;
    private WeakReference<SwipePerfilDeleteListener> mListener;
    private List<Perfil> mListDatos;

    //TODO: Eliminar perfiles
    public PerfilesAdapter(Activity activity, List<Perfil> datos, SwipePerfilDeleteListener swipePerfilDeleteListener){
        mListDatos = datos;
        mActivity = new WeakReference<>(activity);
        mListener = new WeakReference<>(swipePerfilDeleteListener);
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

    /**
     * ItemTouchHelperAdapter
     */
    @Override
    public void onItemDismiss(int position) {
        Perfil perfil = mListDatos.remove(position);
        notifyItemRemoved(position);
        if(mListener != null && mListener.get() != null)
            mListener.get().deletePerfil(perfil);
    }

    public class PerfilesViewHolder extends RecyclerView.ViewHolder{

        private final FilaPerfilesBinding mBinding;

        public PerfilesViewHolder(FilaPerfilesBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(final Perfil perfil) {
            mBinding.textNombreApellido.setText(String.format(FORMAT_NOMBRE_APELLIDO, perfil.getNombre(), perfil.getApellido()));
            mBinding.fila.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mActivity != null && mActivity.get() != null){
                        Intent intent = new Intent(mActivity.get(), AgregarPerfilActivity.class);
                        intent.putExtra(Constants.KEY_PERFIL, perfil);
                        Utils.startActivityWithTransition(mActivity.get(), intent);
                    }
                }
            });
        }
    }

}
