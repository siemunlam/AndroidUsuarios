package com.siem.siemusuarios.model.app;

/**
 * Created by Lucas on 29/9/17.
 */

public class Sexo {

    private int mId;
    private String mDescripcion;

    public Sexo(int id, String descripcion){
        mId = id;
        mDescripcion = descripcion;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getDescripcion() {
        return mDescripcion;
    }

    public void setDescripcion(String descripcion) {
        mDescripcion = descripcion;
    }
}
