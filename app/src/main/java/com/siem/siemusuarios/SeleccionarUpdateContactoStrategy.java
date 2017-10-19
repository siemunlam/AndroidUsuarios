package com.siem.siemusuarios;

import android.app.Activity;
import android.content.Intent;

import com.siem.siemusuarios.interfaces.SeleccionarContactoStrategy;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.model.app.Perfil;
import com.siem.siemusuarios.utils.Constants;

import java.io.Serializable;

/**
 * Created by lucas on 10/19/17.
 */

public class SeleccionarUpdateContactoStrategy implements SeleccionarContactoStrategy, Serializable {

    @Override
    public void contactoSeleccionado(Activity activity, Perfil perfil, Auxilio auxilio) {
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_PERFIL, perfil);
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }

}
