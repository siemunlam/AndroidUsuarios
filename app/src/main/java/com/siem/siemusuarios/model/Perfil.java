package com.siem.siemusuarios.model;

/**
 * Created by Lucas on 25/9/17.
 */

public class Perfil {

    private String mNombre;
    private String mApellido;
    private String mSexo;
    private String mFechaNacimiento;

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
}
