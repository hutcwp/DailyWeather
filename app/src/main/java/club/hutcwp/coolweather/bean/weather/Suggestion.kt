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

class Suggestion {

    /**
     * air : {"brf":"中","txt":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"}
     * comf : {"brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"}
     * cw : {"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"}
     * drsg : {"brf":"较舒适","txt":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"}
     * flu : {"brf":"极易发","txt":"将有一次强降温过程，天气寒冷，且空气湿度较大，极易发生感冒，请特别注意增加衣服保暖防寒。"}
     * sport : {"brf":"较不宜","txt":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意携带雨具并注意避雨防滑。"}
     * trav : {"brf":"适宜","txt":"温度适宜，又有较弱降水和微风作伴，会给您的旅行带来意想不到的景象，适宜旅游，可不要错过机会呦！"}
     * uv : {"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}
     */

    var air: AirBean? = null
    var comf: ComfBean? = null
    var cw: CwBean? = null
    var drsg: DrsgBean? = null
    var flu: FluBean? = null
    var sport: SportBean? = null
    var trav: TravBean? = null
    var uv: UvBean? = null

    class AirBean {
        /**
         * brf : 中
         * txt : 气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。
         */

        var brf: String? = null
        var txt: String? = null
        override fun toString(): String {
            return "AirBean(brf=$brf, txt=$txt)"
        }
    }

    class ComfBean {
        /**
         * brf : 舒适
         * txt : 白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。
         */

        var brf: String? = null
        var txt: String? = null
        override fun toString(): String {
            return "ComfBean(brf=$brf, txt=$txt)"
        }
    }

    class CwBean {
        /**
         * brf : 不宜
         * txt : 不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。
         */

        var brf: String? = null
        var txt: String? = null
        override fun toString(): String {
            return "CwBean(brf=$brf, txt=$txt)"
        }

    }

    class DrsgBean {
        /**
         * brf : 较舒适
         * txt : 建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。
         */

        var brf: String? = null
        var txt: String? = null
        override fun toString(): String {
            return "DrsgBean(brf=$brf, txt=$txt)"
        }

    }

    class FluBean {
        /**
         * brf : 极易发
         * txt : 将有一次强降温过程，天气寒冷，且空气湿度较大，极易发生感冒，请特别注意增加衣服保暖防寒。
         */

        var brf: String? = null
        var txt: String? = null
        override fun toString(): String {
            return "FluBean(brf=$brf, txt=$txt)"
        }
    }

    class SportBean {
        /**
         * brf : 较不宜
         * txt : 有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意携带雨具并注意避雨防滑。
         */

        var brf: String? = null
        var txt: String? = null
        override fun toString(): String {
            return "SportBean(brf=$brf, txt=$txt)"
        }

    }

    class TravBean {
        /**
         * brf : 适宜
         * txt : 温度适宜，又有较弱降水和微风作伴，会给您的旅行带来意想不到的景象，适宜旅游，可不要错过机会呦！
         */

        var brf: String? = null
        var txt: String? = null
        override fun toString(): String {
            return "TravBean(brf=$brf, txt=$txt)"
        }

    }

    class UvBean {
        /**
         * brf : 最弱
         * txt : 属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
         */

        var brf: String? = null
        var txt: String? = null
        override fun toString(): String {
            return "UvBean(brf=$brf, txt=$txt)"
        }
    }

    override fun toString(): String {
        return "Suggestion(air=$air, comf=$comf, cw=$cw, drsg=$drsg, flu=$flu, sport=$sport, trav=$trav, uv=$uv)"
    }

}
