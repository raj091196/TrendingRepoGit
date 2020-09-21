package com.repository.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.repository.models.BuildBy
import com.repository.models.TrendingRepo


@Database(entities = [TrendingRepo::class, BuildBy::class], version = 1, exportSchema = false)
abstract class RoomDataBase : RoomDatabase() {

    abstract fun trendingRepoDao(): TrendingDao

}