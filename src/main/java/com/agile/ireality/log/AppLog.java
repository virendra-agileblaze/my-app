package com.agile.ireality.log;

import android.util.Log;

/**
 * Created by sarath on 4/11/15.
 *
 * Custom log for AyeAuto Can control the log levels easily.
 *
 */
public class AppLog {
    public static final int  ERROR  = 4;
    public static final int  WARNING  = 3;
    public static final int  DEBUG  = 2;
    public static final int  INFO  = 1;
    public static final int  VERBOSE  = 0;

    public static final int logLevel = INFO;


    /**
     * Log info
     * @param tag
     * @param message
     */
    public static void i(String tag, String message){
        if(logLevel <= INFO)
            Log.i(tag, message);
    }

    /**
     * Log debug
     * @param tag
     * @param message
     */
    public static void d(String tag, String message){
        if(logLevel <= DEBUG)
            Log.d(tag, message);
    }

    /**
     * Log warning
     * @param tag
     * @param message
     */
    public static void w(String tag, String message){
        if(logLevel <= WARNING)
            Log.w(tag, message);
    }

    /**
     * Log error
     * @param tag
     * @param message
     */
    public static void e(String tag, String message){
        if(logLevel <= ERROR)
            Log.e(tag, message);
    }

    /**
     * Log error
     * @param tag
     * @param message
     * @param ex
     */
    public static void e(String tag, String message, Throwable ex){
        if(logLevel <= ERROR)
            Log.e(tag, message,ex);
    }



    /**
     * Log verbose
     * @param tag
     * @param message
     */
    public static void v(String tag, String message){
        if(logLevel <= VERBOSE)
            Log.v(tag, message);
    }


}
