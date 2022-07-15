package com.rick.thridweekapp.api

import com.rick.thridweekapp.bean.WeatherSixDay
import com.rick.thridweekapp.bean.WeatherToday
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

/**
 * Created by Rick on 2022/7/14 11:44.
 * God bless my code!
 */
interface WeatherApi {

    @GET("/apis/weather/")
    suspend fun getWeatherToday(@HeaderMap headerMap: Map<String, String>, @Query("id") id: String): WeatherToday

    @GET("/apis/weather6d")
    suspend fun getWeatherSixDays(@HeaderMap headerMap: Map<String, String>, @Query("id") id: String): WeatherSixDay

}