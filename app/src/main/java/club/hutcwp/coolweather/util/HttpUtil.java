/*
 * Copyright (c) 2017. The Android Project Create By Hutcwp.
 */

package club.hutcwp.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class HttpUtil {

    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);


    }

}
