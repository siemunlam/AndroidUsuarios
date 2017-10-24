package com.siem.siemusuarios.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.siem.siemusuarios.utils.Constants;

import java.util.Map;

/**
 * Created by Lucas on 20/9/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("123456789", "Llego notificacion: " + remoteMessage.getData());
        Map<String, String> data = remoteMessage.getData();
        String estado = data.get(Constants.KEY_STATUS);
        String timestamp = data.get(Constants.KEY_TIMESTAMP);
        if(data.containsKey(Constants.KEY_ESTIMACION)){
            String estimacion = data.get(Constants.KEY_ESTIMACION);
        }
    }

}
