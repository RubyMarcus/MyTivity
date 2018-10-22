package com.apia22018.sportactivities.screens.splash

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apia22018.sportactivities.screens.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LoginActivity.start(this)
        finish()
    }
}
