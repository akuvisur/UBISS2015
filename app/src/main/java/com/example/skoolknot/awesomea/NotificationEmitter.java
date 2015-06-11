package com.example.skoolknot.awesomea;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v4.app.NotificationCompat;
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
import android.widget.Toast;

/**
 * Created by skoolknot on 6/10/15.
 */
public class NotificationEmitter {
    private static NotificationCompat.Builder mBuilder;
    private static WindowManager windowManager;
    private static View popupView;

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

    public static void emitDummyToast(Context c) {
        Toast.makeText(c, "Hi there!", Toast.LENGTH_LONG).show();
    }

    public static void emitDummmyPopup(Context c) {
        windowManager = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);

        ImageButton okButton = new ImageButton(c);
        okButton.setImageResource(R.drawable.ok);

        ImageButton ignoreButton = new ImageButton(c);
        ignoreButton.setImageResource(R.drawable.ignore);

        LinearLayout ll = new LinearLayout(c);
        ll.addView(okButton);
        ll.addView(ignoreButton);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;

        windowManager.addView(ll, params);

    }
}
