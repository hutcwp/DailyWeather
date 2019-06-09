/*
 * Copyright (c) 2017. The Android Project Created By Hutcwp.
 */

package club.hutcwp.coolweather.fragment


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import club.hutcwp.coolweather.R
import club.hutcwp.coolweather.activities.SelectCityActivity
import club.hutcwp.coolweather.activities.WeatherInfoActivity
import club.hutcwp.coolweather.adapter.CityListAdapter
import club.hutcwp.coolweather.bean.City
import club.hutcwp.coolweather.bean.County
import club.hutcwp.coolweather.bean.Province
import club.hutcwp.coolweather.util.HttpUtil
import club.hutcwp.coolweather.util.LogUtil
import okhttp3.Call
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class AreaSelectListFragment : Fragment() {

    private var curLevel = 0

    private var selectedProvince: Province? = null
    private var selectedCity: City? = null

    private var provinceList = mutableListOf<Province>()
    private var cityList = mutableListOf<City>()
    private var countyList = mutableListOf<County>()
    private val nameList = ArrayList<String>()

    private var adapter: CityListAdapter? = null
    private var listView: ListView? = null

    private var btnBack: Button? = null
    private var titleText: TextView? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        initView(view)
        setListener()
        queryProvinces()
        return view
    }

    private fun initView(view: View) {
        btnBack = view.findViewById<View>(R.id.back) as Button
        listView = view.findViewById<View>(R.id.listView) as ListView
        titleText = view.findViewById<View>(R.id.title) as TextView

    }

    private fun setListener() {
        adapter = CityListAdapter(context!!, R.layout.item_listview, nameList)
        listView!!.adapter = adapter
        listView!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            if (curLevel == LEVEL_PROVINCE) {
                selectedProvince = provinceList[position]
                queryCities()
            } else if (curLevel == LEVEL_CITY) {
                selectedCity = cityList[position]
                queryCounties()
            } else if (curLevel == LEVEL_COUNTY) {
                val weatherId = countyList[position].weather_id
                if (activity is SelectCityActivity) {
                    val intent = Intent(activity, WeatherInfoActivity::class.java)
                    intent.putExtra("weather_id", weatherId)
                    activity!!.startActivity(intent)
                } else if (activity is WeatherInfoActivity) {
                    val weatherInfoActivity = activity as WeatherInfoActivity?
                    weatherInfoActivity!!.drawerLayout.closeDrawers()
                    weatherInfoActivity.swipeRefresh.isRefreshing = false
                    weatherInfoActivity.requestWeather(weatherId)
                }
            }
        }

        btnBack!!.setOnClickListener {
            if (curLevel == LEVEL_COUNTY) {
                queryCities()
            } else if (curLevel == LEVEL_CITY) {
                queryProvinces()
            }
        }
    }

    private fun queryProvinces() {
        titleText!!.text = "省份"
        btnBack!!.visibility = View.GONE
        val address = BASE_API
        queryFromServer(address, "province")
    }

    private fun queryCities() {
        titleText!!.text = "城市"
        btnBack!!.visibility = View.VISIBLE
        nameList.clear()
        if (cityList.isNotEmpty()) {
            nameList.clear()
            for (city in cityList) {
                nameList.add(city.name)
            }
            adapter!!.notifyDataSetChanged()
            listView!!.setSelection(0)
            curLevel = LEVEL_CITY
        } else {
            val provinceCode = selectedProvince!!.no
            val address = BASE_API + provinceCode
            queryFromServer(address, "city")
        }
    }


    private fun queryCounties() {
        titleText!!.text = "乡镇"
        btnBack!!.visibility = View.VISIBLE
        nameList.clear()
        if (countyList.isNotEmpty()) {
            for (county in countyList) {
                LogUtil.i("test", "111")
                nameList.add(county.name)
            }
            adapter!!.notifyDataSetChanged()
            listView!!.setSelection(0)
            curLevel = LEVEL_COUNTY
        } else {
            val provinceCode = selectedProvince!!.no
            val cityCode = selectedCity!!.cityCode
            val address = "$BASE_API$provinceCode/$cityCode"
            queryFromServer(address, "county")
        }
    }

    /**
     * 处理省的数据
     * @param response
     * @return
     */
    fun handleProvinceResponse(response: String) {
        if (!TextUtils.isEmpty(response)) {
            try {
                nameList.clear()
                val allProvince = JSONArray(response)
                for (i in 0 until allProvince.length()) {
                    val jsonObject: JSONObject = allProvince[i] as JSONObject
                    nameList.add(jsonObject.getString("name"))
                    val province = Province()
                    province.no = jsonObject.getInt("id")
                    province.name = jsonObject.getString("name")
                    province.save()
                    provinceList.add(province)
                }

                activity?.runOnUiThread {
                    adapter!!.notifyDataSetChanged()
                    listView!!.setSelection(0)
                    curLevel = LEVEL_PROVINCE
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 处理城市的数据
     * @param response
     * @return
     */
    fun handleCityResponse(response: String, provinceId: Int) {
        if (!TextUtils.isEmpty(response)) {
            try {
                nameList.clear()
                val allCity = JSONArray(response)
                for (i in 0 until allCity.length()) {
                    val jsonObject = allCity.getJSONObject(i)

                    val city = City()
                    city.cityCode = jsonObject.getInt("id")
                    city.provinceId = provinceId
                    city.name = jsonObject.getString("name")
                    city.save()
                    cityList.add(city)
                    nameList.add(jsonObject.getString("name"))
                }

                activity?.runOnUiThread {
                    adapter!!.notifyDataSetChanged()
                    listView!!.setSelection(0)
                    curLevel = LEVEL_CITY
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 处理乡镇数据
     * @param response
     * @return
     */
    fun handleCountyResponse(response: String, cityId: Int) {
        if (!TextUtils.isEmpty(response)) {
            try {
                val allCounty = JSONArray(response)
                nameList.clear()
                for (i in 0 until allCounty.length()) {
                    LogUtil.d("test", "county11")
                    val jsonObject = allCounty.getJSONObject(i)

                    val county = County()
                    county.cityId = cityId
                    county.id = jsonObject.getInt("id")
                    county.name = jsonObject.getString("name")
                    county.weather_id = jsonObject.getString("weather_id")
                    county.save()
                    countyList.add(county)
                    nameList.add(jsonObject.getString("name"))
                }
                activity?.runOnUiThread {
                    adapter!!.notifyDataSetChanged()
                    listView!!.setSelection(0)
                    curLevel = LEVEL_COUNTY
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }


    private fun queryFromServer(address: String, type: String) {
        showProgressDialog()
        HttpUtil.sendOkHttpRequest(address, object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                closeProgressDialog()
                val h = Handler(Looper.getMainLooper())
                h.post {
                    Toast.makeText(activity, "加载失败", Toast.LENGTH_LONG).show()
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val responseText = response.body().string()
                closeProgressDialog()
                when (type) {
                    "province" -> handleProvinceResponse(responseText)
                    "city" -> handleCityResponse(responseText, selectedProvince!!.no)
                    "county" -> handleCountyResponse(responseText, selectedCity!!.cityCode)
                }
                Log.i(TAG, "onResponse ,cur level = $curLevel ,response=$responseText")
            }
        })
    }

    /**
     * 开启进度窗口
     */
    private fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(activity)
            progressDialog!!.setMessage("正在加载...")
            progressDialog!!.setCancelable(false)
        }
        progressDialog!!.show()
    }

    /**
     * 关闭进度窗口
     */
    fun closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog!!.dismiss()
        }
    }

    companion object {
        private const val LEVEL_PROVINCE = 0
        private const val LEVEL_CITY = 1
        private const val LEVEL_COUNTY = 2
        private const val TAG = "AreaSelectListFragment"
        private const val BASE_API = "http://guolin.tech/api/china/"
    }
}
