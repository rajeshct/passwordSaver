package com.remember.password

import android.app.Application
import com.remember.password.util.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CustomApplication : Application() {

    companion object {
        @get:Synchronized
        lateinit var instance: CustomApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidLogger()
            androidContext(this@CustomApplication)
            modules(appComponent)
        }
    }
}