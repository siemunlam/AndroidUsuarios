package com.siem.siemusuarios.model;

/**
 * Created by Lucas on 22/9/17.
 */

public class Motivos {

    private String mDescripcion;

    public Motivos(String descripcion){
        mDescripcion = descripcion;
    }

    public String getDescripcion() {
        return mDescripcion;
    }

    public void setDescripcion(String descripcion) {
        mDescripcion = descripcion;
    }
}
