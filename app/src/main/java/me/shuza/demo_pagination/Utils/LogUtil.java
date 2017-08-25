package me.shuza.demo_pagination.Utils;

import android.util.Log;

/**
 * Created by Boka on 23-Aug-17.
 */

public class LogUtil {

    public static void printLogMessage(String className, String tag, String message) {
        Log.d(className, tag + "    ====//    " + message);
    }
}
