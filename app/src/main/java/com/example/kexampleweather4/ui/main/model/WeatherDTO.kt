package com.example.kexampleweather4.ui.main.model

data class WeatherDTO(
    val fact: FactDTO?
) {

    data class FactDTO(
        val temp: Int?,
        val feels_like: Int?,
        val condition: String?
    )

//  Converted via https://jsonformatter.org/json-to-kotlin
//  data class Fact (
//      val temp: Long,
//
//      @Json(name = "feels_like")
//      val feelsLike: Long,
//
//      val condition: String
//)
}