package com.myfitness.ui.retrofit.network

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        RetrofitService.initialize()
    }
}
