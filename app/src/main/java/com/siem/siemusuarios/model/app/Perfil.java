package com.siem.siemusuarios.model.app;

import android.content.Context;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.db.DBWrapper;

import java.io.Serializable;

/**
 * Created by Lucas on 25/9/17.
 */

public class Perfil extends Item implements Serializable{

    private static final String MASCULINO = "M";
    private static final String FEMENINO = "F";

    private Integer mId;
    private String mNombre;
    private String mApellido;
    private String mContacto;
    private String mSexo;
    private String mFechaNacimiento;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String nombre) {
        mNombre = nombre;
    }

    public String getApellido() {
        return mApellido;
    }

    public void setApellido(String apellido) {
        mApellido = apellido;
    }

    public String getContacto() {
        return mContacto;
    }

    public void setContacto(String contacto) {
        mContacto = contacto;
    }

    public String getSexo() {
        return mSexo;
    }

    public void setSexo(String sexo) {
        mSexo = sexo;
    }

    public String getFechaNacimiento() {
        return mFechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        mFechaNacimiento = fechaNacimiento;
    }

    public void save(Context context){
        if(mId != null)
            DBWrapper.updatePerfil(context, this);
        else
            DBWrapper.savePerfil(context, this);
    }

    public String getSexoApi(Context context) {
        if(getSexo().equals(context.getString(R.string.masculino)))
            return MASCULINO;
        else if(getSexo().equals(context.getString(R.string.femenino)))
            return FEMENINO;
        else
            return null;
    }
}
