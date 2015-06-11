package com.example.skoolknot.awesomea;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.undobar.UndoBarController;

import java.nio.channels.DatagramChannel;

/**
 * Created by skoolknot on 6/10/15.
 */
public class NotificationEmitter {
    private static NotificationCompat.Builder mBuilder;
    private static WindowManager windowManager;
    private static View popupView;
    private static boolean popupHasView = false;

    public static void createNotification(Context c, String appName, View v, Drawable appImage) {
        //int type = ContextAnalyzer.analyze(appName);
        int type = ContextAnalyzer.POPUP;
        switch(type) {
            case ContextAnalyzer.IGNORE:
                Log.d("CREATOR", "Notification ignored");
                break;
            /*
            case ContextAnalyzer.TOAST:
                Log.d("CREATOR", "Creating a toast");
                emitToast(c, appName, appImage, v);
                break;
            */
            case ContextAnalyzer.NOTIFICATION:
                Log.d("CREATOR", "Creating a notification");
                emitNotification(c, appName, appImage, v);
                break;
            case ContextAnalyzer.POPUP:
                emitPopup(c, appName, appImage, v);
                Log.d("CREATOR", "Creating a popup");
                break;
            /*
            case ContextAnalyzer.LOCK_SCREEN_MSG:
                Log.d("CREATOR", "Creating a lockscreenmsg");
                emitLockMsg(c, appName, appImage, v);
                break;
            */
        }
    }

    private static void emitToast(Context c, String appName, Drawable appImage, View v) {
        UndoBarController.UndoBar ub = new UndoBarController.UndoBar(AwesomeA.getThis());

        //UndoBarController ubc = new UndoBarController(v, AwesomeA.getThis());
        ub.duration(5000);
        ub.message("This is an undobar");
        ub.show();
    }

    private static void emitLockMsg(Context c, String appName, Drawable appImage, View v) {
        /*
        AlertDialog alertDialog = new AlertDialog.Builder(c).create();
        alertDialog.getWindow().setType(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        alertDialog.show();
        */
    }

    private static void emitPopup(Context c, String appName, Drawable appImage, final View v) {
        windowManager = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.OPAQUE);

        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;

        windowManager.addView(v, params);
        popupView = v;
        popupHasView = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (popupHasView) {
                    windowManager.removeView(popupView);
                    popupHasView = false;
                }
            }
        }, 10000);
    }

    private static void emitNotification(Context c, String appName, Drawable appImage, View v) {
        int imageId;

        Intent oki = new Intent(c, notiClicked.class);
        oki.setAction("notifications_pressed");
        oki.putExtra("response", 1);
        oki.putExtra("appName", appName);

        Intent iggy = new Intent(c, notiClicked.class);
        iggy.setAction("notifiations_pressed");
        iggy.putExtra("response", 0);
        iggy.putExtra("appName", appName);

        PendingIntent pOk = PendingIntent.getService(c, 0, oki, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pIg = PendingIntent.getService(c, 0, iggy, PendingIntent.FLAG_UPDATE_CURRENT);

        if (AwesomeA.debugApps.contains(appName)) {
            imageId = AwesomeA.debugIcons.get(AwesomeA.debugApps.indexOf(appName));
        }
        else {
            imageId = R.drawable.alert;
        }

        mBuilder =
                new NotificationCompat.Builder(c)
                        .setSmallIcon(imageId)
                        .setContentTitle(appName)
                        .setContentText("This app just sent you a notification")
                        .setContentIntent(pOk)
                        .addAction(R.drawable.ok, "Ok", pOk)
                        .addAction(R.drawable.ignore, "Ignore", pIg)
                        .setAutoCancel(true);

        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(AwesomeA.debugApps.indexOf(appName), mBuilder.build());
    }

    public static Drawable generateDummyIcon(int n) {
        return AwesomeA.c.getResources().getDrawable(AwesomeA.debugIcons.get(n));
    }

    public static View generateDummyView(final int n) {
        LayoutInflater inflater = (LayoutInflater) AwesomeA.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.notification, null);

        ImageView appImg = (ImageView) layout.findViewById(R.id.appImage);
        TextView appName = (TextView) layout.findViewById(R.id.appName);
        ImageButton okb = (ImageButton) layout.findViewById(R.id.okButton);
        ImageButton igb = (ImageButton) layout.findViewById(R.id.ignoreButton);

        appImg.setImageResource(AwesomeA.debugIcons.get(n));

        okb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK", "clicked ok");
                AwesomeA.storeEvent(DataGatherer.getAllValues(), AwesomeA.debugApps.get(n), 1);
                if (popupHasView) {
                    windowManager.removeView(popupView);
                    popupHasView = false;
                }
            }
        });

        igb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK", "clicked ignore");
                AwesomeA.storeEvent(DataGatherer.getAllValues(), AwesomeA.debugApps.get(n), 0);
                if (popupHasView) {
                    windowManager.removeView(popupView);
                    popupHasView = false;
                }
            }
        });
        appName.setText(AwesomeA.debugApps.get(n));
        okb.setImageResource(R.drawable.ok);
        igb.setImageResource(R.drawable.ignore);
        return layout;
    }

    public static class notiClicked extends IntentService {
        public notiClicked() {
            super("lkjdlkj");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            Log.d("INTENT", "Clicked notification + " + intent.getIntExtra("response", -1));
            NotificationManager mNotifyMgr =
                    (NotificationManager) AwesomeA.c.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotifyMgr.cancel(AwesomeA.debugApps.indexOf(intent.getStringExtra("appName")));
            if (intent.getIntExtra("response", 0) == 0)
                AwesomeA.storeEvent(DataGatherer.getAllValues(), intent.getStringExtra("appname"), 0);
            else
                AwesomeA.storeEvent(DataGatherer.getAllValues(), intent.getStringExtra("appname"), 1);
        }
    }

    public class UndoList implements UndoBarController.UndoListener {
        public void onUndo(Parcelable token) {
            Log.d("UNDO", "clicked somewhere??");
        }
    }

}
