/*
 * Copyright (c) 2017. The Android Project Create By Hutcwp.
 */

package club.hutcwp.coolweather.util

import okhttp3.OkHttpClient
import okhttp3.Request


/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

object HttpUtil {

    fun sendOkHttpRequest(address: String, callback: okhttp3.Callback) {
        val client = OkHttpClient()
        val request = Request.Builder().url(address).build()
        client.newCall(request).enqueue(callback)
    }
}
