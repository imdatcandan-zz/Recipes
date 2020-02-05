package com.marleyspoon.recipes

import android.app.Application
import com.marleyspoon.recipes.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MarleySpoonApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MarleySpoonApp)
            modules(module)
        }
    }
}