package com.example.numberstesttask.main


import android.app.Application
import com.example.numberstesttask.numbers.data.cloud.CloudModule
import com.example.numberstesttask.BuildConfig
class NumbersApp : Application() {

    override fun onCreate() {
        super.onCreate()
        //todo move out of here
        val cloudModel = if (BuildConfig.DEBUG)
            CloudModule.Debug()
        else
            CloudModule.Release()
    }
}