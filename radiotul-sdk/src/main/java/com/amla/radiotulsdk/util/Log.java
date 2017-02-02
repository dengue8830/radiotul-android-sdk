package com.amla.radiotulsdk.util;

import com.amla.radiotulsdk.RadiotulSdk;
import com.android.volley.VolleyError;

/**
 * Created by dengue8830 on 2/2/17.
 */

public class Log {
    static final boolean LOG = RadiotulSdk.LOG_ENABLED;

    public static void i(String tag, String string) {
        if (LOG) android.util.Log.i(tag, string);
    }
    public static void e(String tag, String string) {
        if (LOG) android.util.Log.e(tag, string);
    }
    public static void e(String tag, VolleyError error){
        if (LOG) android.util.Log.e(tag, error.getMessage() + "");//a veces message es null y no debe ser null el parametro
    }
    public static void e(String tag, Exception e) {
        if (LOG) android.util.Log.e(tag, e.getMessage()+"");//getCause().toString() da nullpointer en algunos casos D:
    }
    public static void d(String tag, String string) {
        if (LOG) android.util.Log.d(tag, string);
    }
    public static void v(String tag, String string) {
        if (LOG) android.util.Log.v(tag, string);
    }
    public static void w(String tag, String string) {
        if (LOG) android.util.Log.w(tag, string);
    }
}
