package com.siem.siemusuarios.model;

/**
 * Created by Lucas on 22/9/17.
 */

public class Motivo {

    private String mDescripcion;

    public Motivo(String descripcion){
        mDescripcion = descripcion;
    }

    public String getDescripcion() {
        return mDescripcion;
    }

    public void setDescripcion(String descripcion) {
        mDescripcion = descripcion;
    }
}
