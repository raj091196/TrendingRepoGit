package com.repository.di.components

import com.repository.Application
import com.repository.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [(NetworkModule::class), (RoomDBModule::class),
        (ViewsModule::class), (RepositoryModule::class),
        (AndroidInjectionModule::class), (ActivityModule::class), (ViewModelModules::class)]
)
interface ApplicationComponent : AndroidInjector<Application> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    override fun inject(instance: Application)
}