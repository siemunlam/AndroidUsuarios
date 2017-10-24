package com.siem.siemusuarios.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.utils.Constants;

import java.util.Date;
import java.util.Map;

/**
 * Created by Lucas on 20/9/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("123456789", "Llego notificacion: " + remoteMessage.getData());
        Map<String, String> data = remoteMessage.getData();
        updateAuxilio(data);

    }

    private void updateAuxilio(Map<String, String> data) {
        Auxilio auxilio = new Auxilio();
        String estado = data.get(Constants.PUSH_STATUS);
        String timestamp = data.get(Constants.PUSH_TIMESTAMP);
        String codigo = data.get(Constants.PUSH_AUXILIO);
        if(data.containsKey(Constants.PUSH_ESTIMACION)){
            String estimacion = data.get(Constants.PUSH_ESTIMACION);
        }

        auxilio.setEstado(estado);
        auxilio.setCodigo(codigo);
        Date date;
        try{
            date = Constants.DATE_FORMAT_API.parse(timestamp);
        }catch(Exception e){
            date = new Date();
        }
        auxilio.setFecha(String.valueOf(date.getTime()));
        DBWrapper.updateAuxilio(this, auxilio);
    }

}
