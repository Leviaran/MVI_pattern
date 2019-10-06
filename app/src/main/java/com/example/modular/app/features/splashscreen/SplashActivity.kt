package com.example.modular.app.features.splashscreen

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.modular.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportFragmentManager.takeIf { savedInstanceState == null }
            ?.beginTransaction()
            ?.replace(R.id.container, SplashFragment())
            ?.commitNow()
    }
}