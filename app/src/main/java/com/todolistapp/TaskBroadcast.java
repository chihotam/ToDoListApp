package com.todolistapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;


public class TaskBroadcast extends BroadcastReceiver
{
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intentreturn = new Intent(context, MainActivity.class);
        intentreturn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pending = PendingIntent.getActivity(context, 100, intentreturn, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context, "taskChannel")
                .setSmallIcon(R.drawable.plusicon)
                .setContentTitle(intent.getAction().split("&&")[0])
                .setContentText(intent.getAction().split("&&")[1])
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pending);


        notificationManager.notify(100, builder.build());
    }
}
