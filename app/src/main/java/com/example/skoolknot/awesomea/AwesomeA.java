package com.example.skoolknot.awesomea;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.Communication;
import com.aware.Network;
import com.aware.Screen;
import com.aware.providers.Applications_Provider;
import com.aware.providers.Communication_Provider;

import java.util.HashMap;


public class AwesomeA extends ActionBarActivity {

    private static ApplicationsObserver appObs;

    public static EventDBHelper edh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awesome);

        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_APPLICATIONS, true); // APPLICATIONS
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_CALLS, true); // CALLS
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_MESSAGES, true); // MESSAGES
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_NETWORK_EVENTS, true); // NETWORK
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_SCREEN, true); // SCREEN
        Aware.startPlugin(getApplicationContext(), "com.aware.plugin.google.activity_recognition");

        appObs = new ApplicationsObserver(new Handler());
        getContentResolver().registerContentObserver(
                Applications_Provider.Applications_Foreground.CONTENT_URI,
                true,
                appObs
        );

        IntentFilter netFilter = new IntentFilter();
        netFilter.addAction(Network.ACTION_AWARE_INTERNET_AVAILABLE);
        netFilter.addAction(Network.ACTION_AWARE_INTERNET_UNAVAILABLE);
        netFilter.addAction(Network.ACTION_AWARE_WIFI_OFF);
        netFilter.addAction(Network.ACTION_AWARE_WIFI_ON);
        registerReceiver(nl, netFilter);

        IntentFilter screenFilter = new IntentFilter();
        screenFilter.addAction(Screen.ACTION_AWARE_SCREEN_LOCKED);
        screenFilter.addAction(Screen.ACTION_AWARE_SCREEN_UNLOCKED);
        screenFilter.addAction(Screen.ACTION_AWARE_SCREEN_OFF);
        screenFilter.addAction(Screen.ACTION_AWARE_SCREEN_ON);
        registerReceiver(sl, screenFilter);

        IntentFilter actFilter = new IntentFilter();
        actFilter.addAction("ACTION_AWARE_GOOGLE_ACTIVITY_RECOGNITION");
        registerReceiver(al, actFilter);

        sendBroadcast(new Intent(Aware.ACTION_AWARE_REFRESH));
    }

    @Override
    public void onResume() {
        super.onResume();
        edh = new EventDBHelper(getApplicationContext());
        Log.d("DB", getApplicationContext().getDatabasePath(EventDBHelper.DATABASE_NAME).toString());
    }

    public void createNotification(View view) {
        NotificationEmitter.emitDummyNotification(getApplicationContext());
    }

    public void createToast(View view) {
        NotificationEmitter.emitDummyToast(getApplicationContext());
    }

    public void createPopup(View view) {
        NotificationEmitter.emitDummmyPopup(getApplicationContext());
    }

    // Listener for network events
    private static NetworkListener nl = new NetworkListener();
    public static class NetworkListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newstate = intent.getAction().toString();
            if (newstate.equals(Network.ACTION_AWARE_INTERNET_AVAILABLE)) {
                DataGatherer.internetState = 0;
            }
            else if (newstate.equals(Network.ACTION_AWARE_INTERNET_UNAVAILABLE)) {
                DataGatherer.internetState = 1;
            }
            else if (newstate.equals(Network.ACTION_AWARE_WIFI_OFF)) {
                DataGatherer.wifiState = 0;
            }
            else if (newstate.equals(Network.ACTION_AWARE_WIFI_ON)) {
                DataGatherer.wifiState = 1;
            }
            Log.d("NETWORK", intent.getAction().toString());
        }
    }

    // Listener for screen events
    private static ScreenListener sl = new ScreenListener();
    public static class ScreenListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newstate = intent.getAction().toString();
            if (newstate.equals(Screen.ACTION_AWARE_SCREEN_LOCKED)) {
                DataGatherer.screenLocked = 1;
            }
            else if (newstate.equals(Screen.ACTION_AWARE_SCREEN_UNLOCKED)) {
                DataGatherer.screenLocked = 0;
            }
            else if (newstate.equals(Screen.ACTION_AWARE_SCREEN_ON)) {
                DataGatherer.screenOn = 1;
            }
            else if (newstate.equals(Screen.ACTION_AWARE_SCREEN_OFF)) {
                DataGatherer.screenOn = 0;
            }
            Log.d("SCREEN", intent.getAction());
        }
    }

    // Listener for screen events
    private static ActivityListener al = new ActivityListener();
    public static class ActivityListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (DataGatherer.supportedActivities.contains(intent.getIntExtra("activity", -1)) &&
                    (intent.getIntExtra("confidence", 0) > 50)) {
                DataGatherer.activity = intent.getIntExtra("activity", -1);
            }
            Log.d("ACTIVITY", "" + intent.getIntExtra("activity", -1));
        }
    }


    // Applications observer class
    public class ApplicationsObserver extends ContentObserver {
        public ApplicationsObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            Cursor raw_data = getContentResolver().query(
                    Applications_Provider.Applications_Foreground.CONTENT_URI,
                    null,
                    null,
                    null,
                    "timestamp DESC LIMIT 1"
            );

            if(raw_data != null && raw_data.moveToFirst()) {
                String appName = raw_data.getString(raw_data.getColumnIndex(Applications_Provider.Applications_Foreground.APPLICATION_NAME));
                Log.d("APPLOG", "App name: " + appName);
                DataGatherer.setLatestApp(appName);
            }

            if(raw_data != null && ! raw_data.isClosed())
                raw_data.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_awesome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getContentResolver().unregisterContentObserver(appObs);
        unregisterReceiver(nl);
        unregisterReceiver(al);
        unregisterReceiver(sl);

        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_APPLICATIONS, false); // APPLICATIONS
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_CALLS, false); // CALLS
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_MESSAGES, false); // MESSAGES
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_NETWORK_EVENTS, false); // NETWORK
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_SCREEN, false); // SCREEN
        Aware.stopPlugin(getApplicationContext(), "com.aware.plugin.google.activity_recognition");

        sendBroadcast(new Intent(Aware.ACTION_AWARE_REFRESH));
    }

    public static void getData() {
        // return all the data from database
        SQLiteDatabase db = edh.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                EventDBHelper.EventEntry._ID,
                EventDBHelper.EventEntry.COLUMN_NAME_APP_NAME,
                EventDBHelper.EventEntry.COLUMN_NAME_RESPONSE,
                EventDBHelper.EventEntry.COLUMN_NAME_WIFI_STATE,
                EventDBHelper.EventEntry.COLUMN_NAME_INTERNET_STATE,
                EventDBHelper.EventEntry.COLUMN_NAME_LAST_APP,
                EventDBHelper.EventEntry.COLUMN_NAME_SCREEN_ON,
                EventDBHelper.EventEntry.COLUMN_NAME_SCREEN_LOCKED,
                EventDBHelper.EventEntry.COLUMN_NAME_HOUR,
                EventDBHelper.EventEntry.COLUMN_NAME_DAY,
                EventDBHelper.EventEntry.COLUMN_NAME_ACTIVITY
        };

        /*
        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                EventDBHelper.EventEntry.COLUMN_NAME_UPDATED + " DESC";
        */
        Cursor c = db.query(
                EventDBHelper.EventEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                " * ",                                // The columns for the WHERE clause
                null,                                      // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                     // The sort order
        );

        if(c != null && c.moveToFirst()) {
            while (c.moveToNext()) {
                // do things here
            }
        }

        if(c != null && ! c.isClosed())
            c.close();
    }

    public static void storeEvent(HashMap<String, Integer> newValues, String appName) {
        SQLiteDatabase db = edh.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        // hashmap keys are also the names of the database columns
        ContentValues values = new ContentValues();
        for (String key : newValues.keySet()) {
            values.put(key, newValues.get(key));
        }
        values.put(EventDBHelper.EventEntry.COLUMN_NAME_APP_NAME, appName);
        // Insert the new row, returning the primary key value of the new row
        db.insert(
                EventDBHelper.EventEntry.TABLE_NAME,
                null,
                values);
    }
}
