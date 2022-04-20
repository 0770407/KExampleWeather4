package com.example.kexampleweather4.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kexampleweather4.R
import com.example.kexampleweather4.databinding.MainActivityBinding


class MainActivity : AppCompatActivity() {

// при создании проекта на empty activity работал только класс
//  ActivityMainBinding (т.е. написание наоборот ??? !!!!)
    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}