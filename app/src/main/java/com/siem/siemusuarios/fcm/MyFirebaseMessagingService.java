package com.siem.siemusuarios.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.siem.siemusuarios.ConsultarAuxilioNotificationStrategy;
import com.siem.siemusuarios.R;
import com.siem.siemusuarios.db.DBWrapper;
import com.siem.siemusuarios.model.app.Auxilio;
import com.siem.siemusuarios.ui.activity.SplashActivity;
import com.siem.siemusuarios.utils.Constants;

import java.util.Date;
import java.util.Map;

/**
 * Created by Lucas on 20/9/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String KEY_NOTIFICATION_STRATEGY = "KEY_NOTIFICATION_STRATEGY";

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

        sendNotification(getString(R.string.auxilioChangeStatus, auxilio.getCodigo()), auxilio.getEstado());
    }

    private void sendNotification(String contentTitle, String messageBody) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra(KEY_NOTIFICATION_STRATEGY, new ConsultarAuxilioNotificationStrategy());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long[] vibrate = {1000, 1000, 1000, 1000};
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_notif_ambulance);
        mBuilder.setAutoCancel(true);
        mBuilder.setSound(defaultSoundUri);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setVibrate(vibrate);
        mBuilder.setContentTitle(contentTitle);
        if(messageBody != null)
            mBuilder.setContentText(messageBody);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(Constants.NOTIFICATION_ID, mBuilder.build());
    }

}
