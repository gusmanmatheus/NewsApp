package com.example.newsapp

import android.app.Application
import com.example.newsapp.data.module.dataLayer
import com.example.newsapp.data.module.networkModules
import com.example.newsapp.domain.module.domainModules
import com.example.newsapp.presentation.module.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppNews : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppNews)
            modules(
                listOf(
                    networkModules,
                    dataLayer,
                    domainModules,
                    presentationModule
                )
            )
        }
    }
}