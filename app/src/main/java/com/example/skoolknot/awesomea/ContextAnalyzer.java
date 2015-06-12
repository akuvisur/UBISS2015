package com.example.skoolknot.awesomea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by skoolknot on 6/10/15.
 */
public class ContextAnalyzer {
    public static final int IGNORE = 0;
    // Toasts cannot be a) clicked or b) drawn outside activities
    // public static final int TOAST = 1;
    public static final int NOTIFICATION = 1;
    public static final int POPUP = 2;
    // cant draw to lock_screen outside of normal notifications
    //public static final int LOCK_SCREEN_MSG = 4;

    // oldValues are the history data from the database
    // curEvent are the values for the current event
    public static int analyze(String appName, ArrayList<ArrayList<String>> oldValues, HashMap<String, String> curEvent) {
        // returns the notification type for the app 'AppName'
        Random r = new Random();
        return r.nextInt(3);
    }
}
