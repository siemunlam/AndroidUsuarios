package com.siem.siemusuarios.adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.siem.siemusuarios.databinding.FilaPerfilesBinding;
import com.siem.siemusuarios.interfaces.SeleccionarContactoListener;
import com.siem.siemusuarios.model.app.Perfil;
import com.siem.siemusuarios.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> {

    private List<Perfil> mListDatos;
    private WeakReference<SeleccionarContactoListener> mListener;

    public ContactoAdapter(List<Perfil> datos, SeleccionarContactoListener listener){
        mListDatos = datos;
        mListener = new WeakReference<>(listener);
    }

    @Override
    public ContactoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FilaPerfilesBinding filaPerfilesBinding = FilaPerfilesBinding.inflate(layoutInflater, parent, false);
        return new ContactoViewHolder(filaPerfilesBinding);
    }

    @Override
    public void onBindViewHolder(final ContactoViewHolder holder, int position) {
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


    public class ContactoViewHolder extends RecyclerView.ViewHolder{

        private final FilaPerfilesBinding mBinding;
        private Typeface mTypeface;

        public ContactoViewHolder(FilaPerfilesBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mTypeface = Typeface.createFromAsset(mBinding.textNombreApellido.getContext().getAssets(), Constants.PRIMARY_FONT);
        }

        public void bind(final Perfil perfil) {
            mBinding.textNombreApellido.setText(String.format(Constants.FORMAT_NOMBRE_APELLIDO, perfil.getNombre(), perfil.getApellido()));
            mBinding.textNombreApellido.setTypeface(mTypeface);
            mBinding.fila.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null && mListener.get() != null){
                        mListener.get().contactoSeleccionado(perfil);
                    }
                }
            });
        }
    }

}
