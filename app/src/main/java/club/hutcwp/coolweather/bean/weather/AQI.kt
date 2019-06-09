/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.bean.weather

/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class AQI {


    /**
     * city : {"aqi":"42","pm10":"42","pm25":"29","qlty":"优"}
     */

    var city: CityBean? = null

    class CityBean {
        /**
         * aqi : 42
         * pm10 : 42
         * pm25 : 29
         * qlty : 优
         */

        var aqi: String? = null
        var pm10: String? = null
        var pm25: String? = null
        var qlty: String? = null

        override fun toString(): String {
            return "CityBean(aqi=$aqi, pm10=$pm10, pm25=$pm25, qlty=$qlty)"
        }
    }

    override fun toString(): String {
        return "AQI(city=$city)"
    }

}
