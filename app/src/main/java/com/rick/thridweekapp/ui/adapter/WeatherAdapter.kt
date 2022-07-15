package com.rick.thridweekapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rick.thridweekapp.R
import com.rick.thridweekapp.bean.WeatherSixDay
import com.rick.thridweekapp.databinding.ItemWeatherBinding
import java.util.*

/**
 * Created by Rick on 2022/7/14 17:51.
 * God bless my code!
 */
class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.InnerHolder>() {
    private var mData: WeatherSixDay? = null

    fun setData(it: WeatherSixDay) {
        mData = it
        notifyDataSetChanged()
    }

    class InnerHolder(val bind: ItemWeatherBinding) : RecyclerView.ViewHolder(bind.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {
        return InnerHolder(ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        val item = mData!!.data
        holder.bind.run {
            val month = Calendar.getInstance().get(Calendar.MONTH) + 1
            when (position) {
                0 -> {
                    tvWeek.text = "昨天"
                    tvWeather.text = item.yesterday.weather
                    tvTemp.text = "${item.yesterday.temphigh}℃"
                    tvDate.text = "$month/${item.yesterday.date.replaceAfter(" ", "")}"
                    ivWeather.setImageResource(getImg(item.yesterday.weather))
                }
                1 -> {
                    tvWeek.text = "今天"
                    tvWeather.text = item.forecast[0].weather
                    tvTemp.text = "${item.forecast[0].temphigh}℃"
                    tvDate.text = "$month/${item.forecast[0].date.replaceAfter(" ", "")}"
                    ivWeather.setImageResource(getImg(item.forecast[0].weather))
                }
                else -> {
                    tvWeek.text = item.forecast[position - 1].date.replaceBefore(" ", "")
                    tvWeather.text = item.forecast[position - 1].weather
                    tvTemp.text = "${item.forecast[position - 1].temphigh}℃"
                    tvDate.text = "$month/${item.forecast[position - 1].date.replaceAfter(" ", "")}"
                    ivWeather.setImageResource(getImg(item.forecast[position - 1].weather))
                }
            }

        }
    }

    fun getImg(weather: String) = when (weather) {
        "晴" -> R.drawable.ic_weather_sunny_foreground
        "多云" -> R.drawable.ic_weather_cloundy_dun_foreground
        "阴" -> R.drawable.ic_weather_cloundy_foreground
        else -> R.drawable.ic_weather_rainy_foreground
    }

    override fun getItemCount(): Int {
        return if (null != mData) {
            6
        } else {
            0
        }
    }
}