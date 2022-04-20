package com.example.kexampleweather4.ui.main.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.kexampleweather4.BuildConfig
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class WeatherLoader(
    private val lat: Double,
    private val lon: Double,
    private val listener: WeatherLoaderListener
) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun goToInternet() {

        Thread {
            val uri = URL ("https://api.weather.yandex.ru/v2/forecast?lat=${lat}&lon=${lon}&lang=ru_RU")

            var urlConnection: HttpsURLConnection? = null

            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                // т.е. openConnection возвращает urlConnection, нужно привести тип к его наследнику
                urlConnection.apply {
                    requestMethod = "GET"
                    readTimeout = 10000
//                    addRequestProperty("X-Yandex-API-Key", "627a...........783")
                    // 1-создаем в корне проекта файл apikey.properties c ключом
                    // 2-добавляем данный файл в соседний .gitignore
                    // 3-добавляем текст на Groove в gradle
                    // 4-добавляем строчку ниже
                    addRequestProperty("X-Yandex-API-Key", BuildConfig.WEATHER_API_KEY)
                }
                val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = reader.lines().collect(Collectors.joining("\n"))
                // т.е. для lines() нужна аннотация версии (выше)?

                // здесь преобразование json в объект weatherDTO
                val weatherDTO: WeatherDTO = Gson().fromJson(result, WeatherDTO::class.java)

                // важно! (2) а отрисовку можно сделать только в основном потоке либо через run, либо handler
//            handler.post {
//                binding.webview.loadData(result, "text/html; charset=utf-8", "utf-8")
//            }

                // способ 2. через run
                // меняем на листенер
//                requireActivity().runOnUiThread {
//                    displayWeather(weatherDTO)
//                }
                listener.onLoaded(weatherDTO)

            } catch (e: Exception) {
                listener.onFailed(e)
                Log.e("", "FAILED", e)
            } finally {
                urlConnection?.disconnect()
            }
        }.start()

    }

    interface WeatherLoaderListener {
        fun onLoaded(weatherDTO: WeatherDTO)
        fun onFailed(throwable: Throwable)
    }
}