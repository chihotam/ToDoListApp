package com.todolistapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class TaskBroadcast extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "taskChannel")
                .setSmallIcon(R.drawable.plusicon)
                .setContentTitle(intent.getAction().split("&&")[0])
                .setContentText(intent.getAction().split("&&")[1])
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, builder.build());
    }
}
