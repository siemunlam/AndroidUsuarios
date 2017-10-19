package com.siem.siemusuarios.interfaces;

import android.app.Activity;

import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.model.app.Perfil;

/**
 * Created by lucas on 10/19/17.
 */

public interface SeleccionarContactoStrategy {
    void contactoSeleccionado(Activity activity, Perfil perfil, Auxilio auxilio);
}
