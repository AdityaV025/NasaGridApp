package com.example.nasagridapp

import android.app.Application
import com.example.nasagridapp.app.di.adapterModule
import com.example.nasagridapp.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NASAGridApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@NASAGridApplication)
            modules(
                viewModelModule, adapterModule
            )
        }
    }
}