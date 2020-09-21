package com.repository.di.modules

import com.repository.Application
import com.repository.CustomDrawable
import dagger.Module
import dagger.Provides

@Module
class ViewsModule {

    @Provides
    fun provideCustomDrawable(application: Application) = CustomDrawable(application)
}