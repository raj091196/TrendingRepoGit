package com.repository.di.modules

import com.repository.Application
import androidx.room.Room
import com.repository.R
import com.repository.localdatabase.RoomDataBase
import com.repository.localdatabase.TrendingDao
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * DI Module for the Room Database Related Objects
 */
@Module
class RoomDBModule {

    @Provides
    @Named("databaseName")
    fun provideDatabaseName(application: Application) =
        application.getString(R.string.database_name)

    /**
     * provides the Room database Instance
     */
    @Provides
    @Singleton
    fun provideRoomDatabase(
        @Named("databaseName") dataBaseName: String,
        application: Application
    ): RoomDataBase {
        return Room.databaseBuilder(
            application.applicationContext,
            RoomDataBase::class.java,
            dataBaseName
        ).build()
    }

    /**
     * provides the Room database Trending Dao Instance
     */
    @Provides
    fun provideTrendingDao(dataBase: RoomDataBase): TrendingDao =
        dataBase.trendingRepoDao()

}