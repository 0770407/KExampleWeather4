package com.example.kexampleweather4.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kexampleweather4.ui.main.model.Repository
import com.example.kexampleweather4.ui.main.model.RepositoryImpl


class MainViewModel : ViewModel() {

    private val repository: Repository = RepositoryImpl()
    private var isRus = true

    // <<<<<<<<<<<<<<<<<<<<<< 1.
    //private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    //private var liveDataIsRusToObserve: MutableLiveData<Boolean> = MutableLiveData(true)
    val liveData: MutableLiveData<AppState> = MutableLiveData()
    val liveDataIsRus: MutableLiveData<Boolean> = MutableLiveData(true)

    fun getWeatherFromLocalSource() = getDataFromLocalSource()

    // <<<<<<<<<<<<<<<<<<<<<<< 3.2 отправка пакета в liveData
    fun onLanguageChange() {
        isRus = !isRus
        liveDataIsRus.value = isRus
        // liveDataIsRusToObserve.value = liveDataIsRusToObserve.value != false
    }

    // <<<<<<<<<<<<<<<<<<<<<<< 3.2 отправка пакета в liveData
    private fun getDataFromLocalSource() {
        liveData.value = AppState.Loading
        Thread {
            Thread.sleep(500)
            liveData.postValue(
                AppState.Success(
                    if (isRus) {
                        repository.getWeatherFromLocalStorageRus()
                    } else {
                        repository.getWeatherFromLocalStorageWorld()
                    }
                )
            )
        }.start()
    }

}