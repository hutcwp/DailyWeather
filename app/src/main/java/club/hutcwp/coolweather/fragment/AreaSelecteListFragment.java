/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import club.hutcwp.coolweather.R;
import club.hutcwp.coolweather.activities.SelectCityActivity;
import club.hutcwp.coolweather.activities.WeatherInfoActivity;
import club.hutcwp.coolweather.adapter.CityListAdapter;
import club.hutcwp.coolweather.bean.City;
import club.hutcwp.coolweather.bean.County;
import club.hutcwp.coolweather.bean.Province;
import club.hutcwp.coolweather.util.HttpUtil;
import club.hutcwp.coolweather.util.JsonDataPraseUtil;
import club.hutcwp.coolweather.util.LogUtil;
import okhttp3.Call;
import okhttp3.Response;

public class AreaSelecteListFragment extends Fragment {

    private final int LEVEL_PROVINCE = 0;
    private final int LEVEL_CITY = 1;
    private final int LEVEL_COUNTY = 2;

    private static int cur_level = 0;

    private Province selectedProvince;
    private City selectedCity;

    private List<Province> provinceList = new ArrayList<>();
    private List<City> cityList = new ArrayList<>();
    private List<County> countyList = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();

    private CityListAdapter adapter;
    private ListView listView;

    private ProgressDialog progressDialog = null;
    private Button btn_back;


    private TextView title_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        btn_back = (Button) view.findViewById(R.id.back);
        listView = (ListView) view.findViewById(R.id.listView);
        title_text = (TextView) view.findViewById(R.id.title);


        adapter = new CityListAdapter(getContext(), R.layout.item_listview, nameList);
        listView.setAdapter(adapter);

        queryProvinces();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (cur_level == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCitys();
                } else if (cur_level == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCountys();
                } else if (cur_level == LEVEL_COUNTY) {
                    String weather_id = countyList.get(position).getWeather_id();
                    if (getActivity() instanceof SelectCityActivity) {
                        Intent intent = new Intent(getActivity(), WeatherInfoActivity.class);
                        intent.putExtra("weather_id", weather_id);
                        getActivity().startActivity(intent);
                    }else if(getActivity() instanceof WeatherInfoActivity){
                        WeatherInfoActivity weatherInfoActivity = (WeatherInfoActivity) getActivity();
                        weatherInfoActivity.drawerLayout.closeDrawers();
                        weatherInfoActivity.swipeRefresh.setRefreshing(false);
                        weatherInfoActivity.requestWeather(weather_id);
                    }
                }

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cur_level == LEVEL_COUNTY) {
                    queryCitys();
                } else if (cur_level == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        return view;
    }


    public void queryProvinces() {

        title_text.setText("省份");
        btn_back.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        nameList.clear();
        if (provinceList.size() > 0) {
            for (Province province : provinceList) {
                nameList.add(province.getName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            cur_level = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china/";
            querrFromServer(address, "province");
        }
    }

    public void queryCitys() {

        title_text.setText("城市");
        btn_back.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceId = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        nameList.clear();
        if (cityList.size() > 0) {
            nameList.clear();
            for (City city : cityList) {
                nameList.add(city.getName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            cur_level = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getId();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            querrFromServer(address, "city");
        }

    }


    public void queryCountys() {

        title_text.setText("乡镇");
        btn_back.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityId = ?", String.valueOf((selectedCity.getCityCode()))).find(County.class);
        nameList.clear();
        if (countyList.size() > 0) {
            for (County county : countyList) {
                LogUtil.d("test", "111");
                nameList.add(county.getName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            cur_level = LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getId();
            int CityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + CityCode;
            querrFromServer(address, "county");
        }
    }


    public void querrFromServer(String address, final String type) {

        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                closeProgressDialog();
                Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)) {
                    result = JsonDataPraseUtil.handleProvinceResponse(responseText);
                } else if ("city".equals(type)) {
                    result = JsonDataPraseUtil.handleCityResponse(responseText, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = JsonDataPraseUtil.handleCountyResponse(responseText, selectedCity.getCityCode());
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            }
                            if ("city".equals(type)) {
                                queryCitys();
                            }
                            if ("county".equals(type)) {
                                queryCountys();
                            }
                        }

                    });
                }
            }

        });
    }

    /**
     * 开启进度窗口
     */
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度窗口
     */
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}
