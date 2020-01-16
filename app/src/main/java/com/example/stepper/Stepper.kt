package com.example.stepper

import android.app.Application
import androidx.multidex.MultiDex

class Stepper:Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this@Stepper)
    }
}