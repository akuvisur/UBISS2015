package com.example.skoolknot.awesomea;

import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by skoolknot on 6/10/15.
 */
public class DataGatherer {

    public static ArrayList<Integer> supportedActivities =
            new ArrayList<Integer>(Arrays.asList(0,1,2,3,7,8));

    public static ArrayList<String> usedApps = new ArrayList<String>();
    
    public static Integer wifiState = 0;
    public static Integer internetState = 0;
    public static String lastApp = "";
    public static Integer screenLocked = 0;
    public static Integer screenOn = 0;
    public static Integer activity = 3;

    public static HashMap<String, Integer> getAllValues() {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        result.put("wifiState", wifiState);
        result.put("internetState", internetState);
        result.put("lastApp", usedApps.indexOf(lastApp));
        result.put("screenOn", screenOn);
        result.put("screenLocked", screenLocked);
        Calendar cal = Calendar.getInstance();

        result.put("hour", cal.get(Calendar.HOUR_OF_DAY));
        result.put("day", cal.get(Calendar.DAY_OF_WEEK));
        result.put("activity", activity);

        return result;
    }

    public static void setLatestApp(String appName) {
        lastApp = appName;
        if (!usedApps.contains(appName)) {
            usedApps.add(appName);
        }
        Log.d("GATHERER", getAllValues().toString());
    }
}
