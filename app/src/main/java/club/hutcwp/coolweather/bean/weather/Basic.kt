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

class Basic {


    /**
     * city : 株洲
     * cnty : 中国
     * id : CN101250301
     * lat : 27.835806
     * lon : 113.151737
     * update : {"loc":"2017-04-09 14:51","utc":"2017-04-09 06:51"}
     */

    var city: String? = null
    var cnty: String? = null
    var id: String? = null
    var lat: String? = null
    var lon: String? = null
    var update: UpdateBean? = null

    class UpdateBean {
        /**
         * loc : 2017-04-09 14:51
         * utc : 2017-04-09 06:51
         */

        var loc: String? = null
        var utc: String? = null
        override fun toString(): String {
            return "UpdateBean(loc=$loc, utc=$utc)"
        }
    }

    override fun toString(): String {
        return "Basic(city=$city, cnty=$cnty, id=$id, lat=$lat, lon=$lon, update=$update)"
    }
}
