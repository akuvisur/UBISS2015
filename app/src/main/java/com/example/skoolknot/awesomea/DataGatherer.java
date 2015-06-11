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

    public static HashMap<String, String> getAllValues() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("wifiState", wifiState.toString());
        result.put("internetState", internetState.toString());
        result.put("lastApp", lastApp);
        result.put("screenOn", screenOn.toString());
        result.put("screenLocked", screenLocked.toString());
        Calendar cal = Calendar.getInstance();

        result.put("hour", Integer.valueOf(cal.get(Calendar.HOUR_OF_DAY)).toString());
        result.put("day", Integer.valueOf(cal.get(Calendar.DAY_OF_WEEK)).toString());
        result.put("activity", activity.toString());

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
