package com.mw.example.composenavigation

import android.app.Application
import com.mw.example.mylibrary.FavoriteFeatureApi

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = FavoriteFeatureApi()
    }

    companion object {
        private lateinit var appComponent: FavoriteFeatureApi
        val instance: FavoriteFeatureApi get() = appComponent
    }
}