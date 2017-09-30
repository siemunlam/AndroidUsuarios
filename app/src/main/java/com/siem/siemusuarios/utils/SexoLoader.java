package com.siem.siemusuarios.utils;

import android.content.Context;

import com.siem.siemusuarios.R;
import com.siem.siemusuarios.model.app.Sexo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 29/9/17.
 */

public class SexoLoader {

    public static final int ID_MASCULINO = 1;
    public static final int ID_FEMENINO = 2;

    public static List<Sexo> getListSexo(Context context){
        List<Sexo> listSexo = new ArrayList<>();
        Sexo masculino = new Sexo(ID_MASCULINO, context.getString(R.string.masculino));
        Sexo femenino = new Sexo(ID_FEMENINO, context.getString(R.string.femenino));

        listSexo.add(masculino);
        listSexo.add(femenino);

        return listSexo;
    }

}
