package com.example.libraryapp

import android.app.Application
import com.example.libraryapp.device.di.DiAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application(){
    override fun onCreate() {
        instance = this

        setupKoinModules()

        super.onCreate()
    }

    companion object {
        lateinit var instance: App
            private set
    }

    private fun setupKoinModules() {
        startKoin {
            androidContext(this@App)
            modules(DiAppModules.provideModules())
        }
    }
}
