package com.example.kexampleweather4.ui.main.viewmodel

import com.example.kexampleweather4.ui.main.model.Weather


sealed class AppState {
    // enum - ограниченные параметры
    // sealed class - ограниченное количество видов наследников

    data class Success(val weather: List<Weather>): AppState()
    data class Error (val error: Throwable): AppState()
    object Loading: AppState() // object, т.к. всегда одинаковый


}
