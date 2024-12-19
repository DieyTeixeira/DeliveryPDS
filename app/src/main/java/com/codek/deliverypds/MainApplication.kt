package com.codek.deliverypds

import android.app.Application
import com.codek.deliverypds.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Inicializar o Koin
        startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}