package club.hutcwp.coolweather.bean

/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class County {

    /**
     * Countyid
     * name : County名称
     * weather_id : 天气数据id
     */

    var id: Int = 0
    var cityId: Int = 0
    var name: String? = null
    var weather_id: String? = null

    override fun toString(): String {
        return "County(id=$id, cityId=$cityId, name=$name, weather_id=$weather_id)"
    }

}
