/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import club.hutcwp.coolweather.R;
import club.hutcwp.coolweather.bean.weather.Forecast;
import club.hutcwp.coolweather.bean.weather.Weather;
import club.hutcwp.coolweather.util.HttpUtil;
import club.hutcwp.coolweather.util.JsonDataPraseUtil;
import club.hutcwp.coolweather.util.LogUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherInfoActivity extends AppCompatActivity {

    private static final String BING_PIC_URL = "https://cn.bing.com/th?id=OHR.Biorocks_ROW7976287935_1920x1080.jpg&rf=LaDigue_1920x1081920x1080.jpg";

    private Button navBtn;
    private ImageView bingPicImg;
    private ScrollView weatherLayout;
    private LinearLayout forecastLayout;
    public DrawerLayout drawerLayout;
    public SwipeRefreshLayout swipeRefresh;
    private TextView aqiText, pm25Text;
    private TextView comfortText, carWashText, sportText;
    private TextView degreeText, weatherInfoText, titleCity, titleUpdateTime;

    private String weatherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        initFindViewById();
        setting();
        loadBingPic();
        initData();
    }

    private void initData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            weatherLayout.setVisibility(View.INVISIBLE);
            Weather weather = JsonDataPraseUtil.handleWeatherInfoResponse(weatherString);
            if (weather != null) {
                weatherId = weather.basic.getId();
            }
            showWeatherInfo(weather);
        } else {
            weatherId = getIntent().getStringExtra("weather_id");
            requestWeather(weatherId);
            weatherLayout.setVisibility(View.INVISIBLE);
            if (weatherId != null) {
                requestWeather(weatherId);
            }
        }
    }

    private void setting() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //活动布局显示在状态栏上面
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //将状态栏设置为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });
        navBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
    }

    public void requestWeather(final String weather_id) {
        /**
         * 此时处于的线程非主线程
         */
        String address = "http://guolin.tech/api/weather?cityid="
                + weather_id
                + "&key=40ac802a67574c119bca4e2089c644cd";

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(WeatherInfoActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = JsonDataPraseUtil.handleWeatherInfoResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.d("test1", "weather" + (weather == null));
                        if (weather != null) {
                            LogUtil.d("test1", "ok?" + weather.getStatus());
                        }
                        LogUtil.d("test1", weather_id);
                        //更新当前城市
                        weatherId = weather_id;
                        if (weather != null) {
                            if ("ok".equals(weather.getStatus())) {
                                SharedPreferences.Editor editor = PreferenceManager
                                        .getDefaultSharedPreferences(WeatherInfoActivity.this)
                                        .edit();
                                editor.putString("weather", responseText);
                                editor.putString("weather_id", weather_id);
                                editor.apply();
                                showWeatherInfo(weather);

                            } else {
                                Toast.makeText(WeatherInfoActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void loadBingPic() {
        Glide.with(WeatherInfoActivity.this)
                .load(BING_PIC_URL)
                .into(bingPicImg);
    }

    public void showWeatherInfo(Weather weather) {
        String tmp = weather.now.getTmp() + "°C";
        String weatherInfo = weather.now.getCond().getTxt();
        String cityName = weather.basic.getCity();
        String update_time = weather.basic.getUpdate().getLoc().split(" ")[1];
        String aqi = weather.aqi.getCity().getAqi();
        String pm25 = weather.aqi.getCity().getPm25();
        String comfort = "舒适度" + weather.suggestion.getComf().getTxt();
        String washCar = "洗车指数" + weather.suggestion.getCw().getTxt();
        String sportSuggestion = "运动建议" + weather.suggestion.getSport().getTxt();

        degreeText.setText(tmp);
        titleCity.setText(cityName);
        weatherInfoText.setText(weatherInfo);
        titleUpdateTime.setText(update_time);
        aqiText.setText(aqi);
        pm25Text.setText(pm25);
        comfortText.setText(comfort);
        carWashText.setText(washCar);
        sportText.setText(sportSuggestion);

        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this)
                    .inflate(R.layout.item_wea_forecast, forecastLayout, false);
            TextView date_text = (TextView) view.findViewById(R.id.date_text);
            TextView info_text = (TextView) view.findViewById(R.id.info_text);
            TextView max_text = (TextView) view.findViewById(R.id.max_text);
            TextView min_text = (TextView) view.findViewById(R.id.min_text);
            date_text.setText(forecast.getDate());
            info_text.setText(forecast.getCond().getTxt_d());
            max_text.setText(forecast.getTmp().getMax());
            min_text.setText(forecast.getTmp().getMin());
            forecastLayout.addView(view);
        }
        weatherLayout.setVisibility(View.VISIBLE);
    }

    public void initFindViewById() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        degreeText = (TextView) findViewById(R.id.degree_text);
        titleCity = (TextView) findViewById(R.id.title_city);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        navBtn = (Button) findViewById(R.id.nav_btn);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
    }
}
