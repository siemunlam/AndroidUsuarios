package com.siem.siemusuarios.model.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 22/9/17.
 */

public class MotivoPrecategorizacion {

    private String mDescripcion;
    private List<String> mListOptions;
    private Integer mPositionOptionSelected;

    public MotivoPrecategorizacion(String descripcion){
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

    public Integer getPositionOptionSelected() {
        return mPositionOptionSelected;
    }

    public void setPositionOptionSelected(int positionOptionSelected) {
        mPositionOptionSelected = positionOptionSelected;
    }

    public void addOption(String option){
        mListOptions.add(option);
    }

}
