package com.example.skoolknot.awesomea;

/**
 * Created by skoolknot on 6/10/15.
 */
public class ContextAnalyzer {
    public static final int IGNORE = 0;
    public static final int TOAST = 1;
    public static final int NOTIFICATION = 2;
    public static final int POPUP = 3;
    public static final int LOCK_SCREEN_MSG = 4;

    public static int analyze(String appName) {
        // returns the notification type for the app 'AppName'
        return ContextAnalyzer.POPUP;
    }
}
