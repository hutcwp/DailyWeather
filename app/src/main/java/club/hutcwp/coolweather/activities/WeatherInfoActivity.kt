/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.widget.DrawerLayout
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import club.hutcwp.coolweather.R
import club.hutcwp.coolweather.bean.weather.Weather
import club.hutcwp.coolweather.util.HttpUtil
import club.hutcwp.coolweather.util.LogUtil
import com.bumptech.glide.Glide
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class WeatherInfoActivity : AppCompatActivity() {

    private var navBtn: Button? = null
    private var bingPicImg: ImageView? = null
    private var weatherLayout: ScrollView? = null
    private var forecastLayout: LinearLayout? = null
    lateinit var drawerLayout: DrawerLayout
    lateinit var swipeRefresh: SwipeRefreshLayout
    private var aqiText: TextView? = null
    private var pm25Text: TextView? = null
    private var comfortText: TextView? = null
    private var carWashText: TextView? = null
    private var sportText: TextView? = null
    private var degreeText: TextView? = null
    private var weatherInfoText: TextView? = null
    private var titleCity: TextView? = null
    private var titleUpdateTime: TextView? = null

    private var weatherId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_info)
        initFindViewById()
        setting()
        loadBingPic()
        initData()
    }

    private fun initData() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val weatherString = prefs.getString("weather", null)
        if (weatherString != null) {
            weatherLayout!!.visibility = View.INVISIBLE
            val weather = handleWeatherInfoResponse(weatherString)
            if (weather != null) {
                weatherId = weather.basic.id
            }
            showWeatherInfo(weather)
        } else {
            weatherId = intent.getStringExtra("weather_id")
            requestWeather(weatherId)
            weatherLayout!!.visibility = View.INVISIBLE
            if (weatherId != null) {
                requestWeather(weatherId)
            }
        }
    }

    private fun setting() {
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            //活动布局显示在状态栏上面
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //将状态栏设置为透明
            window.statusBarColor = Color.TRANSPARENT
        }
        swipeRefresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        swipeRefresh.setOnRefreshListener { requestWeather(weatherId) }
        navBtn!!.setOnClickListener { drawerLayout.openDrawer(Gravity.START) }
    }

    fun requestWeather(weather_id: String?) {
        /**
         * 此时处于的线程非主线程
         */
        val weatherUrl = ("http://guolin.tech/api/weather?cityid="
                + weather_id
                + "&key=40ac802a67574c119bca4e2089c644cd")

        LogUtil.i(TAG, "[requestWeather]: weatherUrl=$weatherUrl")
        HttpUtil.sendOkHttpRequest(weatherUrl, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@WeatherInfoActivity, "加载失败", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseText = response.body().string()
                val weather = handleWeatherInfoResponse(responseText)
                runOnUiThread {
                    weather?.let {
                        LogUtil.d(TAG, "weather = $weather")
                        //更新当前城市
                        weatherId = weather_id
                        if ("ok" == weather.getStatus()) {
                            val editor = PreferenceManager
                                    .getDefaultSharedPreferences(this@WeatherInfoActivity)
                                    .edit()
                            editor.putString("weather", responseText)
                            editor.putString("weather_id", weather_id)
                            editor.apply()
                            showWeatherInfo(weather)
                        } else {
                            Toast.makeText(this@WeatherInfoActivity, "获取天气信息失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                    swipeRefresh.isRefreshing = false
                }
            }
        })
    }

    private fun loadBingPic() {
        Glide.with(this@WeatherInfoActivity)
                .load(BING_PIC_URL)
                .into(bingPicImg!!)
    }

    fun showWeatherInfo(weather: Weather?) {
        val tmp = weather!!.now.tmp + "°C"
        val weatherInfo = weather.now.cond?.txt
        val cityName = weather.basic.city
        val updateTime = weather.basic.update?.loc?.split(" ".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()?.get(1)
        val aqi = weather.aqi.city?.aqi
        val pm25 = weather.aqi.city?.pm25
        val comfort = "舒适度" + weather.suggestion.comf?.txt
        val washCar = "洗车指数" + weather.suggestion.cw?.txt
        val sportSuggestion = "运动建议" + weather.suggestion.sport?.txt

        degreeText!!.text = tmp
        titleCity!!.text = cityName
        weatherInfoText!!.text = weatherInfo
        titleUpdateTime!!.text = updateTime
        aqiText!!.text = aqi
        pm25Text!!.text = pm25
        comfortText!!.text = comfort
        carWashText!!.text = washCar
        sportText!!.text = sportSuggestion

        forecastLayout!!.removeAllViews()
        for (forecast in weather.forecastList) {
            val view = LayoutInflater.from(this)
                    .inflate(R.layout.item_wea_forecast, forecastLayout, false)
            val dateText = view.findViewById<View>(R.id.date_text) as TextView
            val infoText = view.findViewById<View>(R.id.info_text) as TextView
            val maxText = view.findViewById<View>(R.id.max_text) as TextView
            val minText = view.findViewById<View>(R.id.min_text) as TextView
            dateText.text = forecast.date
            infoText.text = forecast.cond?.txt_d
            maxText.text = forecast.tmp?.max
            minText.text = forecast.tmp?.min
            forecastLayout!!.addView(view)
        }
        weatherLayout!!.visibility = View.VISIBLE
    }

    private fun initFindViewById() {
        drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        swipeRefresh = findViewById<View>(R.id.swipe_refresh) as SwipeRefreshLayout
        forecastLayout = findViewById<View>(R.id.forecast) as LinearLayout
        weatherLayout = findViewById<View>(R.id.weather_layout) as ScrollView
        bingPicImg = findViewById<View>(R.id.bing_pic_img) as ImageView
        degreeText = findViewById<View>(R.id.degree_text) as TextView
        titleCity = findViewById<View>(R.id.title_city) as TextView
        weatherInfoText = findViewById<View>(R.id.weather_info_text) as TextView
        titleUpdateTime = findViewById<View>(R.id.title_update_time) as TextView
        navBtn = findViewById<View>(R.id.nav_btn) as Button
        aqiText = findViewById<View>(R.id.aqi_text) as TextView
        pm25Text = findViewById<View>(R.id.pm25_text) as TextView
        comfortText = findViewById<View>(R.id.comfort_text) as TextView
        carWashText = findViewById<View>(R.id.car_wash_text) as TextView
        sportText = findViewById<View>(R.id.sport_text) as TextView
    }

    /**
     * 处理具体天气数据
     * @param response
     * @return
     */
    fun handleWeatherInfoResponse(response: String): Weather? {
        if (!TextUtils.isEmpty(response)) {
            try {
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("HeWeather")
                val weatherInfo = jsonArray.getJSONObject(0).toString()
                return Gson().fromJson(weatherInfo, Weather::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return null
    }

    companion object {
        private const val TAG = "WeatherInfoActivity"
        private const val BING_PIC_URL = "https://cn.bing.com/th?id=OHR.Biorocks_ROW7976287935_1920x1080.jpg&rf=LaDigue_1920x1081920x1080.jpg"
    }
}
