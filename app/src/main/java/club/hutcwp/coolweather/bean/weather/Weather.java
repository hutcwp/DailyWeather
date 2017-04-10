/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.bean.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class Weather {

    public Basic basic;
    public AQI aqi;
    public Now now;

    @SerializedName("status")
    public String status;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        status = status;
    }

}
