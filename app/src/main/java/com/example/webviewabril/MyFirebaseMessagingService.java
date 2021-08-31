package com.example.webviewabril;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.nio.charset.MalformedInputException;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String LOGTAG = "android-fcm";
    private static final  String TAG = "MyFirebaseMsgService";
    private static final String MAIN = "MainActivity";

    //obtener el titulo y el cuerpo de la notificacion
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        if (remoteMessage.getNotification() != null){
            showNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }

    //con esta funcion obtenemos el token y lo enseñamos por consola
    @Override
    public void onNewToken(String token){
        Log.d(TAG, "Refreshed token: " + token); //token --> 3124jaksfj134....
    }

    // se obtiene el texto del titulo y del cuerpo de la notificacion
    private RemoteViews getCustomDesign(String title,
                                        String message){
        RemoteViews remoteViews = new RemoteViews(
                getApplicationContext().getPackageName(),
                R.layout.notification);
        remoteViews.setTextViewText(R.id.title, title);
        remoteViews.setTextViewText(R.id.message, message);
        remoteViews.setImageViewResource(R.id.icon,
                R.drawable.ic_check);
        return remoteViews;
    }

    //notificacion
    public void showNotification(String title,
                                 String message){
        Intent intent = new Intent(this, MainActivity.class);
        String channel_id = "notification_channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Log.d(LOGTAG, "NOTIFICACION RECIBIDA");
        Log.d(LOGTAG, "Titulo: " + title);
        Log.d(LOGTAG, "Texto: " + message);

        //muestra por consola si la notificacion se ha recibido, sale el titulo y el cuerpo de la notficacion
        NotificationCompat.Builder builder
                = new NotificationCompat.Builder(getApplicationContext(),
                channel_id)
                .setSmallIcon(R.drawable.ic_check) //icon
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000,
                        1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            builder = builder.setContent(getCustomDesign(title, message));
        }
        else{
            builder = builder.setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(R.drawable.ic_check);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel
                    = new NotificationChannel(
                            channel_id, "web_app",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(
                    notificationChannel);
        }

        notificationManager.notify(0, builder.build());
    }
}
