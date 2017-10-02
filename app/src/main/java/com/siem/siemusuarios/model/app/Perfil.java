package com.siem.siemusuarios.model.app;

import android.content.Context;

import com.siem.siemusuarios.db.DBWrapper;

import java.io.Serializable;

/**
 * Created by Lucas on 25/9/17.
 */

public class Perfil implements Serializable{

    private Integer mId;
    private String mNombre;
    private String mApellido;
    private Integer mNroContacto;
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

    public Integer getNroContacto() {
        return mNroContacto;
    }

    public void setNroContacto(int nroContacto) {
        mNroContacto = nroContacto;
    }

    public void setNroContacto(String nroContacto){
        try{
            mNroContacto = Integer.parseInt(nroContacto);
        }catch (Exception e){
            mNroContacto = null;
        }
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

}
