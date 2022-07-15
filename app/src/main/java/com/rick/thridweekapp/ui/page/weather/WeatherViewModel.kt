package com.rick.thridweekapp.ui.page.weather

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rick.thridweekapp.api.ApiResponse
import com.rick.thridweekapp.bean.CityBean
import com.rick.thridweekapp.bean.WeatherSixDay
import com.rick.thridweekapp.bean.WeatherToday
import com.rick.thridweekapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.InputStream

class WeatherViewModel : ViewModel() {
    private val _cityListLd = MutableLiveData<List<CityBean>>()

    private val _weatherTodayLd = MutableLiveData<ApiResponse<WeatherToday>>()

    private val _weatherSixDay = MutableLiveData<ApiResponse<WeatherSixDay>>()

    val cityListLd: LiveData<List<CityBean>> = _cityListLd

    val weatherTodayLd: LiveData<ApiResponse<WeatherToday>> = _weatherTodayLd

    val weatherSixDay: LiveData<ApiResponse<WeatherSixDay>> = _weatherSixDay

    /**
     * 获取今日天气
     */
    fun requestWeatherToday(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            WeatherRepository.instance.getWeatherToday(id, _weatherTodayLd::postValue)
        }
    }

    /**
     * 获取6天天气
     */
    fun requestWeatherSixDay(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            WeatherRepository.instance.getWeatherSixDay(id, _weatherSixDay::postValue)
        }
    }

    /**
     * 从Exel读取城市数据
     */
    fun readCityFromExel(stream: InputStream) {
        viewModelScope.launch(Dispatchers.IO) {
            val cityList = arrayListOf<CityBean>()
            runCatching {
                val workBook = XSSFWorkbook(stream)
                val sheet = workBook.getSheetAt(0)
                val rowCount = sheet.physicalNumberOfRows
                Log.d("TAG", "rowCount: $rowCount")
                for (i in 1 until rowCount) {
                    val row = sheet.getRow(i)
                    val detailName = row.getCell(3).stringCellValue
                    val city = row.getCell(4).stringCellValue
                    val code = row.getCell(1).stringCellValue
                    cityList.add(CityBean(city, code, detailName))
                }
                _cityListLd.postValue(cityList)
            }
        }
    }

    /**
     * 从Sp读取城市
     */
    fun readCityFromSp(sp: SharedPreferences) {
        viewModelScope.launch(Dispatchers.IO) {
            val citiCodes = arrayListOf<CityBean>()
            runCatching {
                val size = sp.all.size / 3
                for (i in 0 until size) {
                    val city = sp.getString("city$i", "").toString()
                    val code = sp.getString("code$i", "").toString()
                    val detailName = sp.getString("detailName$i", "").toString()
                    citiCodes.add(CityBean(city, code, detailName))
                }
                _cityListLd.postValue(citiCodes)
            }
        }
    }

    /**
     * 存储城市信息
     */
    fun saveCityList(cityList: List<CityBean>, sp: SharedPreferences) {
        viewModelScope.launch(Dispatchers.IO) {
            val edit = sp.edit()
            cityList.asSequence().forEachIndexed { i, cityCode ->
                edit.putString("city$i", cityCode.city)
                edit.putString("code$i", cityCode.code)
                edit.putString("detailName$i", cityCode.detailName)
            }
            edit.putBoolean("hasCity", true)
            edit.apply()
        }
    }
}