package com.guilhermembisotto.shuffle

import android.app.Application
import com.guilhermembisotto.data.di.getDataModules
import com.guilhermembisotto.shuffle.ui.main.di.getMainModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class ShuffleSongsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger()
            androidContext(this@ShuffleSongsApplication)
            modules(getModules())
        }
    }

    private fun getModules(): MutableList<Module> {
        val modules: MutableList<Module> = arrayListOf()
        modules.addAll(getDataModules())
        modules.addAll(getMainModules())
        return modules
    }
}