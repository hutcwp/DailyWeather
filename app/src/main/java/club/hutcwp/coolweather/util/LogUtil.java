/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.util;

import android.util.Log;

/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class LogUtil {

    /**
     * Log工具
     */

    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;

    public static int LEVEL = VERBOSE;

    // 调用Log.v()方法打印日志
    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            /**
             * 加了一个日志信息值的判断，为了防止参数msg的值为null情况的出现
             */
            if (msg != null) {
                Log.v(tag, msg);
            } else {
                Log.v(tag, "msg is NULL");
            }
        }
    }

    // 调用Log.d()方法打印日志
    public static void d(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            if (msg != null) {
                Log.d(tag, msg);
            } else {
                Log.d(tag, "msg is NULL");
            }
        }
    }

    // 调用Log.i()方法打印日志
    public static void i(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            if (msg != null) {
                Log.i(tag, msg);
            } else {
                Log.i(tag, "msg is NULL");
            }
        }
    }

    // 调用Log.w()方法打印日志
    public static void w(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            if (msg != null) {
                Log.w(tag, msg);
            } else {
                Log.w(tag, "msg is NULL");
            }
        }
    }

    // 调用Log.e()方法打印日志
    public static void e(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            if (msg != null) {
                Log.e(tag, msg);
            } else {
                Log.e(tag, "msg is NULL");
            }
        }
    }
}
