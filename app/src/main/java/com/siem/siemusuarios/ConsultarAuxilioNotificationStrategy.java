package com.siem.siemusuarios;

import android.app.Activity;
import android.content.Intent;

import com.siem.siemusuarios.interfaces.NotificationStrategy;
import com.siem.siemusuarios.ui.activity.ConsultarAuxilioActivity;
import com.siem.siemusuarios.utils.Utils;

import java.io.Serializable;

/**
 * Created by lucas on 10/24/17.
 */

public class ConsultarAuxilioNotificationStrategy implements NotificationStrategy, Serializable {
    @Override
    public void run(Activity activity) {
        Utils.startActivityWithTransition(activity, new Intent(activity, ConsultarAuxilioActivity.class));
    }
}
