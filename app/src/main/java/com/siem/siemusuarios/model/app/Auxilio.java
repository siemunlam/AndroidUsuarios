package com.siem.siemusuarios.model.app;

import android.content.Context;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucas on 10/18/17.
 */

public class Auxilio implements Serializable {

    private static final String ORIGEN_MOBILE = "3";

    private Perfil mPerfil;
    private String mUbicacion;
    private String mReferencia;
    private String mLatitud;
    private String mLongitud;
    private HashMap<String, String> mMotivos;
    private String mOrigen;
    private String mObservaciones;

    public Auxilio(){
        mMotivos = new HashMap<>();
        mOrigen = ORIGEN_MOBILE;
    }

    public Perfil getPerfil() {
        return mPerfil;
    }

    public void setPerfil(Perfil perfil) {
        mPerfil = perfil;
    }

    public String getUbicacion() {
        return mUbicacion;
    }

    public void setUbicacion(String ubicacion) {
        mUbicacion = ubicacion;
    }

    public String getReferencia() {
        return mReferencia;
    }

    public void setReferencia(String referencia) {
        mReferencia = referencia;
    }

    public String getLatitud() {
        return mLatitud;
    }

    public void setLatitud(String latitud) {
        mLatitud = latitud;
    }

    public String getLongitud() {
        return mLongitud;
    }

    public void setLongitud(String longitud) {
        mLongitud = longitud;
    }

    public HashMap<String, String> getMotivos() {
        return mMotivos;
    }

    public void addMotivo(String key, String value) {
        mMotivos.put(key, value);
    }

    public void addMotivos(HashMap<String, String> motivos){
        for (Map.Entry<String, String> entry : motivos.entrySet()) {
            addMotivo(entry.getKey(), entry.getValue());
        }
    }

    public String getOrigen() {
        return mOrigen;
    }

    public String getObservaciones() {
        return mObservaciones;
    }

    public void setObservaciones(String observaciones) {
        mObservaciones = observaciones;
    }

    public String getNombre(String defaultStr) {
        if(getPerfil() != null)
            return getPerfil().getNombre() + " " + getPerfil().getApellido();
        else
            return defaultStr;
    }

    public String getContacto(String defaultStr) {
        if(getPerfil() != null)
            return getPerfil().getContacto();
        else
            return defaultStr;
    }

    public String getSexo(Context context, String defaultStr) {
        if(getPerfil() != null)
            return getPerfil().getSexoApi(context);
        else
            return defaultStr;
    }
}
