package com.siem.siemusuarios.model.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 22/9/17.
 */

public class MotivoAjuste {

    private String mDescripcion;
    private List<String> mListOptions;

    public MotivoAjuste(String descripcion){
        mDescripcion = descripcion;
        mListOptions = new ArrayList<>();
    }

    public String getDescripcion() {
        return mDescripcion;
    }

    public void setDescripcion(String descripcion) {
        mDescripcion = descripcion;
    }

    public List<String> getListOptions() {
        return mListOptions;
    }

    public void setListOptions(List<String> listOptions) {
        mListOptions = listOptions;
    }

    public void addOption(String option){
        mListOptions.add(option);
    }

}
