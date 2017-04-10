/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import club.hutcwp.coolweather.bean.City;
import club.hutcwp.coolweather.bean.County;
import club.hutcwp.coolweather.bean.Province;
import club.hutcwp.coolweather.bean.weather.Weather;

/**
 * Created by hutcwp on 2017/4/9.
 * Mail : hutcwp@foxmail.com
 * Blog : hutcwp.club
 * GitHub : github.com/hutcwp
 */

public class JsonDataPraseUtil {


    /**
     * 处理省的数据
     * @param response
     * @return
     */
    public static boolean handleProvinceResponse(String response){

        if(!TextUtils.isEmpty(response)){

            try {
                JSONArray allProvince = new JSONArray(response);
                for (int i = 0; i < allProvince.length(); i++) {
                    JSONObject jsonObject = allProvince.getJSONObject(i);

                    Province province = new Province();
                    province.setId(jsonObject.getInt("id"));
                    province.setName(jsonObject.getString("name"));
                    province.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 处理城市的数据
     * @param response
     * @return
     */
    public static boolean handleCityResponse(String response,int provinceId){

        if(!TextUtils.isEmpty(response)){

            try {
                JSONArray allCity = new JSONArray(response);
                for (int i = 0; i < allCity.length(); i++) {
                    JSONObject jsonObject = allCity.getJSONObject(i);

                    City city = new City();
                    city.setCityCode(jsonObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.setName(jsonObject.getString("name"));
                    city.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return false;
    }

    /**
     * 处理乡镇数据
     * @param response
     * @return
     */
    public static boolean handleCountyResponse(String response,int cityId){

        if(!TextUtils.isEmpty(response)){

            try {
                JSONArray allCounty = new JSONArray(response);
                for (int i = 0; i < allCounty.length(); i++) {
                    LogUtil.d("test","county11");
                    JSONObject jsonObject = allCounty.getJSONObject(i);

                    County county = new County();
                    county.setCityId(cityId);
                    county.setId(jsonObject.getInt("id"));
                    county.setName(jsonObject.getString("name"));
                    county.setWeather_id(jsonObject.getString("weather_id"));
                    county.save();
                }
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return false;
    }

    /**
     * 处理具体天气数据
     * @param response
     * @return
     */
    public static Weather handleWeatherInfoResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
                String weatherInfo = jsonArray.getJSONObject(0).toString();
                return new Gson().fromJson(weatherInfo, Weather.class);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }




}
