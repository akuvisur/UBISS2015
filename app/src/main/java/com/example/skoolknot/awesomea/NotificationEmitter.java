package com.example.skoolknot.awesomea;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by skoolknot on 6/10/15.
 */
public class NotificationEmitter {
    private static NotificationCompat.Builder mBuilder;
    private static WindowManager windowManager;
    private static View popupView;

    public static void createNotification(Context c, String appName, View v, Drawable appImage) {
        int type = ContextAnalyzer.analyze(appName);
        switch(type) {
            case ContextAnalyzer.IGNORE:
                Log.d("CREATOR", "Notifiation ignored");
            case ContextAnalyzer.TOAST:
                Log.d("CREATOR", "Creating a toast");
                emitToast(c, appName, appImage, v);
            case ContextAnalyzer.NOTIFICATION:
                Log.d("CREATOR", "Creating a notification");
                emitNotification(c, appName, appImage, v);
            case ContextAnalyzer.POPUP:
                emitPopup(c, appName, appImage, v);
                Log.d("CREATOR", "Creating a popup");
            case ContextAnalyzer.LOCK_SCREEN_MSG:
                Log.d("CREATOR", "Creating a lockscreenmsg");
                emitLockMsg(c, appName, appImage, v);
        }
    }

    private static void emitToast(Context c, String appName, Drawable appImage, View v) {
        // emit a custom notification
    }

    private static void emitLockMsg(Context c, String appName, Drawable appImage, View v) {
        AlertDialog alertDialog = new AlertDialog.Builder(c).create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        alertDialog.show();
    }

    private static void emitPopup(Context c, String appName, Drawable appImage, View v) {
        // emit a popup
    }

    private static void emitNotification(Context c, String appName, Drawable appImage, View v) {
        // emit a notification
    }

    public static void emitDummyNotification(Context c) {

        Intent okIntent = new Intent(c, null);
        okIntent.setAction("notifications_pressed");
        okIntent.putExtra("notifications_accepted", "dummy_app");

        PendingIntent pOkIntent = PendingIntent.getService(c, 0, okIntent, 0);

        Intent igIntent = new Intent(c, null);
        igIntent.setAction("notifications_pressed");
        igIntent.putExtra("notifications_ignored", "dummy_app");

        PendingIntent pIgIntent = PendingIntent.getService(c, 0, igIntent, 0);

        mBuilder =
                new NotificationCompat.Builder(c)
                        .setSmallIcon(R.drawable.alert)
                        .setContentTitle("New notification")
                        .setContentText("Hi there!")
                        .addAction(R.drawable.ok, "Ok", pOkIntent)
                        .addAction(R.drawable.ignore, "Ignore", pIgIntent);

        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(0, mBuilder.build());
    }

    public static void emitDummyToast(Context c) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.notification, null);

        TextView appName = (TextView) layout.findViewById(R.id.appName);
        ImageButton okb = (ImageButton) layout.findViewById(R.id.okButton);
        ImageButton igb = (ImageButton) layout.findViewById(R.id.ignoreButton);

        appName.setText("Dummy application");
        okb.setImageResource(R.drawable.ok);
        igb.setImageResource(R.drawable.ignore);

        Toast toast = new Toast(c.getApplicationContext());
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 360);
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static void emitDummmyPopup(Context c) {
        windowManager = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.notification, null);

        TextView appName = (TextView) layout.findViewById(R.id.appName);
        ImageButton okb = (ImageButton) layout.findViewById(R.id.okButton);
        ImageButton igb = (ImageButton) layout.findViewById(R.id.ignoreButton);

        appName.setText("Dummy application");
        okb.setImageResource(R.drawable.ok);
        igb.setImageResource(R.drawable.ignore);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.OPAQUE);

        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;

        windowManager.addView(layout, params);
    }

}
