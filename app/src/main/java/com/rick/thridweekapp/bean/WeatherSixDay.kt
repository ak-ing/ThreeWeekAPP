package com.rick.thridweekapp.bean

/**
 * Created by Rick on 2022/7/14 14:17.
 * God bless my code!
 */
data class WeatherSixDay(
    val `data`: Data,
    val msg: String,
    val status: Int
)

data class Data(
    val city: String,
    val forecast: List<Forecast>,
    val life: String,
    val temp: String,
    val yesterday: Yesterday
)

data class Forecast(
    val date: String,
    val temphigh: String,
    val templow: String,
    val weather: String,
    val wind: String,
    val windforce: String
)

data class Yesterday(
    val date: String,
    val temphigh: String,
    val templow: String,
    val weather: String,
    val wind: String,
    val windforce: String
)