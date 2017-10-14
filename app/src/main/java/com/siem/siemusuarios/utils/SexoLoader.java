package com.siem.siemusuarios.utils;

import android.content.Context;

import com.siem.siemusuarios.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 29/9/17.
 */

public class SexoLoader {

    public static List<String> getListSexo(Context context){
        List<String> listSexo = new ArrayList<>();
        listSexo.add(context.getString(R.string.masculino));
        listSexo.add(context.getString(R.string.femenino));

        return listSexo;
    }

}
