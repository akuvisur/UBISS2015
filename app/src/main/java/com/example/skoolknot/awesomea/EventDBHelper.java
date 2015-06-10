package com.example.skoolknot.awesomea;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by skoolknot on 6/10/15.
 */
public class EventDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EventDB.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String NUM_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + EventEntry.TABLE_NAME + " (" +
                    EventEntry._ID + " INTEGER PRIMARY KEY," +
                    EventEntry.COLUMN_NAME_APP_NAME + NUM_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_RESPONSE + NUM_TYPE + " DEFAULT 0 "+ COMMA_SEP +
                    EventEntry.COLUMN_NAME_WIFI_STATE + NUM_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_INTERNET_STATE + NUM_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_LAST_APP + NUM_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_SCREEN_ON + NUM_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_SCREEN_LOCKED + NUM_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_HOUR + NUM_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_DAY + NUM_TYPE + COMMA_SEP +
                    EventEntry.COLUMN_NAME_ACTIVITY + NUM_TYPE + COMMA_SEP + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EventEntry.TABLE_NAME;


    public EventDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "events";

        public static final String COLUMN_NAME_APP_NAME = "appName";
        public static final String COLUMN_NAME_RESPONSE = "response";


        public static final String COLUMN_NAME_WIFI_STATE = "wifiState";
        public static final String COLUMN_NAME_INTERNET_STATE = "internetState";
        public static final String COLUMN_NAME_LAST_APP = "lastApp";
        public static final String COLUMN_NAME_SCREEN_ON = "screenOn";
        public static final String COLUMN_NAME_SCREEN_LOCKED = "screenLocked";
        public static final String COLUMN_NAME_HOUR = "hour";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_ACTIVITY = "activity";
    }
}