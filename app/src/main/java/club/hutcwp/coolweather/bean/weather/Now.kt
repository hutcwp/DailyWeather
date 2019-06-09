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

class Now {

    /**
     * cond : {"code":"300","txt":"阵雨"}
     * fl : 18
     * hum : 96
     * pcpn : 0
     * pres : 1006
     * tmp : 17
     * vis : 10
     * wind : {"deg":"300","dir":"西北风","sc":"3-4","spd":"19"}
     */

    var cond: CondBean? = null
    var fl: String? = null
    var hum: String? = null
    var pcpn: String? = null
    var pres: String? = null
    var tmp: String? = null
    var vis: String? = null
    var wind: WindBean? = null

    class CondBean {
        /**
         * code : 300
         * txt : 阵雨
         */

        var code: String? = null
        var txt: String? = null
        override fun toString(): String {
            return "CondBean(code=$code, txt=$txt)"
        }
    }

    class WindBean {
        /**
         * deg : 300
         * dir : 西北风
         * sc : 3-4
         * spd : 19
         */

        var deg: String? = null
        var dir: String? = null
        var sc: String? = null
        var spd: String? = null
        override fun toString(): String {
            return "WindBean(deg=$deg, dir=$dir, sc=$sc, spd=$spd)"
        }
    }

    override fun toString(): String {
        return "Now(cond=$cond, fl=$fl, hum=$hum, pcpn=$pcpn, pres=$pres, tmp=$tmp, vis=$vis, wind=$wind)"
    }

}
