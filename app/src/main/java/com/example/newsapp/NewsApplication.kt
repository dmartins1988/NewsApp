package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.AppComponent
import org.koin.standalone.StandAloneContext.startKoin


class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(
            getAppComponent()
        )
    }

    private fun getAppComponent() = AppComponent
}