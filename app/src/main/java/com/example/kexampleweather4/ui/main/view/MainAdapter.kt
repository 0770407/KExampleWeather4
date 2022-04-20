package com.example.kexampleweather4.ui.main.view

import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kexampleweather4.R
import com.example.kexampleweather4.ui.main.model.Weather


class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    var weatherData: List<Weather> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
//    вариант:
//    fun setWeather (weather: List<Weather>) {
//        weatherData = weather
//        notifyDataSetChanged()
//    }

    // интерфейс описан ниже
    var listener: OnItemViewClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_fragment_item, parent, false)
        )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weatherData[position])
    }

    // тут просто для примера also
    override fun getItemCount(): Int = weatherData.size.also {
        Log.d ("TAG", "getItemCount: $it")
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            itemView.apply {
                findViewById<TextView>(R.id.cityName).text = weather.city.name
                setOnClickListener {
                    listener?.onItemClick(weather)
                }
            }
        }
    }

    // Для чего нужен вообще этот интерфейс? Чтобы не вешать отдельный лисенер на каждый item
// в адаптере.
//    interface OnItemViewClickListener {
//        fun onItemClick (weather: Weather)
//    }
// !!!!!!!! теперь интерфейс можно определить как функциональный интерфейс (kotlin 1.5 и выше):
    fun interface OnItemViewClickListener {
        fun onItemClick(weather: Weather)
    }

}