package com.safronov_original_app_online_store.app

import android.app.Application
import com.safronov_original_app_online_store.di.dataDi
import com.safronov_original_app_online_store.di.domainDi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(domainDi, dataDi))
        }
    }

}