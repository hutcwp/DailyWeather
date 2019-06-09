/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import club.hutcwp.coolweather.R;
import club.hutcwp.coolweather.fragment.AreaSelectListFragment;

/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class SelectCityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //活动布局显示在状态栏上面
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //将状态栏设置为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        AreaSelectListFragment fragment = new AreaSelectListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String cityCode = pref.getString("weather_id", null);

        if(cityCode!=null){
            startActivity(new Intent(this,WeatherInfoActivity.class));
        }
    }


}
