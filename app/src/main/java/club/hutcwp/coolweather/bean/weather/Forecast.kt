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

class Forecast {

    /**
     * cond : {"txt_d":"小雨","txt_n":"小雨"}
     * date : 2017-04-09
     * tmp : {"max":"19","min":"11"}
     */

    var cond: CondBean? = null
    var date: String? = null
    var tmp: TmpBean? = null

    class CondBean {
        /**
         * txt_d : 小雨
         * txt_n : 小雨
         */

        var txt_d: String? = null
        var txt_n: String? = null
        override fun toString(): String {
            return "CondBean(txt_d=$txt_d, txt_n=$txt_n)"
        }
    }

    class TmpBean {
        /**
         * max : 19
         * min : 11
         */

        var max: String? = null
        var min: String? = null
        override fun toString(): String {
            return "TmpBean(max=$max, min=$min)"
        }
    }

    override fun toString(): String {
        return "Forecast(cond=$cond, date=$date, tmp=$tmp)"
    }

}
