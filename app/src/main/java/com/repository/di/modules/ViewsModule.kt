package com.repository.di.modules

import com.repository.Application
import com.repository.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewsModule {

    @Provides
    @Singleton
    fun provideCustomDrawable(application: Application) = Utils(application)
}