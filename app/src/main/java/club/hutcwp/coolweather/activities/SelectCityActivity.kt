/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.activities

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.view.View

import club.hutcwp.coolweather.R
import club.hutcwp.coolweather.fragment.AreaSelectListFragment

/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

class SelectCityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)

        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            //活动布局显示在状态栏上面
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            //将状态栏设置为透明
            window.statusBarColor = Color.TRANSPARENT
        }

        val fragment = AreaSelectListFragment()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        transaction.commitAllowingStateLoss()

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val cityCode = pref.getString("weather_id", null)

        if (cityCode != null) {
            startActivity(Intent(this, WeatherInfoActivity::class.java))
        }
    }
}
