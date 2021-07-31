package com.mohamadrizki.nontonyuk

import android.app.Application
import com.mohamadrizki.nontonyuk.core.di.databaseModule
import com.mohamadrizki.nontonyuk.core.di.networkModule
import com.mohamadrizki.nontonyuk.core.di.repositoryModule
import com.mohamadrizki.nontonyuk.di.useCaseModule
import com.mohamadrizki.nontonyuk.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}