package com.repository

import android.app.Application
import com.facebook.stetho.Stetho
import com.repository.di.components.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class Application : Application(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector() = injector
}