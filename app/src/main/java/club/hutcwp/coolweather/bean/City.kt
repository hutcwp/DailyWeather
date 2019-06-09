package club.hutcwp.coolweather.bean


/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class City {

    /**
     * id : 城市id
     * name : 城市名称
     */

    var id: Int = 0
    var cityCode: Int = 0
    var provinceId: Int = 0
    var name: String? = null

    override fun toString(): String {
        return "City(id=$id, cityCode=$cityCode, provinceId=$provinceId, name=$name)"
    }
}
