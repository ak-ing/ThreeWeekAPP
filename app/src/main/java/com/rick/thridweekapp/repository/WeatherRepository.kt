package com.rick.thridweekapp.repository

import android.util.Log
import com.rick.thridweekapp.api.ApiResponse
import com.rick.thridweekapp.api.ServiceCreator
import com.rick.thridweekapp.api.WeatherApi
import com.rick.thridweekapp.bean.WeatherSixDay
import com.rick.thridweekapp.bean.WeatherToday

/**
 * Created by Rick on 2022/7/14 14:35.
 * God bless my code!
 */
class WeatherRepository {

    companion object {
        private val it = WeatherRepository()

        @JvmStatic
        val instance
            get() = it
    }

    /**
     * 天气接口
     */
    private val mApi = ServiceCreator.create(WeatherApi::class.java)

    /**
     * 获取今日天气
     */
    suspend fun getWeatherToday(id: String, result: ApiResponse.Result<WeatherToday>) {
        runCatching { mApi.getWeatherToday(header, id) }.onSuccess {
            Log.d("TAG", "requestWeatherToday: $it")
            result.onResult(ApiResponse.Success(it))
        }.onFailure {
            it.printStackTrace()
            result.onResult(ApiResponse.Error("${it.message}"))
        }
    }

    /**
     * 获取6天天气
     */
    suspend fun getWeatherSixDay(id: String, result: ApiResponse.Result<WeatherSixDay>) {
        runCatching { mApi.getWeatherSixDays(header,id) }.onSuccess {
            Log.d("TAG", "getWeatherSixDay: $it")
            result.onResult(ApiResponse.Success(it))
        }.onFailure {
            it.printStackTrace()
            result.onResult(ApiResponse.Error(it.message))
        }
    }

    /**
     * 伪造请求头
     */
    private val header = mapOf(
        "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
        "Accept-Encoding" to "gzip, deflate",
        "Accept-Language" to "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
        "Cache-Control" to "max-age=0",
        "Connection" to "keep-alive",
        "Cookie" to "Hm_lvt_187fda34c81598ec29ebc4da675267cc=1657007239,1657094016,1657158864; Hm_lpvt_187fda34c81598ec29ebc4da675267cc=1657175544",
        "Host" to "api.help.bj.cn",
        "content-type" to "application/json",
        "Referer" to "http://api.help.bj.cn/api/?id=45",
        "Upgrade-Insecure-Requests" to "1",
        "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.5060.66 Safari/537.36 Edg/103.0.1264.44"
    )

}