package com.siem.siemusuarios;

import android.app.Activity;
import android.content.Intent;

import com.siem.siemusuarios.interfaces.SeleccionarContactoStrategy;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.model.app.Perfil;
import com.siem.siemusuarios.ui.activity.GenerarAuxilioActivity;
import com.siem.siemusuarios.utils.Constants;
import com.siem.siemusuarios.utils.Utils;

import java.io.Serializable;

import static com.siem.siemusuarios.utils.Constants.KEY_AUXILIO_GENERADO;

/**
 * Created by lucas on 10/19/17.
 */

public class SeleccionarNewContactoStrategy implements SeleccionarContactoStrategy, Serializable {

    @Override
    public void contactoSeleccionado(Activity activity, Perfil perfil, Auxilio auxilio) {
        Intent intent = new Intent(activity, GenerarAuxilioActivity.class);
        auxilio.setPerfil(perfil);
        intent.putExtra(Constants.KEY_AUXILIO, auxilio);
        Utils.startActivityWithTransitionForResult(activity, intent, KEY_AUXILIO_GENERADO);
    }

}
