package com.example.skoolknot.awesomea;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by skoolknot on 6/10/15.
 */
public class NotificationEmitter {
    private static NotificationCompat.Builder mBuilder;

    public static void emitDummyNotification(Context c) {
        mBuilder =
                new NotificationCompat.Builder(c)
                        .setSmallIcon(R.drawable.alert)
                        .setContentTitle("New notification")
                        .setContentText("Hi there!");
        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
