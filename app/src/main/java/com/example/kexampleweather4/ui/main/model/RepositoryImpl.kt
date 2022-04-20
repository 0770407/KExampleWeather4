package com.example.kexampleweather4.ui.main.model


class RepositoryImpl : Repository {
    override fun getWeatherFromServer(): Weather = Weather()
    override fun getWeatherFromLocalStorageWorld(): List<Weather> = getWorldCities()
    override fun getWeatherFromLocalStorageRus(): List<Weather> = getRussianCities()
}