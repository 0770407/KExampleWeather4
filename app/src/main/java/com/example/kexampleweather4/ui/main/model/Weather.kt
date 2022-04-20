package com.example.kexampleweather4.ui.main.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//для Parcelable подключить в gradle расширение kotlin-android-extensions
//аннотация Parcelize позволяет автоматически переопределить обязательные методы для :Parcelable
@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    // эти нижние поля уже не используются вроде
    val temperature: Int = 0,
    val feelsLike: Int = 0
) : Parcelable

fun getDefaultCity(): City = City("Москва", 55.75, 37.61)

fun getWorldCities(): List<Weather> = listOf(
    Weather(City("London", 51.5085, -0.12574), 15, 13),
    Weather(City("Minsk", 53.9, 27.5667), 15, 13),
    Weather(City("Tokyo", 35.6895, 139.692), 15, 13),
    Weather(City("Paris", 48.8534, 2.3488), 15, 13),
    Weather(City("Rome", 41.8919, 12.5113), 15, 13),
    Weather(City("Beijing", 39.9075, 116.397), 15, 13),
)

fun getRussianCities(): List<Weather> = listOf(
    Weather(City("Москва", 55.7522, 37.6156), 15, 13),
    Weather(City("Санкт-Петербург", 59.9386, 30.3141), 15, 13),
    Weather(City("Новосибирск", 55.0415, 82.9346), 15, 13),
    Weather(City("Самара", 53.2001, 50.15), 15, 13),
    Weather(City("Владивосток", 43.1056, 131.874), 15, 13),
    Weather(City("Астрахань", 46.3497, 48.0408), 15, 13),
    Weather(City("Калининград", 54.7065, 20.511), 15, 13),
)



